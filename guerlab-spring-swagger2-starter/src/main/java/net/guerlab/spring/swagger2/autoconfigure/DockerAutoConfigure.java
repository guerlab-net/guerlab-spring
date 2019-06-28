package net.guerlab.spring.swagger2.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import net.guerlab.commons.collection.CollectionUtil;
import net.guerlab.spring.commons.util.SpringApplicationContextUtil;
import net.guerlab.spring.swagger2.DocketFactory;
import net.guerlab.spring.swagger2.properties.DocketProperties;
import net.guerlab.spring.swagger2.properties.Swagger2Properties;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Docker自动配置
 *
 * @author guer
 *
 */
@EnableSwagger2
@Configuration
@Conditional(SwaggerEnableCondition.class)
@EnableConfigurationProperties(Swagger2Properties.class)
public class DockerAutoConfigure {

    /**
     * 创建Docker
     *
     * @param swagger2Properties
     *            swagger2配置
     */
    @Autowired
    public void createDocker(Swagger2Properties swagger2Properties) {
        if (CollectionUtil.isEmpty(swagger2Properties.getDockets())) {
            return;
        }

        DefaultListableBeanFactory beanFactory = getBeanFactory();

        if (beanFactory == null) {
            return;
        }

        for (DocketProperties properties : swagger2Properties.getDockets()) {
            createDockerOnce(properties, beanFactory);
        }
    }

    private static DefaultListableBeanFactory getBeanFactory() {
        ApplicationContext context = SpringApplicationContextUtil.getContext();

        if (!(context instanceof ConfigurableApplicationContext)) {
            return null;
        }

        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) context;

        ConfigurableListableBeanFactory beanFactory = configurableApplicationContext.getBeanFactory();

        if (beanFactory instanceof DefaultListableBeanFactory) {
            return (DefaultListableBeanFactory) beanFactory;
        }

        return null;
    }

    private static void createDockerOnce(DocketProperties properties, DefaultListableBeanFactory beanFactory) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(DocketFactory.class, "build");
        builder.addConstructorArgValue(properties);

        BeanDefinition beanDefinition = builder.getBeanDefinition();

        String beanName = createBeanName(properties);

        beanFactory.registerBeanDefinition(beanName, beanDefinition);
    }

    private static String createBeanName(DocketProperties properties) {
        return "Docket_" + properties;
    }
}
