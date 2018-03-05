package net.guerlab.spring.cloud.commons.exception;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * @author guer
 *
 */
public abstract class AbstractI18nInfo {

    final int errorCode;

    final Throwable throwable;

    AbstractI18nInfo(Throwable throwable, int errorCode) {
        this.throwable = throwable;
        this.errorCode = errorCode;
    }

    public final String getMessage(
            MessageSource messageSource) {
        String key = getKey();

        if (StringUtils.isBlank(key)) {
            return getDefaultMessage();
        }

        Locale locale = LocaleContextHolder.getLocale();

        return messageSource.getMessage(key, getArgs(), getDefaultMessage(), locale);
    }

    protected String getKey() {
        return null;
    }

    protected Object[] getArgs() {
        return null;
    }

    protected String getDefaultMessage() {
        return throwable != null ? throwable.getLocalizedMessage() : this.getClass().getName();
    }

    public final int getErrorCode() {
        return errorCode;
    }

    public final Throwable getThrowable() {
        return throwable;
    }

}