package net.guerlab.spring.commons.autoconfigure;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.guerlab.commons.collection.CollectionUtil;

/**
 * web mvc配置
 *
 * @author guer
 *
 */
@Configuration
@AutoConfigureAfter(ObjectMapperAutoconfigure.class)
public class WebMvcAutoconfigure {

    @Configuration
    @ConditionalOnClass(WebMvcConfigurer.class)
    public static class MvcAutoconfigure implements WebMvcConfigurer {

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        private LocaleChangeInterceptor localeChangeInterceptor;

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(localeChangeInterceptor);
        }

        @Override
        public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
            if (CollectionUtil.isEmpty(converters)) {
                converters.add(new MappingJackson2HttpMessageConverter(objectMapper));
                converters.add(new StringHttpMessageConverter());
                return;
            }

            for (HttpMessageConverter<?> httpMessageConverter : converters) {
                if (httpMessageConverter instanceof AbstractJackson2HttpMessageConverter) {
                    AbstractJackson2HttpMessageConverter converter = (AbstractJackson2HttpMessageConverter) httpMessageConverter;

                    converter.setObjectMapper(objectMapper);
                }
            }
        }
    }
}
