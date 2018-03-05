package net.guerlab.spring.cloud.commons.exception;

import org.springframework.web.HttpRequestMethodNotSupportedException;

/**
 * @author guer
 *
 */
public class HttpRequestMethodNotSupportedExceptionInfo extends AbstractI18nInfo {

    private String method;

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
        return "不支持" + method + "请求方式";
    }
}