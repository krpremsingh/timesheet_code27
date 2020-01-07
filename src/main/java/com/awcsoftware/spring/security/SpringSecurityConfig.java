package com.awcsoftware.spring.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.awcsoftware.spring.security.auth.LoginFilter;
import com.awcsoftware.spring.security.auth.LogoutFilter;
import com.awcsoftware.spring.security.auth.TokenFilter;
import com.awcsoftware.spring.security.auth.user.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = "com.awcsoftware")
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	public static final String CREDENTIAL_BASED_LOGIN_ENTRY_POINT = "/auth/login";
	public static final String TOKEN_BASED_AUTH_ENTRY_POINT = "/**";
	public static final String LOGOUT_ENTRY_POINT = "/auth/logout";
	public static final String FORGOT_PASSWORD = "/employee/forgotPassword";
	public static final String CONFIRM_RESET = "/employee/confirm-reset";
	public static final String CHANGE_PASSWORD = "/employee/changePassword";
	//public static final String WITHOUT_TOKEN_BASED_AUTH_ENTRY_POINT="/*";
		
	@Bean
	protected LoginFilter getLoginFilter() throws Exception {
		LoginFilter filter = new LoginFilter(CREDENTIAL_BASED_LOGIN_ENTRY_POINT);
		//filter.setRequiresAuthenticationRequestMatcher(new OrRequestMatcher(new AntPathRequestMatcher("/auth/forgotPassword")));
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

	@Bean
	AccessDeniedHandler accessDeniedHandler() {
		return new AccessDeniedHandler();
	}
	

/*	
    @Bean
    public TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler();
    }*/

/*	@Bean
	public AuthenticationFailureHandler customAuthenticationFailureHandler() {
		ExceptionMappingAuthenticationFailureHandler exceptionMappingAuthenticationFailureHandler = new ExceptionMappingAuthenticationFailureHandler();
		Map<Object, Object> map = new HashMap<>();
		map.put("org.springframework.security.authentication.CredentialsExpiredException", "/resetPassword");

		exceptionMappingAuthenticationFailureHandler.setExceptionMappings(map);

		exceptionMappingAuthenticationFailureHandler.setRedirectStrategy(new RedirectStrategy() {
			@Override
			public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url)
					throws IOException {
				response.sendRedirect(request.getContextPath() + url);
			}
		});

		return exceptionMappingAuthenticationFailureHandler;
	}*/

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		         http.csrf().disable()
		        .addFilterBefore(getLoginFilter(), UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(getLogoutFilter(), UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(getTokenFilter(), UsernamePasswordAuthenticationFilter.class)
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeRequests()
				.antMatchers(FORGOT_PASSWORD).permitAll()
				.antMatchers(CONFIRM_RESET).permitAll()
				.antMatchers(CHANGE_PASSWORD).permitAll()
				.and().authorizeRequests()
				.antMatchers(CREDENTIAL_BASED_LOGIN_ENTRY_POINT).permitAll()
				.antMatchers(LOGOUT_ENTRY_POINT)
				.authenticated().anyRequest()
				.authenticated()
				.and()
				.exceptionHandling()
				.accessDeniedHandler(accessDeniedHandler())
				.and()
				.logout()
				.clearAuthentication(true)
				.logoutSuccessUrl(LOGOUT_ENTRY_POINT).invalidateHttpSession(true);

	}
	
	 @Override
	 public void configure(final WebSecurity webSecurity) {
	  webSecurity.ignoring().antMatchers("/employee/**");
	 }
}
