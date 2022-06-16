package com.mcc.summercamp.security;

import com.mcc.summercamp.configuration.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@Configuration
@Order(200)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private CustomUserDetailsService customUserDetailsService;

  @Autowired
  private RestUnauthorizedEntryPoint restUnauthorizedEntryPoint;

  @Lazy
  @Autowired
  private JwtAuthenticationFilter jwtAuthenticationFilter;

  @Bean
  JwtAuthenticationFilter jwtAuthenticationFilter(
      TokenProvider tokenProvider, CustomUserDetailsService customUserDetailsService) {
    return new JwtAuthenticationFilter(tokenProvider, customUserDetailsService);
  }

  @Bean
  TokenProvider tokenProvider(AppProperties appProperties) {
    return new TokenProvider(appProperties);
  }

  @Bean(BeanIds.AUTHENTICATION_MANAGER)
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  public void configure(AuthenticationManagerBuilder authenticationManagerBuilder)
      throws Exception {
    authenticationManagerBuilder
        .userDetailsService(customUserDetailsService)
        .passwordEncoder(passwordEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.headers().frameOptions().disable();
    http.cors()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .csrf().disable()
        .formLogin().disable()
        .httpBasic().disable()
        .exceptionHandling()
        .authenticationEntryPoint(restUnauthorizedEntryPoint)
        .and()
        .authorizeRequests()
        .antMatchers("/", "/error")
        .permitAll()
        .antMatchers(
            "/auth/**",
            "/oauth2/**",
            "/h2",
            "/h2/**",
            "/h2/*",
            "/auth/login",
            "/auth/register",
            "/actuator/**",
            "/health",
            "/health/*")
        .permitAll()
        .anyRequest()
        .authenticated();

    // Add our custom Token based authentication filter
    http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
