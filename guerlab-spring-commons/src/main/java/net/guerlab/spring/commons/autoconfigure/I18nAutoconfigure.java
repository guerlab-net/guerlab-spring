package net.guerlab.spring.commons.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import net.guerlab.spring.commons.properties.I18nProperties;

/**
 * 国际化配置
 *
 * @author guer
 *
 */
@Configuration
@EnableConfigurationProperties(I18nProperties.class)
public class I18nAutoconfigure {

    @Autowired
    private I18nProperties properties;

    /**
     * create LocaleResolver
     * 
     * @return LocaleResolver
     */
    @Bean
    @ConditionalOnMissingBean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(properties.getDefaultLocale());
        return slr;
    }

    /**
     * create LocaleChangeInterceptor
     * 
     * @return LocaleChangeInterceptor
     */
    @Bean
    @ConditionalOnMissingBean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = properties.getLocaleChangeInterceptor();

        return lci != null ? lci : new LocaleChangeInterceptor();
    }
}
