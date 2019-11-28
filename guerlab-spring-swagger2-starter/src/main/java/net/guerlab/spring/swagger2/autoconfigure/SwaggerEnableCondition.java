package net.guerlab.spring.swagger2.autoconfigure;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * swagger启用判断
 *
 * @author guer
 *
 */
public class SwaggerEnableCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return context.getEnvironment().getProperty("swagger.enable", Boolean.TYPE, false);
    }

}
