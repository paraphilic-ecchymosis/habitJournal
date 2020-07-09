// package com.crookedcoder.habitjournal.security;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.core.annotation.Order;
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
// import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;

// //@Configuration
// //@Order(1)
// public class AdminSecurityConfiguration extends WebSecurityConfigurerAdapter {
    
//     // @Override
//     // protected void configure(HttpSecurity http) throws Exception {
//     //     // TODO: These need to actually exist first.
//     //     http.antMatcher("/support/admin/**")
//     //         .addFilter(getDigestAuthFilter()).exceptionHandling()
//     //         .authenticationEntryPoint(getDigestEntryPoint())
//     //         .and().authorizeRequests().antMatchers("/support/admin/**").hasRole("ADMIN");
                
//     // }

//     // private DigestAuthenticationFilter getDigestAuthFilter() throws Exception {
//     //     DigestAuthenticationFilter filter = new DigestAuthenticationFilter();
//     //     filter.setUserDetailsService(userDetailsServiceBean());
//     //     filter.setAuthenticationEntryPoint(getDigestEntryPoint());
//     //     return filter;
//     // }

//     // private DigestAuthenticationEntryPoint getDigestEntryPoint() {
//     //     DigestAuthenticationEntryPoint entryPoint = new DigestAuthenticationEntryPoint();
//     //     entryPoint.setRealmName("admin-digest-realm");
//     //     // TODO: There's a better way.
//     //     entryPoint.setKey("fjkf33DD312_+");
//     //     return entryPoint;
//     // }

//     // @Override
//     // protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//     //     // TODO: There's a better way.
//     //     auth.inMemoryAuthentication()
//     //     .withUser("user")
//     //         .password("password")
//     //         .roles("USER")
//     //     .and()
//     //         .withUser("admin")
//     //             .password("password2")
//     //             .roles("ADMIN");
//     // }

//     // @Override
//     // @Bean
//     // public UserDetailsService userDetailsServiceBean() throws Exception {
//     //     return super.userDetailsServiceBean();
//     // }

// }