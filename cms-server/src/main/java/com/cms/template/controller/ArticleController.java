package com.cms.template.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cms.template.common.result.CodeMsg;
import com.cms.template.common.result.PageResult;
import com.cms.template.common.result.Result;
import com.cms.template.common.security.authentication.UserPrincipal;
import com.cms.template.common.util.DateTimeUtil;
import com.cms.template.common.util.DateUtils;
import com.cms.template.common.util.ObjectUtil;
import com.cms.template.entity.Article;
import com.cms.template.entity.Cate;
import com.cms.template.service.ArticleService;
import com.cms.template.service.CateService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 文章 前端控制器
 * </p>
 *
 * @author author
 * @since 2021-04-12
 */
@RestController
@RequestMapping("//article")
public class ArticleController {

    @Resource
    private CateService cateService;
    @Resource
    private ArticleService articleService;

    //添加文章 修改
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    public Result add(UserPrincipal userPrincipal, @RequestBody JSONObject requestData) {
        Long id = requestData.getLong("id");
        Long cateId = requestData.getLong("cateId");
        Integer recommend = requestData.getInteger("recommend");
        String title = requestData.getString("title");
        String time = requestData.getString("time");
        String author = requestData.getString("author");
        Integer views = requestData.getInteger("views");
        String description = requestData.getString("description");
        String detail = requestData.getString("detail");
        String header = requestData.getString("header");
        String files = requestData.getString("files");
        Integer status = requestData.getInteger("status");
        Article article = null;
        if (id == -1) {
            article = new Article();
            article.setSiteId(userPrincipal.getSiteId());
            article.setCreateTime(new Timestamp(System.currentTimeMillis()));
        } else {
            article = articleService.getById(id);
        }
        System.out.println(article.toString());
        article.setCateId(cateId);
        article.setRecommend(recommend);
        article.setTitle(title);
        article.setTime(time);
        article.setAuthor(author);
        article.setViews(views);
        article.setDescription(description);
        article.setDetail(detail);
        article.setHeader(header);
        article.setFiles(files);
        article.setStatus(status);
        article.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        articleService.saveOrUpdate(article);
        return Result.success(article);
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Result delete(UserPrincipal userPrincipal, @RequestBody JSONObject requestData) {
        Long id = requestData.getLong("articleId");
        Article article = articleService.getById(id);
        if (article != null) {
            article.setStatus(2);
            article.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            articleService.updateById(article);
            return Result.success(article);
        }
        return Result.error(CodeMsg.ARTICLE_NOT_EXIST);
    }

    @RequestMapping(value = "/common/item", method = RequestMethod.POST)
    public Result item(@RequestBody JSONObject requestData) {
        Long id = requestData.getLong("articleId");
        Article article = articleService.getById(id);
        if (article != null) {
            article.setViews(article.getViews() + 1);
            article.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            articleService.updateById(article);
            QueryWrapper<Article> prevArticleQueryWrapper = new QueryWrapper<>();
            prevArticleQueryWrapper.orderByDesc("id");
            prevArticleQueryWrapper.lt("id",article.getId());
            prevArticleQueryWrapper.last("limit 1");
            Article prev = articleService.getOne(prevArticleQueryWrapper);
            QueryWrapper<Article> nextArticleQueryWrapper = new QueryWrapper<>();
            nextArticleQueryWrapper.orderByAsc("id");
            nextArticleQueryWrapper.gt("id",article.getId());
            nextArticleQueryWrapper.last("limit 1");
            Article next = articleService.getOne(nextArticleQueryWrapper);
            Map<String,Object> result = ObjectUtil.objectToMap(article);
            result.put("prev",prev);
            result.put("next",next);
            return Result.success(result);
        }
        return Result.error(CodeMsg.ARTICLE_NOT_EXIST);
    }

    @RequestMapping(value = "/common/cateArticle", method = RequestMethod.POST)
    public Result list(@RequestBody JSONObject requestData) {
        Long siteId = requestData.getLong("siteId");
        Long pCateId = requestData.getLong("pCateId");
        Integer size = requestData.getInteger("size");
        QueryWrapper<Cate> cateQueryWrapper = new QueryWrapper<>();
        cateQueryWrapper.eq("site_id", siteId);
        cateQueryWrapper.eq("pid", pCateId);
        cateQueryWrapper.orderByDesc("recommend");
        cateQueryWrapper.eq("status", 1);
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
            articleQueryWrapper.last("limit "+size);
            articleList = articleService.list(articleQueryWrapper);
        }
        return Result.success(articleList);
    }

    @RequestMapping(value = "/common/page", method = RequestMethod.POST)
    public Result page(@RequestBody JSONObject requestData) {
        int page = requestData.getInteger("page");
        int pageSize = requestData.getInteger("pageSize");
        Long siteId = requestData.getLong("siteId");
        Long cateId = requestData.getLong("cateId");
        String name = requestData.getString("name");
        Page<Article> articlePage = new Page<>(page, pageSize);
        QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
        if (siteId == null) {
            return Result.error(CodeMsg.ARTICLE_CATE_SITE_ID_NOT_EXIST);
        }
        if (-1 != cateId) {
            articleQueryWrapper.eq("cate_id", cateId);
        }
        articleQueryWrapper.eq("site_id", siteId);
        if (name != null && !name.equals("")) {
            articleQueryWrapper.like("title", '%' + name + '%');
        }
        articleQueryWrapper.ne("status", 2);
        articleQueryWrapper.orderByDesc("time");
        articlePage = articleService.page(articlePage, articleQueryWrapper);
        return Result.success(new PageResult(articlePage.getCurrent(), articlePage.getSize(),
                articlePage.getPages(), articlePage.getTotal(), handleArticleList(articlePage.getRecords())));
    }

    @RequestMapping(value = "updateStatus", method = RequestMethod.POST)
    public Result down(@RequestBody JSONObject requestData) {
        Long id = requestData.getLong("id");
        Article article = articleService.getById(id);
        if (article != null) {
            if (article.getStatus() == 0) {
                article.setStatus(1);
            } else {
                article.setStatus(0);
            }
            article.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            articleService.updateById(article);
            return Result.success(article);
        }
        return Result.error(CodeMsg.ARTICLE_NOT_EXIST);
    }


    private Map<String, Object> handleArticle(Article article) {
        Map<String, Object> data = ObjectUtil.objectToMap(article);
        data.put("cateInfo", cateService.getById(article.getCateId()));
        return data;
    }

    private List<Map<String, Object>> handleArticleList(List<Article> articles) {
        List<Map<String, Object>> result = new ArrayList<>();
        articles.forEach(article -> {
            result.add(handleArticle(article));
        });
        return result;
    }


}
