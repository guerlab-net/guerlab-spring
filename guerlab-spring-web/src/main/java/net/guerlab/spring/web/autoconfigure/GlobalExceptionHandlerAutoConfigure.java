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
