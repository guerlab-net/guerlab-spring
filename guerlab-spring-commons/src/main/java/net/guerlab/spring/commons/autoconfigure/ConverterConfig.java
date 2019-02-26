package net.guerlab.spring.commons.autoconfigure;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import net.guerlab.spring.commons.converter.DateConverter;
import net.guerlab.spring.commons.converter.LocalDateConverter;
import net.guerlab.spring.commons.converter.LocalDateTimeConverter;

/**
 * 转换配置
 *
 * @author guer
 *
 */
@Configuration
@ConditionalOnClass(RequestMappingHandlerAdapter.class)
public class ConverterConfig {

    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;

    /**
     * 增加字符串转日期的功能
     */
    @PostConstruct
    public void addDateTimeConverter() {
        WebBindingInitializer webBindingInitializer = handlerAdapter.getWebBindingInitializer();

        if (!(webBindingInitializer instanceof ConfigurableWebBindingInitializer)) {
            return;
        }

        ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer) webBindingInitializer;

        ConversionService conversionService = initializer.getConversionService();

        if (initializer.getConversionService() == null || !(conversionService instanceof GenericConversionService)) {
            return;
        }

        GenericConversionService service = (GenericConversionService) conversionService;

        service.addConverter(new DateConverter());
        service.addConverter(new LocalDateTimeConverter());
        service.addConverter(new LocalDateConverter());
    }
}
