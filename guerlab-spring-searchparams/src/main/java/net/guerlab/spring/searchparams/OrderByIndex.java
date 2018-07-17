package net.guerlab.spring.searchparams;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Order By执行先后顺序
 *
 * @author guer
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface OrderByIndex {

    /**
     * 获取Order By执行先后顺序
     *
     * @return Order By执行先后顺序
     */
    int value() default 0;
}
