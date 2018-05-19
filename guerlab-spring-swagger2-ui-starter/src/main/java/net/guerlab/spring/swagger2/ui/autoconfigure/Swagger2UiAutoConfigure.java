package net.guerlab.spring.swagger2.ui.autoconfigure;

import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import net.guerlab.spring.swagger2.autoconfigure.SwaggerEnableCondition;

/**
 * 设置swagger-ui的静态资源的映射规则
 *
 * @author guer
 *
 */
@Configuration
@Conditional(SwaggerEnableCondition.class)
public class Swagger2UiAutoConfigure implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
