package com.cms.template.common.security.userdetails;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Map;

/**
 *  MyUserDetails 实体
 */
public class MyUserDetails extends User {

    private Map<String, Object> details;

    public MyUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, Map<String, Object> details) {
        super(username, password, authorities);
        this.details = details;
    }

    public void setDetails(Map<String, Object> details) {
        this.details = details;
    }

    public Map<String, Object> getDetails() {
        return details;
    }
}
