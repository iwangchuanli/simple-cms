package com.cms.template.controller;


import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cms.template.common.result.CodeMsg;
import com.cms.template.common.result.PageResult;
import com.cms.template.common.result.Result;
import com.cms.template.common.security.authentication.UserPrincipal;
import com.cms.template.common.security.jwt.JwtToken;
import com.cms.template.common.security.userdetails.MyUserDetails;
import com.cms.template.common.sys.sysConst;
import com.cms.template.common.util.ObjectUtil;
import com.cms.template.entity.Site;
import com.cms.template.entity.User;
import com.cms.template.service.SiteService;
import com.cms.template.service.UserService;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author author
 * @since 2021-04-12
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private SiteService siteService;
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private JwtToken jwtToken;
    @Resource
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/auth/login", method = RequestMethod.POST)
    public Result login(@RequestBody JSONObject jsonObject) {
        String username = jsonObject.getStr("username");
        String password = jsonObject.getStr("password");

        MyUserDetails myUserDetails = (MyUserDetails) authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)).getPrincipal();
        String token = jwtToken.generateToken(myUserDetails);
        Map<String, Object> data = new HashMap<>();
        data.put("details", myUserDetails.getDetails());
        data.put(sysConst.TOKEN, token);
        return Result.success(data);
    }

    @RequestMapping(value = "/auth/register", method = RequestMethod.POST)
    public Result register(@RequestBody JSONObject jsonObject) {
        String username = jsonObject.getStr("username");
        String password = jsonObject.getStr("password");
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        userService.save(user);
        return Result.success(true);
    }

    @PostMapping("/modifyPwd")
    public Result modifyPwd(UserPrincipal userPrincipal,@RequestBody JSONObject jsonObject) {
        Long userId = jsonObject.getLong("userId");
        User user = userService.getById(userId);
        String oldPwd = jsonObject.getStr("oldPwd");
        String newPwd = jsonObject.getStr("newPwd");
        if (!passwordEncoder.matches(oldPwd,user.getPassword())) {
            return Result.error(new CodeMsg(1,"原密码错误"));
        }
        user.setPassword(passwordEncoder.encode(newPwd));
        userService.saveOrUpdate(user);
        return Result.success(true);
    }

    @RequestMapping(value = "/info/item", method = RequestMethod.POST)
    public Result infoItem(UserPrincipal userPrincipal, @RequestBody JSONObject jsonObject) {
        Long userId = jsonObject.getLong("userId");
        if (userId == null){
            userId = userPrincipal.getId();
        }
        User user = userService.getById(userId);
        if (user!=null){
            user.setPassword("");
            return Result.success(user);
        }else {
            return Result.error(CodeMsg.USER_NOT_EXIST);
        }
    }
    @RequestMapping(value = "/info/page", method = RequestMethod.POST)
    public Result infoPage(UserPrincipal userPrincipal, @RequestBody JSONObject jsonObject) {
        int page = jsonObject.getInt("page");
        int pageSize = jsonObject.getInt("pageSize");
        String username = jsonObject.getStr("username");
        Integer status = jsonObject.getInt("status");
        Page<User> userPage = new Page<>(page, pageSize);
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        if (username != null) {
            userQueryWrapper.like("username", '%' + username + '%');
        }
        if (status!=-1){
            userQueryWrapper.eq("status",status);
        }
        userPage = userService.page(userPage, userQueryWrapper);
        return Result.success(new PageResult(userPage.getCurrent(), userPage.getSize(),
                userPage.getPages(), userPage.getTotal(), handleUserPage(userPage.getRecords())));
    }

    private List<Map<String,Object>> handleUserPage(List<User> users){
        List<Map<String,Object>> result = new ArrayList<>();
        for(User user:users){
            Map<String,Object> userItem = ObjectUtil.objectToMap(user);
            Site site = siteService.getById(user.getSiteId());
            userItem.put("password","");
            userItem.put("siteInfo",site);
            result.add(userItem);
        }
        return result;
    }

    @RequestMapping(value = "/info/update", method = RequestMethod.POST)
    public Result infoUpdate(UserPrincipal userPrincipal, @RequestBody JSONObject jsonObject) {
        String nickName = jsonObject.getStr("nickName");
        String avatarUrl = jsonObject.getStr("avatarUrl");
        String email = jsonObject.getStr("email");
        String phone = jsonObject.getStr("phone");
        User user = userService.getById(userPrincipal.getId());
        user.setNickName(nickName);
        user.setAvatarUrl(avatarUrl);
        user.setEmail(email);
        user.setPhone(phone);
        userService.updateById(user);
        user.setPassword("");
        return Result.success(user);
    }

    @RequestMapping(value = "/info/role", method = RequestMethod.POST)
    public Result infoRole(UserPrincipal userPrincipal, @RequestBody JSONObject jsonObject) {
        Long userId = jsonObject.getLong("id");
        Integer role = jsonObject.getInt("role");
        Long siteId = jsonObject.getLong("siteId");
        User user = userService.getById(userId);
        if (userPrincipal.getRole()<=1 && userPrincipal.getRole()<role){
            user.setRole(role);
            user.setSiteId(siteId);
            userService.updateById(user);
            return Result.success(true);
        }else {
            return Result.error(CodeMsg.USER_AUTH_SHORT);
        }
    }
}
