package net.guerlab.spring.swagger2.autoconfigure;

import net.guerlab.spring.swagger2.DocketFactory;
import net.guerlab.spring.swagger2.properties.DocketProperties;
import net.guerlab.spring.swagger2.properties.Swagger2Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

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

    private static void createDockerOnce(DocketProperties properties, BeanDefinitionRegistry beanFactory) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(DocketFactory.class, "build");
        builder.addConstructorArgValue(properties);
        beanFactory.registerBeanDefinition(createBeanName(properties), builder.getBeanDefinition());
    }

    @Autowired
    public void createDockers(Swagger2Properties swagger2Properties, BeanDefinitionRegistryHolder holder) {
        List<DocketProperties> dockets = swagger2Properties.getDockets();
        if (dockets == null || dockets.isEmpty()) {
            return;
        }

        dockets.forEach(docket -> createDockerOnce(docket, holder.getRegistry()));
    }

    private static String createBeanName(DocketProperties properties) {
        return "Docket_" + properties;
    }
}
