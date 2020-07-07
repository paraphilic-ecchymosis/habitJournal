package com.crookedcoder.habitjournal.config;

import java.util.Properties;

import com.crookedcoder.habitjournal.filters.TotpAuthenticationFilter;
import com.crookedcoder.habitjournal.model.Authorities;
import com.crookedcoder.habitjournal.security.AdditionalAuthenticationDetailsSource;
import com.crookedcoder.habitjournal.userdetails.AdditionalAuthenticationProvider;
import com.crookedcoder.habitjournal.userdetails.AuthenticationSuccessHandlerImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;



@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
	private AdditionalAuthenticationProvider additionalProvider;
	@Autowired
	private TotpAuthenticationFilter totpAuthFilter;
	@Autowired
	private AccessDeniedHandler accessDeniedHandler;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private PersistentTokenRepository persistentTokenRepository;
	@Autowired
	@Qualifier("oauth2authSuccessHandler")
	private AuthenticationSuccessHandler oauth2authSuccessHandler;

    // protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    //     auth.jdbcAuthentication().withDefaultSchema().withUser("jacob").password("password")
    //         .dataSource(this.datasource);
    // }



    
    @Override
	protected void configure(HttpSecurity http) throws Exception {
        //@formatter:off
		http
			.addFilterBefore(totpAuthFilter, UsernamePasswordAuthenticationFilter.class)
			.exceptionHandling().accessDeniedHandler(accessDeniedHandler)
			.and()
			.formLogin().loginPage("/login")
				.successHandler(new AuthenticationSuccessHandlerImpl())
				.failureUrl("/login-error")
				.authenticationDetailsSource(new AdditionalAuthenticationDetailsSource())
				.and()
			.rememberMe()
				.authenticationSuccessHandler(new AuthenticationSuccessHandlerImpl())
				.tokenRepository(persistentTokenRepository)
				.and()
			.oauth2Login()
				.loginPage("/login")
				.successHandler(oauth2authSuccessHandler)
				.and()
			.authorizeRequests()
				.mvcMatchers("/register","/login","/login-error",
						"/login-verified","/verify/email","/qrcode").permitAll()
				.mvcMatchers("/totp-login","/totp-login-error").hasAuthority(Authorities.TOTP_AUTH_AUTHORITY)
				.mvcMatchers("/journal/**","/account/**").hasRole("USER")
				.mvcMatchers("/support/**").hasAnyRole("USER","ADMIN")
				.mvcMatchers("/support/admin/**").access("isFullyAuthenticated() and hasRole('ADMIN')")
				.mvcMatchers("/api/users").hasRole("ADMIN")
				.mvcMatchers("/api/users/{username}/journal")
					//.access("hasRole('ADMIN') || hasRole('USER') && #username == principal.username")
					.access("@isJournalOwnerOrAdmin.check(#username)")
				.anyRequest().denyAll();
        
		//@formatter:on
	}
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/webjars/**");
    }
    
    @Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(additionalProvider);
    }
    
    @Bean
	public RedirectStrategy getRedirectStrategy() {
        return new DefaultRedirectStrategy();
    }
    
    @Bean(name="simpleMappingExceptionResolver")
	public SimpleMappingExceptionResolver createExceptionResolver() {
        SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
		Properties properties = new Properties();
		properties.setProperty("InvalidTOTPVerificationCode", "totp-login-error");
		resolver.setExceptionMappings(properties);
		resolver.setDefaultErrorView("error");
		return resolver;
    }
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
        DelegatingPasswordEncoder encoder =  (DelegatingPasswordEncoder)PasswordEncoderFactories.createDelegatingPasswordEncoder();
		return encoder;	
	}	
}