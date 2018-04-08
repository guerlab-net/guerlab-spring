package net.guerlab.spring.cloud.commons.autoconfigure;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * web mvc配置
 *
 * @author guer
 *
 */
@Configuration
@AutoConfigureAfter(ObjectMapperAutoconfigure.class)
public class WebMvcAutoconfigure extends WebMvcConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebMvcAutoconfigure.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LocaleChangeInterceptor localeChangeInterceptor;

    @Override
    public void addResourceHandlers(
            ResourceHandlerRegistry registry) {
        if (!hasSwagger()) {
            return;
        }

        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addCorsMappings(
            CorsRegistry registry) {
        CorsRegistration registration = registry.addMapping("/**");
        registration.allowedOrigins(CorsConfiguration.ALL);
        registration.allowedMethods(CorsConfiguration.ALL);
        registration.allowedHeaders(CorsConfiguration.ALL);
        registration.allowCredentials(true);
    }

    @Override
    public void configureMessageConverters(
            List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        converters.clear();
        converters.add(new MappingJackson2HttpMessageConverter(objectMapper));
        converters.add(new StringHttpMessageConverter());
    }

    @Override
    public void addInterceptors(
            InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor);
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    private boolean hasSwagger() {
        try {
            getClass().getClassLoader().loadClass("springfox.documentation.swagger2.annotations.EnableSwagger2");
            return true;
        } catch (Exception e) {
            LOGGER.trace(e.getMessage(), e);
            return false;
        }
    }
}
