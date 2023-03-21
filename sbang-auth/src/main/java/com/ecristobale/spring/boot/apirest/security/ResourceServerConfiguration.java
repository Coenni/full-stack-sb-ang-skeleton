package com.ecristobale.spring.boot.apirest.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private final TokenStore tokenStore;

    public ResourceServerConfiguration(final TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @Override
    public void configure(final ResourceServerSecurityConfigurer resources) {
        resources.tokenStore(tokenStore);
    }
}
