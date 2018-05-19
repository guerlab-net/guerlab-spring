package net.guerlab.spring.searchparams;

import java.util.Map;

import tk.mybatis.mapper.entity.Example;

/**
 * SearchParams参数处理接口
 *
 * @author guer
 *
 */
@FunctionalInterface
public interface SearchParamsHandler {

    /**
     * 设置参数值
     *
     * @param example
     *            example
     * @param columnName
     *            字段名
     * @param value
     *            参数值
     * @param searchModelType
     *            搜索模式类型
     */
    void setValue(final Example example, final String columnName, final Object value,
            final SearchModelType searchModelType);

    /**
     * 设置参数值
     *
     * @param map
     *            map
     * @param name
     *            参数名
     * @param value
     *            参数值
     * @param searchModelType
     *            搜索模式类型
     */
    default void setValue(final Map<String, Object> map, final String name, final Object value,
            final SearchModelType searchModelType) {
        map.put(name, value);
    }
}
