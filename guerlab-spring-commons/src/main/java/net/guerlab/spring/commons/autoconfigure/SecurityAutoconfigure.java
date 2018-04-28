package net.guerlab.spring.commons.autoconfigure;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author guer
 *
 */
@Configuration
public class SecurityAutoconfigure extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(
            HttpSecurity http) throws Exception {
        // @formatter:off
        http.cors().disable();
        // @formatter:on
    }

}
