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
package net.guerlab.spring.commons.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 转换对象
 *
 * @param <D>
 *         对象类型
 * @author guer
 */
@FunctionalInterface
public interface Convert<D> {

    /**
     * 转换
     *
     * @return 转换对象
     */
    @JsonIgnore
    D convert();
}
