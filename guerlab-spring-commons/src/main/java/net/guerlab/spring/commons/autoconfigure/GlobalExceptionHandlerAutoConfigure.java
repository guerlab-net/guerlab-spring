package net.guerlab.spring.commons.autoconfigure;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import net.guerlab.spring.commons.exception.handler.GlobalExceptionHandler;

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
