package net.guerlab.spring.commons.exception;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import net.guerlab.commons.exception.ApplicationException;

/**
 * 抽象国际化异常信息
 *
 * @author guer
 *
 */
public abstract class AbstractI18nApplicationException extends ApplicationException {

    private static final long serialVersionUID = 1L;

    /**
     * 获取国际化处理后内容
     *
     * @param messageSource
     *            messageSource
     * @return 国际化处理后内容
     */
    public final String getMessage(MessageSource messageSource) {
        String key = getKey();

        if (StringUtils.isBlank(key)) {
            return getDefaultMessage();
        }

        Locale locale = LocaleContextHolder.getLocale();

        return messageSource.getMessage(key, getArgs(), getDefaultMessage(), locale);
    }

    /**
     * 获取国际化key
     *
     * @return 国际化key
     */
    protected String getKey() {
        return "";
    }

    /**
     * 获取国际化参数列表
     *
     * @return 国际化参数列表
     */
    protected Object[] getArgs() {
        return new Object[0];
    }

    /**
     * 获取默认显示错误信息
     *
     * @return 默认显示错误信息
     */
    protected String getDefaultMessage() {
        return getLocalizedMessage();
    }
}
