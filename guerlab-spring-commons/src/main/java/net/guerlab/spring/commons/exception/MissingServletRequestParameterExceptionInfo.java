package net.guerlab.spring.commons.exception;

import org.springframework.web.bind.MissingServletRequestParameterException;

/**
 * 缺失请求参数
 *
 * @author guer
 *
 */
public class MissingServletRequestParameterExceptionInfo extends AbstractI18nInfo {

    private String parameterName;

    /**
     * 通过MissingServletRequestParameterException初始化
     * 
     * @param cause
     *            MissingServletRequestParameterException
     */
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
