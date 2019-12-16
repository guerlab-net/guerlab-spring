package net.guerlab.spring.searchparams;

import java.lang.annotation.*;

/**
 * 搜索模式
 *
 * @author guer
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface SearchModel {

    /**
     * 获取搜索模式类型
     *
     * @return 搜索模式类型
     */
    SearchModelType value() default SearchModelType.EQUAL_TO;

    /**
     * 自定义sql
     *
     * @return 自定义sql
     */
    String sql() default "";
}
