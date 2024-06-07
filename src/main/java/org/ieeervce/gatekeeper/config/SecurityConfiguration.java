package org.ieeervce.gatekeeper.config;

import jakarta.servlet.http.HttpServletResponse;
import org.ieeervce.gatekeeper.entity.User;
import org.ieeervce.gatekeeper.service.UserInfoUserDetailsService;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
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
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

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
        //HeaderWriterLogoutHandler clearSiteData = new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(ClearSiteDataHeaderWriter.Directive.COOKIES));

        http.formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.failureForwardUrl("/loginStatus/failed").successForwardUrl("/loginStatus/success"))
                .httpBasic(Customizer.withDefaults())

                .authorizeHttpRequests(SecurityConfiguration::getCustomizedHttpAuthorization)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(customizer->customizer.configurationSource(corsConfigurationSource()));

        return http.build();
    }

    private static void getCustomizedHttpAuthorization(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry customizer) {
        customizer

                .requestMatchers(HttpMethod.POST,"/user").hasRole("Admin")
                .requestMatchers(HttpMethod.PUT,"/user").hasRole("Admin")
                .requestMatchers(HttpMethod.DELETE,"/user").hasRole("Admin")
                .requestMatchers("/role").hasRole("Admin")
                .requestMatchers("/society").hasRole("Admin")
                .requestMatchers("/").permitAll()
                .requestMatchers("/loginStatus/**").permitAll()
                .requestMatchers("/logout").permitAll()
                .requestMatchers("/login").permitAll()
                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .anyRequest().authenticated();

    }

    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.applyPermitDefaultValues();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000","https://gate.ieee-rvce.org"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    public static String getRequesterDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}