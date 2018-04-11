package net.guerlab.spring.commons.autoconfigure;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import net.guerlab.spring.commons.annotation.IgnoreResponseHandler;
import net.guerlab.spring.commons.properties.ResponseAdvisorProperties;
import net.guerlab.web.result.Result;
import net.guerlab.web.result.Succeed;

/**
 * 响应数据处理
 *
 * @author guer
 *
 */
@RestControllerAdvice
@Configuration
@EnableConfigurationProperties(ResponseAdvisorProperties.class)
public class ResponseAdvisorAutoconfigure implements ResponseBodyAdvice<Object> {

    @Autowired
    private ResponseAdvisorProperties properties;

    @Override
    public boolean supports(
            MethodParameter returnType,
            Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.getMethod().getAnnotation(IgnoreResponseHandler.class) == null
                && returnType.getContainingClass().getAnnotation(IgnoreResponseHandler.class) == null
                && returnType.getDeclaringClass().getAnnotation(IgnoreResponseHandler.class) == null;
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response) {
        String requestPath = request.getURI().getPath();

        List<String> excluded = properties.getExcluded();

        if (excluded != null && excluded.stream().anyMatch(requestPath::startsWith)) {
            return body;
        }

        if (body instanceof Result) {
            return body;
        }

        return body instanceof String ? new Succeed<>(Succeed.MSG, body) : new Succeed<>(body);
    }
}