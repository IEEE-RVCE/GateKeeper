package org.ieeervce.gatekeeper.config;

import org.ieeervce.gatekeeper.entity.User;
import org.ieeervce.gatekeeper.service.UserInfoUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserInfoUserDetailsService();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.formLogin(Customizer.withDefaults()).httpBasic(Customizer.withDefaults()).authorizeHttpRequests(SecurityConfiguration::getCustomizedHttpAuthorization).csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable);
        return http.build();
    }
    private static void getCustomizedHttpAuthorization(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry customizer) {
        customizer
                .requestMatchers("/user").hasRole("ADMIN")
                .requestMatchers("/role").hasRole("ADMIN")
                .requestMatchers("/society").hasRole("ADMIN")
                .anyRequest().authenticated();

    }


public static String getRequesterDetails()
{
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication.getName();
}
}
