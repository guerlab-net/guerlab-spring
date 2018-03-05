package net.guerlab.spring.cloud.commons.exception;

import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * @author guer
 *
 */
public class NoHandlerFoundExceptionInfo extends AbstractI18nInfo {

    private String method;

    private String url;

    public NoHandlerFoundExceptionInfo(NoHandlerFoundException cause) {
        super(cause, 404);
        method = cause.getHttpMethod();
        url = cause.getRequestURL();
    }

    @Override
    protected String getKey() {
        return Keys.NO_HANDLER_FOUND;
    }

    @Override
    protected Object[] getArgs() {
        return new Object[] {
                method, url
        };
    }

    @Override
    protected String getDefaultMessage() {
        return "请求地址无效[" + method + " " + url + "]";
    }
}
