package net.guerlab.spring.commons.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.guerlab.commons.collection.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.List;

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

        private ObjectMapper objectMapper;

        private LocaleChangeInterceptor localeChangeInterceptor;

        @Autowired
        public void setObjectMapper(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        @Autowired
        public void setLocaleChangeInterceptor(LocaleChangeInterceptor localeChangeInterceptor) {
            this.localeChangeInterceptor = localeChangeInterceptor;
        }

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

            for (HttpMessageConverter<?> converter : converters) {
                if (converter instanceof MappingJackson2XmlHttpMessageConverter) {
                    continue;
                }
                if (converter instanceof AbstractJackson2HttpMessageConverter) {
                    ((AbstractJackson2HttpMessageConverter) converter).setObjectMapper(objectMapper);
                }
            }
        }
    }
}
