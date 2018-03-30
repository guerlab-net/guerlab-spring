package net.guerlab.spring.cloud.commons.autoconfigure;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import net.guerlab.spring.cloud.commons.converter.LocalDateConverter;
import net.guerlab.spring.cloud.commons.converter.LocalDateTimeConverter;

/**
 * 转换配置
 *
 * @author guer
 *
 */
@Configuration
public class ConverterConfig {

    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;

    /**
     * 增加字符串转日期的功能
     */
    @PostConstruct
    public void initEditableValidation() {
        ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer) handlerAdapter
                .getWebBindingInitializer();
        if (initializer.getConversionService() == null) {
            return;
        }

        GenericConversionService service = (GenericConversionService) initializer.getConversionService();

        service.addConverter(new LocalDateTimeConverter());
        service.addConverter(new LocalDateConverter());
    }
}
