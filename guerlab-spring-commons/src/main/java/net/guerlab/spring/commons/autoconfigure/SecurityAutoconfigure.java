package net.guerlab.spring.commons.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;

/**
 * @author guer
 *
 */
@Configuration
public class SecurityAutoconfigure extends WebSecurityConfigurerAdapter {

    @Autowired(required = false)
    private CorsConfiguration config;

    private static final CorsConfiguration DEFAULT_CONFIG = new CorsConfiguration();

    static {
        DEFAULT_CONFIG.addAllowedHeader(CorsConfiguration.ALL);
        DEFAULT_CONFIG.addAllowedMethod(CorsConfiguration.ALL);
        DEFAULT_CONFIG.addAllowedOrigin(CorsConfiguration.ALL);
        DEFAULT_CONFIG.setMaxAge(1800L);
        DEFAULT_CONFIG.setAllowCredentials(true);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.cors().configurationSource(request -> config == null ? DEFAULT_CONFIG : config);
    }

}
