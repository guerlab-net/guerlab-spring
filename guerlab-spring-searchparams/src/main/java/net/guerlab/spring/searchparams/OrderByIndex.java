package net.guerlab.spring.searchparams;

import java.lang.annotation.*;

/**
 * Order By执行先后顺序
 * 数值越大，优先级越大
 *
 * @author guer
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
