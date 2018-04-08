package net.guerlab.spring.cloud.commons.properties;

import java.util.Locale;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * i18n配置
 *
 * @author guer
 *
 */
@Component
@RefreshScope
@ConfigurationProperties("spring.i18n")
public class I18nProperties {

    /**
     * 默认语言
     */
    private Locale defaultLocale = Locale.getDefault();

    /**
     * 语言环境改变拦截器
     */
    private LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();

    /**
     * 返回默认语言
     *
     * @return 默认语言
     */
    public Locale getDefaultLocale() {
        return defaultLocale;
    }

    /**
     * 设置默认语言
     *
     * @param defaultLocale
     *            默认语言
     */
    public void setDefaultLocale(
            Locale defaultLocale) {
        this.defaultLocale = defaultLocale;
    }

    /**
     * 返回语言环境改变拦截器
     *
     * @return 语言环境改变拦截器
     */
    public LocaleChangeInterceptor getLocaleChangeInterceptor() {
        return localeChangeInterceptor;
    }

    /**
     * 设置语言环境改变拦截器
     *
     * @param localeChangeInterceptor
     *            语言环境改变拦截器
     */
    public void setLocaleChangeInterceptor(
            LocaleChangeInterceptor localeChangeInterceptor) {
        this.localeChangeInterceptor = localeChangeInterceptor;
    }

}
