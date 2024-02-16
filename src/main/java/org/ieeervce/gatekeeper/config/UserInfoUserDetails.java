package org.ieeervce.gatekeeper.config;

import org.ieeervce.gatekeeper.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * implementation of UserDetails interface by spring security and mapping the data members to the user info
 */
public class UserInfoUserDetails implements UserDetails {
    private  String userName;
    private  String password;
    private  boolean isEnabled;
    private List<GrantedAuthority> authorities;

    public UserInfoUserDetails(User userInfo){
        userName = userInfo.getEmail();
        password = userInfo.getPassword();
        isEnabled = userInfo.isEnabled();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
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
        return isEnabled;
    }
}
