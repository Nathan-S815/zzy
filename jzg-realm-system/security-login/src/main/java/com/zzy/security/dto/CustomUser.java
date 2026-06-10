package com.zzy.security.dto;

import com.zzy.security.lib.entity.RoleInfo;
import com.zzy.security.lib.entity.UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomUser implements UserDetails {


    private Integer userId;
    private String userName;
    private String passWord;
    private List<String> auths;
    private Set<String> roleCode;
    private boolean isEnable;

    public CustomUser(int userId, String userName, String passWord, List<String> auths, boolean isEnabled) {
        this.userId = userId;
        this.userName = userName;
        this.passWord = passWord;
        this.auths = auths;
    }

    public CustomUser(){}


    public CustomUser(UserInfo user, List<String> authorities) {
        this.userId = user.getId();
        this.userName = user.getUserName();
        this.passWord = user.getPassWord();
        this.auths = authorities;
        this.isEnable = user.getIsEnable()==1;
    }

    public CustomUser(UserInfo user, List<RoleInfo> authorities,boolean flag) {
        this.userId = user.getId();
        this.userName = user.getUserName();
        this.passWord = user.getPassWord();
        this.auths = authorities.stream().map(o->o.getRoleName()).collect(Collectors.toList());
        this.roleCode = authorities.stream().map(o->o.getRoleCode()).collect(Collectors.toSet());
        this.isEnable = user.getIsEnable()==1;
    }


    public CustomUser(String username, List<String> roles) {
        this.userName = username;
        this.auths = roles;
    }

    public CustomUser(int userId, String username, List<String> roles) {
        this.userId = userId;
        this.userName = username;
        this.auths = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> li = new ArrayList<>();
        auths.forEach(ob->li.add(new SimpleGrantedAuthority(ob)));
        return li;
    }

    @Override
    public String getPassword() {
        return passWord;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnable;
    }


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }


    public List<String> AuthToListStr(){
        return auths;
    }

    public List<String> getAuths() {
        auths = getAuthorities().stream().map(x->x.getAuthority()).collect(Collectors.toList());
        return auths;
    }

    public Set<String> getRoleCode(){
        return roleCode;
    }
}
