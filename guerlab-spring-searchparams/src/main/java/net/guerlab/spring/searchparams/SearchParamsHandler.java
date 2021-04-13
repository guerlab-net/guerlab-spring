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
     * @param object
     *            输出对象
     * @param fieldName
     *            类字段名
     * @param columnName
     *            数据库字段名
     * @param value
     *            参数值
     * @param searchModelType
     *            搜索模式类型
     * @param customSql
     *            自定义sql
     */
    void setValue(final Object object, final String fieldName, final String columnName, final Object value, final SearchModelType searchModelType, String customSql);
}
