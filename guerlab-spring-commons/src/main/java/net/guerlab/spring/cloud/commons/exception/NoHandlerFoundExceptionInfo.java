package net.guerlab.spring.cloud.commons.exception;

import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 未发现处理程序(404)
 * 
 * @author guer
 *
 */
public class NoHandlerFoundExceptionInfo extends AbstractI18nInfo {

    private String method;

    private String url;

    /**
     * 通过NoHandlerFoundException初始化
     *
     * @param cause
     *            NoHandlerFoundException
     */
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
