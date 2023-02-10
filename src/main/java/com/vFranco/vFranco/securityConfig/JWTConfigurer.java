package com.vFranco.vFranco.securityConfig;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.vFranco.vFranco.filter.JWTFilter;
import com.vFranco.vFranco.provider.JwtProvider;
import org.springframework.stereotype.Component;

//Especifica los aspectos del jwt
@Component
@Configuration
public class JWTConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    
    private final JwtProvider jwtProvider;

    public static final String AUTHORIZATION_HEADER = "Authorization";
  
    public JWTConfigurer(JwtProvider jwtProvider) {
      this.jwtProvider = jwtProvider;
    }
    
    //Cuando viene una petición http la manda al filter, según la configuración especificada
    @Override
    public void configure(HttpSecurity http) throws Exception {
      JWTFilter customFilter = new JWTFilter(jwtProvider);
      http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public FilterRegistrationBean<JWTFilter> filterRegistrationBean() {
        FilterRegistrationBean<JWTFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JWTFilter(this.jwtProvider));
        registrationBean.addUrlPatterns("/api/*");
        return registrationBean;
    }
  }