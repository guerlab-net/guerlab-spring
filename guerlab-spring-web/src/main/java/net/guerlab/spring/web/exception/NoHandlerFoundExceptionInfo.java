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
package net.guerlab.spring.web.exception;

import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Locale;

/**
 * 未发现处理程序(404)
 *
 * @author guer
 */
public class NoHandlerFoundExceptionInfo extends AbstractI18nInfo {

    private static final String DEFAULT_MSG;

    private final String method;

    private final String url;

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
        return new Object[] { method, url };
    }

    @Override
    protected String getDefaultMessage() {
        return String.format(DEFAULT_MSG, method, url);
    }
}
