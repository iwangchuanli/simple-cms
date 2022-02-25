package com.cms.template.controller;

import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cms.template.common.result.Result;
import com.cms.template.common.security.authentication.UserPrincipal;
import com.cms.template.common.util.ObjectUtil;
import com.cms.template.entity.Article;
import com.cms.template.entity.Cate;
import com.cms.template.entity.Site;
import com.cms.template.entity.User;
import com.cms.template.service.ArticleService;
import com.cms.template.service.CateService;
import com.cms.template.service.SiteService;
import com.cms.template.service.UserService;
import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

@RestController
@RequestMapping("/index")
public class IndexController {
    @Resource
    private UserService userService;
    @Resource
    private SiteService siteService;
    @Resource
    private CateService cateService;
    @Resource
    private ArticleService articleService;
    @Autowired
    Cache<String, Object> caffeineCache;
    @RequestMapping(value = "/admin/index", method = RequestMethod.POST)
    public Result adminIndex(UserPrincipal userPrincipal, @RequestBody JSONObject jsonObject) {
        Map<String,Object> result = new HashMap<>();
        if (userPrincipal.getRole()!=0){
            Site site = siteService.getById(userPrincipal.getSiteId());
            if (site!=null){
                QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
                articleQueryWrapper.eq("site_id",site.getId());
                articleQueryWrapper.eq("status",1);
                Integer article_total = articleService.count(articleQueryWrapper);
                articleQueryWrapper.eq("recommend",1);
                Integer article_recommend_total = articleService.count(articleQueryWrapper);
                QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
                userQueryWrapper.eq("site_id",site);
                userQueryWrapper.eq("status",1);
                Integer admin_total = userService.count(userQueryWrapper);
                result.put("site",site);
                result.put("article_total",article_total);
                result.put("article_recommend_total",article_recommend_total);
                result.put("admin_total",admin_total);
            }
        }
        if (userPrincipal.getRole()==0){
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("status",1);
            Integer admin_total = userService.count(userQueryWrapper);
            QueryWrapper<Site> siteQueryWrapper = new QueryWrapper<>();
            siteQueryWrapper.eq("status",1);
            Integer site_total = siteService.count(siteQueryWrapper);
            QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
            articleQueryWrapper.eq("status",1);
            Integer article_total = articleService.count(articleQueryWrapper);
            result.put("admin_total",admin_total);
                result.put("site_total",site_total);
            result.put("article_total",article_total);
        }
        return Result.success(result);
    }

    @RequestMapping(value = "/common/index", method = RequestMethod.POST)
    public Result index(@RequestBody JSONObject jsonObject) {
        Long siteId = jsonObject.getLong("siteId");
        Map<String, Object> data = (Map<String, Object>) caffeineCache.asMap().get(siteId.toString());
        if (data != null){
            return Result.success(data);
        }
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Callable<List<Article>> Callable1 = () -> getRecommendArticle(siteId);
        Callable<List<Map<String, Object>>> Callable2 = () -> getRecommendCateArticle(siteId);
        Callable<List<Map<String, Object>>> Callable3 = () -> getNormalCateArticle(siteId);
        FutureTask<List<Article>> Task1 = new FutureTask<>(Callable1);
        FutureTask<List<Map<String, Object>>> Task2 = new FutureTask<>(Callable2);
        FutureTask<List<Map<String, Object>>> Task3 = new FutureTask<>(Callable3);
        executorService.submit(Task1);
        executorService.submit(Task2);
        executorService.submit(Task3);
        data = new HashMap<>();
        try {
            data.put("recommendArticle", Task1.get());
            data.put("recommendCate", Task2.get());
            data.put("normalCate", Task3.get());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
        caffeineCache.put(siteId.toString(),data);
        return Result.success(data);
    }

    public List<Article> getRecommendArticle(Long siteId) {
        Site site = siteService.getById(siteId);
        List<Article> articleList = new ArrayList<>();
        if (site != null && site.getRecommendArticle() != null) {
//            List<Integer> articles = (List<Integer>) JSON.parse(site.getRecommendArticle());
            List<Long> articles = JSON.parseArray(site.getRecommendArticle(),Long.class);
            QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
            articleQueryWrapper.in("id",articles);
            articleQueryWrapper.orderByDesc("create_time");
            articleList = articleService.list(articleQueryWrapper);
//            articles.forEach(article -> {
//                articleList.add(articleService.getById(Long.parseLong(article.toString())));
//            });
        }
        return articleList;
    }

    public List<Map<String, Object>> getRecommendCateArticle(Long siteId) {
        QueryWrapper<Cate> cateQueryWrapper = new QueryWrapper<>();
        cateQueryWrapper.eq("site_id", siteId);
        cateQueryWrapper.eq("recommend", 1);
        cateQueryWrapper.ne("pid", 0);
        cateQueryWrapper.eq("status", 1);
        List<Cate> cateList = cateService.list(cateQueryWrapper);
        List<Map<String, Object>> result = new ArrayList<>();
        cateList.forEach(cate -> {
            QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
            articleQueryWrapper.eq("cate_id", cate.getId());
            articleQueryWrapper.eq("status", 1);
//            articleQueryWrapper.orderByDesc("recommend");
            articleQueryWrapper.orderByDesc("create_time");
            articleQueryWrapper.last("limit 6");
            List<Article> articleList = articleService.list(articleQueryWrapper);
            if (articleList.size()>0){
                Map<String,Object> cateItem = ObjectUtil.objectToMap(cate);
                cateItem.put("articleList",articleList);
                result.add(cateItem);
            }
        });
        return result;
    }

    public List<Map<String, Object>> getNormalCateArticle(Long siteId) {
        QueryWrapper<Cate> cateQueryWrapper = new QueryWrapper<>();
        cateQueryWrapper.eq("site_id", siteId);
        cateQueryWrapper.eq("pid", 0);
        cateQueryWrapper.eq("status", 1);
        List<Cate> firstCateList = cateService.list(cateQueryWrapper);
        List<Map<String, Object>> result = new ArrayList<>();
        firstCateList.forEach(cate -> {
            List<Article> articleList = getIndexArticle(siteId,cate.getId());
            if (articleList.size()>0){
                Map<String,Object> cateItem = ObjectUtil.objectToMap(cate);
                cateItem.put("articleList",articleList);
                result.add(cateItem);
            }
        });
        return result;
    }

    public List<Article> getIndexArticle(Long siteId, Long pCateId) {
        QueryWrapper<Cate> cateQueryWrapper = new QueryWrapper<>();
        cateQueryWrapper.eq("site_id", siteId);
        cateQueryWrapper.eq("pid", pCateId);
        cateQueryWrapper.eq("status", 1);
//        cateQueryWrapper.orderByDesc("createTime");
        List<Cate> cateList = cateService.list(cateQueryWrapper);
        List<Long> cateIds = new ArrayList<>();
        cateList.forEach(cate -> {
            cateIds.add(cate.getId());
        });
        List<Article> articleList = new ArrayList<>();
        if (cateIds.size() > 0) {
            QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
            articleQueryWrapper.in("cate_id", cateIds);
            articleQueryWrapper.eq("status", 1);
            articleQueryWrapper.orderByDesc("create_time");
            articleQueryWrapper.last("limit 6");
            articleList = articleService.list(articleQueryWrapper);
        }
        return articleList;
    }
}
