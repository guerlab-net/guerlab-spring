package net.guerlab.spring.commons.exception;

import java.util.Locale;

import org.springframework.web.bind.MissingServletRequestParameterException;

/**
 * 缺失请求参数
 *
 * @author guer
 *
 */
public class MissingServletRequestParameterExceptionInfo extends AbstractI18nInfo {

    private static final String DEFAULT_MSG;

    private String parameterName;

    static {
        Locale locale = Locale.getDefault();

        if (Locale.CHINA.equals(locale)) {
            DEFAULT_MSG = "请求参数[%s]缺失";
        } else {
            DEFAULT_MSG = "Request parameter [%s] missing.";
        }
    }

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
        return String.format(DEFAULT_MSG, parameterName);
    }
}
