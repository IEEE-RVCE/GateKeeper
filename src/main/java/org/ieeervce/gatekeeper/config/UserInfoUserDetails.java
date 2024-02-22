package org.ieeervce.gatekeeper.config;

import org.ieeervce.gatekeeper.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * implementation of UserDetails interface by spring security and mapping the data members to the user info
 */
public class UserInfoUserDetails implements UserDetails {
    private final User userInfo;

    public UserInfoUserDetails(User userInfo){
        this.userInfo = userInfo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities;
        authorities = Arrays.stream((userInfo.getRole()).getRoleName().split(",")).map(role->new SimpleGrantedAuthority("ROLE_"+role)).collect(Collectors.toList());
        return authorities;
    }

    @Override
    public String getPassword() {

        return userInfo.getPassword();
    }

    @Override
    public String getUsername() {
        return userInfo.getEmail();
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
        return userInfo.isEnabled();
    }
}
