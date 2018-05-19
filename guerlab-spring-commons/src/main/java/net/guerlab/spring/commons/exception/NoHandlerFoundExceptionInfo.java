package net.guerlab.spring.commons.exception;

import java.util.Locale;

import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 未发现处理程序(404)
 *
 * @author guer
 *
 */
public class NoHandlerFoundExceptionInfo extends AbstractI18nInfo {

    private static final String DEFAULT_MSG;

    private String method;

    private String url;

    static {
        Locale locale = Locale.getDefault();

        if (Locale.CHINA.equals(locale)) {
            DEFAULT_MSG = "请求地址无效[%s %s]";
        } else {
            DEFAULT_MSG = "Invalid request address[%s %s]";
        }
    }

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
        return String.format(DEFAULT_MSG, method, url);
    }
}
