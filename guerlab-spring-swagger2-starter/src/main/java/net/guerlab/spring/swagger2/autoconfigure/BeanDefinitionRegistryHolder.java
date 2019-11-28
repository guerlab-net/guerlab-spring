package net.guerlab.spring.swagger2.autoconfigure;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

/**
 * Bean定义注册器持有器
 *
 * @author guer
 */
public class BeanDefinitionRegistryHolder implements BeanDefinitionRegistryPostProcessor {

    private BeanDefinitionRegistry registry;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        this.registry = registry;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }

    /**
     * 获取Bean定义注册器持有器
     *
     * @return 注册器
     */
    BeanDefinitionRegistry getRegistry() {
        return registry;
    }
}
