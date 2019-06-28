package net.guerlab.spring.commons.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;

/**
 * @author guer
 *
 */
@Configuration
public class SecurityAutoconfigure extends WebSecurityConfigurerAdapter {

    private static final CorsConfiguration DEFAULT_CONFIG = new CorsConfiguration();

    private CorsConfiguration config;

    @Autowired(required = false)
    public void setConfig(CorsConfiguration config) {
        this.config = config;
    }

    static {
        DEFAULT_CONFIG.setAllowedHeaders(Collections.singletonList(CorsConfiguration.ALL));
        DEFAULT_CONFIG.setAllowedMethods(Collections.singletonList(CorsConfiguration.ALL));
        DEFAULT_CONFIG.setAllowedOrigins(Collections.singletonList(CorsConfiguration.ALL));
        DEFAULT_CONFIG.setMaxAge(1800L);
        DEFAULT_CONFIG.setAllowCredentials(true);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();

        http.csrf().disable();

        http.cors().configurationSource(request -> config == null ? DEFAULT_CONFIG : config);
    }

}
