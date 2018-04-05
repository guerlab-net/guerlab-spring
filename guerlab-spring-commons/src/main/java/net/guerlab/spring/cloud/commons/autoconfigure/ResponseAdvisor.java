package net.guerlab.spring.cloud.commons.autoconfigure;

import java.util.Arrays;
import java.util.List;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import net.guerlab.spring.cloud.commons.annotation.IgnoreResponseHandler;
import net.guerlab.web.result.Result;
import net.guerlab.web.result.Succeed;

@RestControllerAdvice
public class ResponseAdvisor implements ResponseBodyAdvice<Object> {

    private static final List<String> EXCLUDED = Arrays.asList("/swagger", "/v2", "/health", "/info", "/bus",
            "/service-registry");

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

        if (EXCLUDED.stream().anyMatch(requestPath::startsWith)) {
            return body;
        }

        if (body instanceof Result) {
            return body;
        }

        return body instanceof String ? new Succeed<>(Succeed.MSG, body) : new Succeed<>(body);
    }
}