package net.guerlab.spring.searchparams;

/**
 * 搜索模式类型
 *
 * @author guer
 *
 */
public enum SearchModelType {

    /**
     * 空
     */
    IS_NULL,

    /**
     * 非空
     */
    IS_NOT_NULL,

    /**
     * 相等
     */
    EQUAL_TO,

    /**
     * 不想等
     */
    NOT_EQUAL_TO,

    /**
     * 大于
     */
    GREATER_THAN,

    /**
     * 大于等于
     */
    GREATER_THAN_OR_EQUAL_TO,

    /**
     * 小于
     */
    LESS_THAN,

    /**
     * 小于等于
     */
    LESS_THAN_OR_EQUAL_TO,

    /**
     * 包含
     */
    LIKE,

    /**
     * 不包含
     */
    NOT_LIKE,

    /**
     * 在列表中,只能配合Collection接口使用
     */
    IN,

    /**
     * 不在列表中,只能配合Collection接口使用
     */
    NOT_IN,

    /**
     * 忽略
     */
    IGNORE;
}
