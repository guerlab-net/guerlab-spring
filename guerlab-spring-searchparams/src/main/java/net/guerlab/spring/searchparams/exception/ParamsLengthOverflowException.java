package net.guerlab.spring.searchparams.exception;

import net.guerlab.spring.commons.exception.AbstractI18nApplicationException;

/**
 * 参数值长度错误异常
 *
 * @author guer
 */
public class ParamsLengthOverflowException extends AbstractI18nApplicationException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE_KEY = "message.exception.paramsLengthOverflow";

    /**
     * 参数名
     */
    private String paramName;

    /**
     * 参数值最大长度
     */
    private Integer maxLength;

    /**
     * 初始化参数值长度错误异常
     *
     * @param paramName
     *         参数名
     * @param maxLength
     *         参数值最大长度
     */
    public ParamsLengthOverflowException(String paramName, Integer maxLength) {
        this.paramName = paramName;
        this.maxLength = maxLength;
    }

    /**
     * 获取参数名
     *
     * @return 参数名
     */
    public String getParamName() {
        return paramName;
    }

    /**
     * 获取参数值最大长度
     *
     * @return 参数值最大长度
     */
    public Integer getMaxLength() {
        return maxLength;
    }

    @Override
    protected String getKey() {
        return MESSAGE_KEY;
    }

    @Override
    protected Object[] getArgs() {
        return new Object[] { paramName, maxLength };
    }
}
