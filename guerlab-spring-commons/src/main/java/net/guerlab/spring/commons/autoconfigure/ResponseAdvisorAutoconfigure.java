package net.guerlab.spring.commons.autoconfigure;

import java.lang.annotation.Annotation;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
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
 * 响应数据处理自动配置
 *
 * @author guer
 *
 */
@Configuration
@ConditionalOnClass(ResponseBodyAdvice.class)
@EnableConfigurationProperties(ResponseAdvisorProperties.class)
public class ResponseAdvisorAutoconfigure {

    /**
     * 响应数据处理
     *
     * @author guer
     *
     */
    @RestControllerAdvice
    public static class ResponseAdvice implements ResponseBodyAdvice<Object> {

        @Autowired
        private ResponseAdvisorProperties properties;

        @Override
        public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
            return !hasAnnotation(returnType, IgnoreResponseHandler.class);
        }

        @Override
        public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                ServerHttpResponse response) {
            if (matchExcluded(request) || body instanceof Result) {
                return body;
            }

            return body instanceof String ? new Succeed<>(Succeed.MSG, body) : new Succeed<>(body);
        }

        /**
         * 判断方法或者类上是否包含指定注解
         *
         * @param returnType
         *            方法参数对象
         * @param annotationClass
         *            注解类
         * @return 是否包含注解
         */
        private boolean hasAnnotation(MethodParameter returnType, Class<? extends Annotation> annotationClass) {
            return AnnotationUtils.findAnnotation(returnType.getMethod(), annotationClass) != null
                    || AnnotationUtils.findAnnotation(returnType.getContainingClass(), annotationClass) != null;
        }

        /**
         * 判断是否在排除路径中
         * 
         * @param request
         *            请求对象
         * @return 是否在排除路径中
         */
        private boolean matchExcluded(ServerHttpRequest request) {
            List<String> excluded = properties.getExcluded();

            if (excluded == null || excluded.isEmpty()) {
                return false;
            }

            String requestPath = request.getURI().getPath();

            return excluded.stream().anyMatch(requestPath::startsWith);
        }
    }
}
