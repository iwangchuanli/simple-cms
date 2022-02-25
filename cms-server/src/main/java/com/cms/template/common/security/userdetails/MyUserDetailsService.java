package com.cms.template.common.security.userdetails;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cms.template.common.exception.BizException;
import com.cms.template.common.result.CodeMsg;
import com.cms.template.entity.User;
import com.cms.template.service.UserService;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * MyUserDetailsService 服务实现
 */
@Component
public class MyUserDetailsService implements UserDetailsService {

    @Resource
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username);
        User user = userService.getOne(userQueryWrapper);
        if (user != null) {
            Collection authorities = new ArrayList();
            if (user.getRole()==0){
                authorities=AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_SYSTEM");
            }else if(user.getRole()==1){
                authorities=AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN");
            }else if(user.getRole()==2){
                authorities=AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_EDIT");
            }else {
                authorities=AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
            }
            Map<String, Object> details = new HashMap<>();
            details.put("id", user.getId());
            details.put("status", user.getStatus());
            details.put("username", user.getUsername());
            details.put("nickName", user.getNickName());
            details.put("avatarUrl", user.getAvatarUrl());
            details.put("phone", user.getPhone());
            details.put("email", user.getEmail());
            details.put("role", user.getRole());
            details.put("siteId", user.getSiteId());
            return new MyUserDetails(username, user.getPassword(), authorities, details);
        } else {
            throw new BizException(CodeMsg.USER_NOT_EXIST);
        }
    }

}

