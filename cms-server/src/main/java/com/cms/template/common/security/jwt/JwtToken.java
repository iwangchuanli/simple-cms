package com.cms.template.common.security.jwt;


import com.cms.template.common.security.userdetails.MyUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class JwtToken {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;


    private Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            Claims claims = getClaimsFromToken(token);
            created = claims.getIssuedAt();
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    private Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    public List<GrantedAuthority> getAuthorities(String token) {
        List<GrantedAuthority> authorities;
        try {
            Claims claims = getClaimsFromToken(token);
            String strAuthority = (String) claims.get("scope");
            authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(strAuthority);
        } catch (Exception e) {
            authorities = null;
        }
        return authorities;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }

        return claims;
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    private boolean isTokenExpired(String token) {
        Date expirationDate = getExpirationDateFromToken(token);
        if (expirationDate == null) return false;
        return expirationDate.before(new Date());
    }

    public String getUsername(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public Map<String, Object> getDetails(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            return (Map<String, Object>) claims.get("details");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String generateToken(MyUserDetails userDetails) {

        StringBuilder strAuthority = new StringBuilder();
        List<GrantedAuthority> authorities = new ArrayList<>(userDetails.getAuthorities());
        for (GrantedAuthority grantedAuthority : authorities) {
            strAuthority.append(grantedAuthority.getAuthority()).append(",");
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("scope", strAuthority.toString());
        claims.put("details", userDetails.getDetails());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
}
