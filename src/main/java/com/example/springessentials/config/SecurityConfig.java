package com.example.springessentials.config;

import com.example.springessentials.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

import static com.example.springessentials.config.SecurityConstants.*;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailService customUserDetailService;

//WITHOUT JWT
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers(HttpMethod.DELETE).hasAuthority("ROLE_ADMIN")
//                .anyRequest().authenticated()
//                .and()
//                .httpBasic()
//                .and()
//                .csrf().disable()
//        ;
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
                .and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.GET, SIGNUP_URL).permitAll()
                .antMatchers(HttpMethod.DELETE).hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), customUserDetailService));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService).passwordEncoder(new BCryptPasswordEncoder());
    }

//IN MEMORY AUTHENTICATION
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("gustavo").password("{noop}secret").roles("USER")
//                .and()
//                .withUser("admin").password("{noop}secret").roles("USER", "ADMIN");
//    }
}
