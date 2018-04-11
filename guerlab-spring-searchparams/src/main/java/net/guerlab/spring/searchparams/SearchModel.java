package net.guerlab.spring.searchparams;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
}
