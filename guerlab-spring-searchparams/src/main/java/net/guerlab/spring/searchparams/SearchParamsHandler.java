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
    void setValue(final Object object, final String fieldName, final String columnName, final Object value,
            final SearchModelType searchModelType, String customSql);
}
