package net.guerlab.spring.cloud.commons.autoconfigure;

import java.util.Arrays;
import java.util.List;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import net.guerlab.web.result.Result;
import net.guerlab.web.result.Succeed;

@ControllerAdvice
public class ResponseAdvisor implements ResponseBodyAdvice<Object> {

    private static final List<String> EXCLUDED = Arrays.asList("/swagger", "/v2", "/health", "/info", "/bus",
            "/service-registry");

    @Override
    public boolean supports(
            MethodParameter returnType,
            Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
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

    public static void main(
            String[] args) {
        MediaType a = MediaType.APPLICATION_JSON;
        MediaType b = MediaType.APPLICATION_JSON_UTF8;

        System.out.println(a.includes(b));
        System.out.println(a.isCompatibleWith(b));
    }
}