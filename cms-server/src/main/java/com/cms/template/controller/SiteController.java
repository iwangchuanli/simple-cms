package com.cms.template.controller;


import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cms.template.common.result.CodeMsg;
import com.cms.template.common.result.PageResult;
import com.cms.template.common.result.Result;
import com.cms.template.common.security.authentication.UserPrincipal;
import com.cms.template.entity.Site;
import com.cms.template.entity.User;
import com.cms.template.service.SiteService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 站点设置 前端控制器
 * </p>
 *
 * @author author
 * @since 2021-04-12
 */
@RestController
@RequestMapping("//site")
public class SiteController {
    @Resource
    private SiteService siteService;

    @RequestMapping(value = "/common/item", method = RequestMethod.POST)
    public Result item(@RequestBody JSONObject jsonObject) {
        Long siteId = jsonObject.getLong("siteId");
        Site site = siteService.getById(siteId);
        if (site != null) {
            return Result.success(site);
        } else {
            return Result.error(new CodeMsg(1, "没有找到该站点信息"));
        }
    }

    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    public Result saveOrUpdate(UserPrincipal userPrincipal, @RequestBody JSONObject jsonObject) {
        if (userPrincipal.getRole()>1){
            return Result.error(CodeMsg.USER_AUTH_SHORT);
        }
        Long siteId = jsonObject.getLong("id");
        String name = jsonObject.getStr("name");
        String nickName = jsonObject.getStr("nickName");
        String address = jsonObject.getStr("address");
        String lat = jsonObject.getStr("lat");
        String lng = jsonObject.getStr("lng");
        String logo = jsonObject.getStr("logo");
        String phone = jsonObject.getStr("phone");
        String email = jsonObject.getStr("email");
        String beian = jsonObject.getStr("beian");
        String domain = jsonObject.getStr("domain");
        String banner = jsonObject.getStr("banner");
        Site site = null;
        if (siteId == -1) {
            site = new Site();
        } else {
            site = siteService.getById(siteId);
        }
        site.setName(name);
        site.setNickName(nickName);
        site.setAddress(address);
        site.setLat(lat);
        site.setLng(lng);
        site.setLogo(logo);
        site.setPhone(phone);
        site.setEmail(email);
        site.setBeian(beian);
        site.setDomain(domain);
        site.setBanner(banner);
        siteService.saveOrUpdate(site);
        return Result.success(site);
    }

    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    public Result updateStatus(UserPrincipal userPrincipal, @RequestBody JSONObject jsonObject) {
        if (userPrincipal.getRole()>1){
            return Result.error(CodeMsg.USER_AUTH_SHORT);
        }
        Long siteId = jsonObject.getLong("siteId");
        Integer status = jsonObject.getInt("status");
        Site site = siteService.getById(siteId);
        if (site != null) {
            site.setStatus(status);
            siteService.updateById(site);
            return Result.success(site);
        } else {
            return Result.error(new CodeMsg(1, "没有找到该站点信息"));
        }
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result page(UserPrincipal userPrincipal, @RequestBody JSONObject jsonObject) {
        int page = jsonObject.getInt("page");
        int pageSize = jsonObject.getInt("pageSize");
        String name = jsonObject.getStr("name");
        Integer status = jsonObject.getInt("status");
        Page<Site> sitePage = new Page<>(page, pageSize);
        QueryWrapper<Site> siteQueryWrapper = new QueryWrapper<>();
        if (name != null) {
            siteQueryWrapper.like("name", '%' + name + '%');
        }
        if (status!=-1){
            siteQueryWrapper.eq("status",status);
        }
        sitePage = siteService.page(sitePage, siteQueryWrapper);
        return Result.success(new PageResult(sitePage.getCurrent(), sitePage.getSize(),
                sitePage.getPages(), sitePage.getTotal(), sitePage.getRecords()));
    }

    @RequestMapping(value = "/updateBanner", method = RequestMethod.POST)
    public Result updateBanner(UserPrincipal userPrincipal, @RequestBody JSONObject jsonObject) {
        Long siteId = userPrincipal.getSiteId();
        String banner = jsonObject.getStr("banner");
        Site site = siteService.getById(siteId);
        if (site != null) {
            site.setBanner(banner);
            siteService.updateById(site);
            return Result.success(site);
        } else {
            return Result.error(new CodeMsg(1, "没有找到该站点信息"));
        }
    }

    @RequestMapping(value = "/updateRecommend", method = RequestMethod.POST)
    public Result updateRecommend(UserPrincipal userPrincipal, @RequestBody JSONObject jsonObject) {
        Long siteId = userPrincipal.getSiteId();
        String recommendArticle = jsonObject.getStr("recommendArticle");
        Site site = siteService.getById(siteId);
        if (site != null) {
            site.setRecommendArticle(recommendArticle);
            siteService.updateById(site);
            return Result.success(site);
        } else {
            return Result.error(new CodeMsg(1, "没有找到该站点信息"));
        }
    }

    @RequestMapping(value = "/updateDetail", method = RequestMethod.POST)
    public Result updateDetail(UserPrincipal userPrincipal, @RequestBody JSONObject jsonObject) {
        Long siteId = userPrincipal.getSiteId();
        String description = jsonObject.getStr("description");
        String detail = jsonObject.getStr("detail");
        Site site = siteService.getById(siteId);
        if (site != null) {
            site.setDescription(description);
            site.setDetail(detail);
            siteService.updateById(site);
            return Result.success(site);
        } else {
            return Result.error(new CodeMsg(1, "没有找到该站点信息"));
        }
    }

}
