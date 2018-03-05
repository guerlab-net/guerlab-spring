package net.guerlab.spring.cloud.commons.autoconfigure;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfigTool implements ApplicationContextAware {

    private static ApplicationContext context = null;

    @Override
    public void setApplicationContext(
            ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static ApplicationContext getContext() {
        return context;
    }

}
