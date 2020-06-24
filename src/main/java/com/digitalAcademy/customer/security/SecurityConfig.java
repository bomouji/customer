package com.digitalAcademy.customer.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${spring.security.user.name}")
    private String username;

    @Value("${spring.security.user.password}")
    private String password;

    @Value("${spring.security.user.roles}")
    private String roles;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated()
                .and().csrf()
                .disable()
                .httpBasic();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        User.UserBuilder user = User.withDefaultPasswordEncoder();
        InMemoryUserDetailsManager manager =  new InMemoryUserDetailsManager();
        manager.createUser(user.username(username)
                .password(password)
                .roles(roles).build());
        manager.createUser(user.username("bom")
                .password("teerayuth")
                .roles("junior").build());
        System.out.println(manager.loadUserByUsername(username));
        System.out.println(manager.loadUserByUsername("bom"));
        return manager;

    }
}
