/*
 * Copyright 2018-2021 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
    private final String paramName;

    /**
     * 参数值最大长度
     */
    private final Integer maxLength;

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
