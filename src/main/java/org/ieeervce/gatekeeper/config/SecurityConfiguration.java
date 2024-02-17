package org.ieeervce.gatekeeper.config;

import org.ieeervce.gatekeeper.service.UserInfoUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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
                .requestMatchers(HttpMethod.POST,"/user").permitAll()
                .requestMatchers(HttpMethod.POST,"/role").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,"/role/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE,"/role/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST,"/society").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,"/society/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE,"/society/**").hasRole("ADMIN")
                .anyRequest().permitAll();
    }

}
