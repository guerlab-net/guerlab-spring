package net.guerlab.spring.cloud.commons.exception;

import org.springframework.web.bind.MissingServletRequestParameterException;

/**
 * @author guer
 *
 */
public class MissingServletRequestParameterExceptionInfo extends AbstractI18nInfo {

    private String parameterName;

    public MissingServletRequestParameterExceptionInfo(MissingServletRequestParameterException cause) {
        super(cause, 403);
        parameterName = cause.getParameterName();
    }

    @Override
    protected String getKey() {
        return Keys.MISSING_SERVLET_REQUEST_PARAMETER;
    }

    @Override
    protected Object[] getArgs() {
        return new Object[] {
                parameterName
        };
    }

    @Override
    protected String getDefaultMessage() {
        return "请求参数[" + parameterName + "]缺失";
    }
}
