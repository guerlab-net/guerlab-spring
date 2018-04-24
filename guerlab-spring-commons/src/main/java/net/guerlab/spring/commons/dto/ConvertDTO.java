package net.guerlab.spring.commons.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 转换为DTO对象
 *
 * @author guer
 *
 * @param <D>
 *            DTO对象类型
 */
@FunctionalInterface
public interface ConvertDTO<D> {

    /**
     * 转换
     *
     * @return DTO对象
     */
    @JsonIgnore
    D toDTO();
}
