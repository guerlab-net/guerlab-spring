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
package net.guerlab.spring.web.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

/**
 * i18n配置
 *
 * @author guer
 */
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
    public void setDefaultLocale(Locale defaultLocale) {
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
    public void setLocaleChangeInterceptor(LocaleChangeInterceptor localeChangeInterceptor) {
        this.localeChangeInterceptor = localeChangeInterceptor;
    }

}
