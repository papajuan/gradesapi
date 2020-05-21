package com.urfu.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author aperminov
 * 21.02.2020
 */
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${spring.security.user.name}")
    private String user;

    @Value("${spring.security.user.password}")
    private String password;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/technologyCards**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/scores**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/**").denyAll()
                .antMatchers(HttpMethod.PATCH, "/**").denyAll()
                .antMatchers(HttpMethod.DELETE, "/**").denyAll()
                .and()
                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth
                .inMemoryAuthentication()
                .withUser(user)
                .password(encoder.encode(password))
                .roles("ADMIN");
    }
}
