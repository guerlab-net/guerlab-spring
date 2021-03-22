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
