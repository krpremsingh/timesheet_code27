package com.awcsoftware.spring.security;

import org.springframework.context.annotation.Bean;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.awcsoftware.spring.security.auth.LoginFilter;
import com.awcsoftware.spring.security.auth.LogoutFilter;
import com.awcsoftware.spring.security.auth.TokenFilter;


@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = "com.awcsoftware")

public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	public static final String CREDENTIAL_BASED_LOGIN_ENTRY_POINT = "/auth/login";
    public static final String TOKEN_BASED_AUTH_ENTRY_POINT = "/**";
    public static final String LOGOUT_ENTRY_POINT = "/auth/logout";
    
    @Bean
    protected LoginFilter getLoginFilter() throws Exception {
        LoginFilter filter = new LoginFilter(CREDENTIAL_BASED_LOGIN_ENTRY_POINT);
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }
    
    @Bean
    protected LogoutFilter getLogoutFilter() throws Exception {
    	LogoutFilter filter = new LogoutFilter(LOGOUT_ENTRY_POINT);
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }
	
	@Bean
    protected TokenFilter getTokenFilter() throws Exception {
		TokenFilter filter = new TokenFilter(TOKEN_BASED_AUTH_ENTRY_POINT);
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .csrf().disable()
	        .sessionManagement()
	        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	        .and()
            	.authorizeRequests()
				.antMatchers(CREDENTIAL_BASED_LOGIN_ENTRY_POINT).permitAll()
				.antMatchers(LOGOUT_ENTRY_POINT).authenticated()
				.anyRequest().authenticated()
			.and()
         	.addFilterBefore(getLoginFilter(), UsernamePasswordAuthenticationFilter.class)
         	.addFilterBefore(getLogoutFilter(), UsernamePasswordAuthenticationFilter.class)
        	.addFilterBefore(getTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
