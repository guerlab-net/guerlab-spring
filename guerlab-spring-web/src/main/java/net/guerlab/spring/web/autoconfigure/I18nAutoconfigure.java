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

import net.guerlab.spring.web.properties.I18nProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * 国际化配置
 *
 * @author guer
 *
 */
@Configuration
@ConditionalOnClass({ LocaleResolver.class, LocaleChangeInterceptor.class, SessionLocaleResolver.class
})
@EnableConfigurationProperties(I18nProperties.class)
public class I18nAutoconfigure {

    /**
     * create LocaleResolver
     *
     * @param properties I18nProperties
     *
     * @return LocaleResolver
     */
    @Bean
    @ConditionalOnMissingBean
    public LocaleResolver localeResolver(I18nProperties properties) {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(properties.getDefaultLocale());
        return slr;
    }

    /**
     * create LocaleChangeInterceptor
     *
     * @param properties I18nProperties
     *
     * @return LocaleChangeInterceptor
     */
    @Bean
    @ConditionalOnMissingBean
    public LocaleChangeInterceptor localeChangeInterceptor(I18nProperties properties) {
        LocaleChangeInterceptor lci = properties.getLocaleChangeInterceptor();

        return lci != null ? lci : new LocaleChangeInterceptor();
    }
}
