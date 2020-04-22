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
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
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

    /**
     * 获取应用名称
     *
     * @return 应用名称
     */
    public static String getApplicationName() {
        if (context == null) {
            return null;
        }
        return context.getEnvironment().getProperty("spring.application.name");
    }

}
