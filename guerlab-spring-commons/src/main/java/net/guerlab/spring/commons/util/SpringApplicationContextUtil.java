package net.guerlab.spring.commons.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * ApplicationContext工具类
 * 
 * @author guer
 *
 */
@Configuration
public class SpringApplicationContextUtil implements ApplicationContextAware {

    private static ApplicationContext context = null;

    @Override
    public void setApplicationContext(
            ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static ApplicationContext getContext() {
        return context;
    }

}
