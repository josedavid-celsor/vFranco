package com.vFranco.vFranco.securityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.vFranco.vFranco.provider.JwtProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebSecurityConfigurer<WebSecurity> {

  @Autowired
  JwtProvider jwtProvider;

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  public SecurityConfig(JwtProvider jwtProvider) {
    this.jwtProvider = jwtProvider;
  }

  /*
   * @Override
   * protected void configure(HttpSecurity http) throws Exception {
   * http
   * .csrf().disable()
   * .cors().and()
   * .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
   * .and()
   * .authorizeRequests()
   * .antMatchers("/login").permitAll()
   * .anyRequest().authenticated()
   * .and()
   * .apply(new JWTConfigurer(jwtProvider));
   * }
   */

  @Override
  public void init(WebSecurity builder) throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void configure(WebSecurity web) {
    // Configure web security settings
    web.ignoring().antMatchers(
        "/v2/api-docs",
        "/configuration/ui",
        "/swagger-resources/**",
        "/configuration/security",
        "/swagger-ui.html",
        "/webjars/**");

    // Add the security filter chain
    web.addSecurityFilterChainBuilder(builder -> builder
        .addFilterBefore(new JWTFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
        .authenticationProvider(authenticationProvider())
        .authorizeExchange()
        .pathMatchers("/api/users/signup").permitAll()
        .pathMatchers("/api/users/login").permitAll()
        .pathMatchers("/api/users/**").authenticated()
        .anyExchange().permitAll()
        .and()
        .build());
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
        .withUser("user")
        .password("password")
        .roles("USER");
  }

}
