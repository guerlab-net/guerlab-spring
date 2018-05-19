package net.guerlab.spring.commons.exception;

import java.util.Locale;

import org.springframework.web.HttpRequestMethodNotSupportedException;

/**
 * 不支持的HTTP请求方式
 *
 * @author guer
 *
 */
public class HttpRequestMethodNotSupportedExceptionInfo extends AbstractI18nInfo {

    private static final String DEFAULT_MSG;

    private String method;

    static {
        Locale locale = Locale.getDefault();

        if (Locale.CHINA.equals(locale)) {
            DEFAULT_MSG = "不支持%s请求方式";
        } else {
            DEFAULT_MSG = "No support %s request mode";
        }
    }

    /**
     * 通过HttpRequestMethodNotSupportedException初始化
     *
     * @param cause
     *            HttpRequestMethodNotSupportedException
     */
    public HttpRequestMethodNotSupportedExceptionInfo(HttpRequestMethodNotSupportedException cause) {
        super(cause, 405);
        method = cause.getMethod();
    }

    @Override
    protected String getKey() {
        return Keys.HTTP_REQUEST_METHOD_NOT_SUPPORTED;
    }

    @Override
    protected Object[] getArgs() {
        return new Object[] {
                method
        };
    }

    @Override
    protected String getDefaultMessage() {
        return String.format(DEFAULT_MSG, method);
    }
}