/*
 * Copyright 2018-2021 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.guerlab.spring.web.autoconfigure;

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
        DEFAULT_CONFIG.setAllowedOriginPatterns(Collections.singletonList(CorsConfiguration.ALL));
        DEFAULT_CONFIG.setAllowedHeaders(Collections.singletonList(CorsConfiguration.ALL));
        DEFAULT_CONFIG.setAllowedMethods(Collections.singletonList(CorsConfiguration.ALL));
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
