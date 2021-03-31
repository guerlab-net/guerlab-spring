package net.guerlab.spring.web.annotation;

import java.lang.annotation.*;

/**
 * 忽略ResponseAdvisor的返回结果处理
 *
 * @author guer
 *
 */
@Target({
        ElementType.METHOD, ElementType.TYPE
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface IgnoreResponseHandler {

}
