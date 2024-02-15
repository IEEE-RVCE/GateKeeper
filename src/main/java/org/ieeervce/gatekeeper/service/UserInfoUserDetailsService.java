package org.ieeervce.gatekeeper.service;

import org.ieeervce.gatekeeper.config.UserInfoUserDetails;
import org.ieeervce.gatekeeper.entity.User;
import org.ieeervce.gatekeeper.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserInfoUserDetailsService implements UserDetailsService {
    /**
     * implementation of UserDetailsService by spring security
     */
    @Autowired
    private UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        Optional<User> userInfo = repository.findByEmail(userEmail);
        return userInfo.map(UserInfoUserDetails::new)
                .orElseThrow(()-> new UsernameNotFoundException("User Email not found" + userEmail));
    }
}
