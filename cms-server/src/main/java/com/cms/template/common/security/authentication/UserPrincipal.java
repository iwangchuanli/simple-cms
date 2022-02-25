package com.cms.template.common.security.authentication;

import com.cms.template.common.exception.BizException;
import com.cms.template.common.result.CodeMsg;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Map;


/**
 * 每一个Provider都会通UserDetailsService和UserDetail来返回一个
 * 以UsernamePasswordAuthenticationToken实现的带用户名和密码以及权限的Authentication
 * <p>
 * 重写
 */
public class UserPrincipal extends UsernamePasswordAuthenticationToken {


    public UserPrincipal(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public UserPrincipal(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public Map<String, Object> detailHandler() {
        return (Map<String, Object>) getDetails();
    }

    public Long getId() {
        return Long.parseLong(getValue("id").toString());
    }

    public Integer getStatus() {
        return Integer.parseInt(getValue("status").toString());
    }

    public String getUsername() {
        return (String) getValue("username");
    }

    public String getPhone() {
        return (String) getValue("phone");
    }

    public String getEmail() {
        return (String) getValue("email");
    }

    public String getUserSn() {
        return (String) getValue("userSn");
    }

    public Integer getRole() {
        return Integer.parseInt(getValue("role").toString());
    }

    public Long getSiteId() {
        return Long.parseLong(getValue("siteId").toString());
    }

    public Object getValue(String key) {
        Map<String, Object> details = (Map<String, Object>) getDetails();
        Object value = details.get(key);
        if (value == null) {
            throw new BizException(CodeMsg.BIZ_EXCEPTION_DETAILS_NULL_VALUE);
        } else {
            return value;
        }
    }
}
