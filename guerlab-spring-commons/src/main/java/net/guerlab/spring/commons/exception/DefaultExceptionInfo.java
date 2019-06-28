package net.guerlab.spring.commons.exception;

import java.util.Locale;

/**
 * 默认错误
 *
 * @author guer
 *
 */
public class DefaultExceptionInfo extends AbstractI18nInfo {

    private static final String DEFAULT_MSG;

    static {
        Locale locale = Locale.getDefault();

        if (Locale.CHINA.equals(locale)) {
            DEFAULT_MSG = "系统繁忙,请稍后再试";
        } else {
            DEFAULT_MSG = "The system is busy. Please try again later.";
        }
    }

    /**
     * 通过异常信息初始化
     *
     * @param cause
     *            Throwable
     */
    public DefaultExceptionInfo(Throwable cause) {
        super(cause, 500);
    }

    @Override
    protected String getKey() {
        return Keys.DEFAULT;
    }

    @Override
    protected String getDefaultMessage() {
        return DEFAULT_MSG;
    }
}