package com.databasir.api.config;

import com.databasir.api.Routes;
import com.databasir.api.config.security.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DatabasirUserDetailService databasirUserDetailService;

    private final DatabasirAuthenticationEntryPoint databasirAuthenticationEntryPoint;

    private final DatabasirJwtTokenFilter databasirJwtTokenFilter;

    private final DatabasirAuthenticationFailureHandler databasirAuthenticationFailureHandler;

    private final DatabasirAuthenticationSuccessHandler databasirAuthenticationSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http.csrf().disable();
        http.cors();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .failureHandler(databasirAuthenticationFailureHandler)
                .successHandler(databasirAuthenticationSuccessHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/login", Routes.Login.REFRESH_ACCESS_TOKEN).permitAll()
                .antMatchers("/oauth2/apps", "/oauth2/failure", "/oauth2/authorization/*", "/oauth2/login/*").permitAll()
                .antMatchers("/", "/*.html", "/js/**", "/css/**", "/img/**", "/*.ico").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(databasirAuthenticationEntryPoint);

        http.addFilterBefore(
                databasirJwtTokenFilter,
                UsernamePasswordAuthenticationFilter.class
        );
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(databasirUserDetailService)
                .passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    @SuppressWarnings("checkstyle:all")
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
