package com.cms.template.controller;


import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cms.template.common.result.CodeMsg;
import com.cms.template.common.result.Result;
import com.cms.template.common.security.authentication.UserPrincipal;
import com.cms.template.entity.Article;
import com.cms.template.entity.Cate;
import com.cms.template.service.ArticleService;
import com.cms.template.service.CateService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 分类 前端控制器
 * </p>
 *
 * @author author
 * @since 2021-04-12
 */
@RestController
@RequestMapping("//cate")
public class CateController {

    @Resource
    private CateService cateService;
    @Resource
    private ArticleService articleService;

    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    public Result add( UserPrincipal userPrincipal, @RequestBody JSONObject requestData) {
        Long id = requestData.getLong("id");
        Long siteId = requestData.getLong("siteId");
        String name = requestData.getString("name");
        Long pid = requestData.getLong("pid");
        Integer recommend = requestData.getInteger("recommend");
        Integer sort = requestData.getInteger("sort");
        Integer status = requestData.getInteger("status");
        Cate cate = null;
        if (id == -1) {
            cate=new Cate();
        }else {
            cate = cateService.getById(id);
        }
        cate.setSiteId(siteId);
        cate.setName(name);
        if (pid!=null){
            cate.setPid(pid);
        }
        cate.setRecommend(recommend);
        cate.setSort(sort);
        cate.setStatus(status);
        cateService.saveOrUpdate(cate);
        return Result.success(cate);
    }


    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Result delete(@RequestBody JSONObject requestData) {
        Long cateId = requestData.getLong("cateId");
        Cate cate = cateService.getById(cateId);
        if (cate != null) {
            cate.setStatus(2);
            cateService.updateById(cate);
            return Result.success(cate);
        }
        return Result.error(CodeMsg.ARTICLE_CATE_NOT_EXIST);
    }

    @RequestMapping(value = "/common/tree", method = RequestMethod.POST)
    public Result tree(@RequestBody JSONObject requestData) {
        Long siteId = requestData.getLong("siteId");
        if (siteId == null) {
            return Result.error(CodeMsg.ARTICLE_CATE_SITE_ID_NOT_EXIST);
        }
        QueryWrapper<Cate> cateQueryWrapper = new QueryWrapper<>();
        cateQueryWrapper.eq("site_id", siteId);
        cateQueryWrapper.ne("status", 2);
        List<Cate> cateList = cateService.list(cateQueryWrapper);
        return Result.success(handleCateTree(cateList));
    }

    private List<Tree<String>> handleCateTree(List<Cate> cateList) {
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setChildrenKey("childs");
        treeNodeConfig.setDeep(2);
        treeNodeConfig.setIdKey("id");
        List<Tree<String>> build = TreeUtil.build(cateList, "0", treeNodeConfig,
                (treeNode, tree) -> {
                    tree.setId(treeNode.getId().toString());
                    tree.setParentId(treeNode.getPid().toString());
                    tree.setName(treeNode.getName());
                    // 扩展属性
                    tree.putExtra("siteId", treeNode.getSiteId());
                    tree.putExtra("pid", treeNode.getPid());
                    tree.putExtra("recommend",treeNode.getRecommend());
                    tree.putExtra("sort", treeNode.getSort());
                    tree.putExtra("status", treeNode.getStatus());
                });
        return build;
    }

}
