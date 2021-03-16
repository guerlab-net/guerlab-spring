package net.guerlab.spring.commons.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.Map;

/**
 * ApplicationContext工具类
 *
 * @author guer
 */
@SuppressWarnings("unused")
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
     * 根据指定类型获取bean实例
     *
     * @param clazz
     *         bean实例类
     * @param <T>
     *         bean实例类型
     * @return bean实例
     */
    public static <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }

    /**
     * 根据指定类型获取bean实例列表
     *
     * @param clazz
     *         bean实例类
     * @param <T>
     *         bean实例类型
     * @return bean实例列表
     */
    public static <T> Collection<T> getBeans(Class<T> clazz) {
        return context.getBeansOfType(clazz).values();
    }

    /**
     * 根据指定类型获取bean实例和实例名称
     *
     * @param clazz
     *         bean实例类
     * @param <T>
     *         bean实例类型
     * @return bean实例和实例名称散列表
     */
    public static <T> Map<String, T> getBeanMap(Class<T> clazz) {
        return context.getBeansOfType(clazz);
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
