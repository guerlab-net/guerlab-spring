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
package net.guerlab.spring.web.autoconfigure;

import net.guerlab.spring.web.exception.handler.GlobalExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常统一处理配置自动配置
 *
 * @author guer
 *
 */
@Configuration
public class GlobalExceptionHandlerAutoConfigure {

    /**
     * 默认异常统一处理配置
     *
     * @author guer
     *
     */
    @ControllerAdvice
    @ResponseBody
    public static class DefaultGlobalExceptionHandler extends GlobalExceptionHandler {

    }
}
