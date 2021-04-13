/*
 * Copyright 2018-2021 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
