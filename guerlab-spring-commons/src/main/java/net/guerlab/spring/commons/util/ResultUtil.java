package net.guerlab.spring.commons.util;

import java.util.Collection;
import java.util.Map;

import net.guerlab.spring.commons.list.ListObject;
import net.guerlab.web.result.Result;

/**
 * Result工具类
 *
 * @author guer
 *
 */
public class ResultUtil {

    private ResultUtil() {
    }

    /**
     * 判断是否为空map
     *
     * @param <T>
     *            数据类型
     * @param result
     *            result
     * @return 是否为空map
     */
    public static <T extends Map<?, ?>> boolean isEmptyMap(
            Result<T> result) {
        return isNull(result) || result.getData().isEmpty();
    }

    /**
     * 判断是否为非空map
     *
     * @param <T>
     *            数据类型
     * @param result
     *            result
     * @return 是否为非空map
     */
    public static <T extends Map<?, ?>> boolean isNotEmptyMap(
            Result<T> result) {
        return !isEmptyMap(result);
    }

    /**
     * 判断是否为空集合
     *
     * @param <T>
     *            数据类型
     * @param result
     *            result
     * @return 是否为空集合
     */
    public static <T extends Collection<?>> boolean isEmpty(
            Result<T> result) {
        return isNull(result) || result.getData().isEmpty();
    }

    /**
     * 判断是否为非空集合
     *
     * @param <T>
     *            数据类型
     * @param result
     *            result
     * @return 是否为非空集合
     */
    public static <T extends Collection<?>> boolean isNotEmpty(
            Result<T> result) {
        return !isEmpty(result);
    }

    /**
     * 判断是否为空集合
     *
     * @param <T>
     *            数据类型
     * @param result
     *            result
     * @return 是否为空集合
     */
    public static <T extends ListObject<?>> boolean isEmptyListObject(
            Result<T> result) {
        return isNull(result) || result.getData().getList() == null || result.getData().getList().isEmpty();
    }

    /**
     * 判断是否为非空集合
     *
     * @param <T>
     *            数据类型
     * @param result
     *            result
     * @return 是否为非空集合
     */
    public static <T extends ListObject<?>> boolean isNotEmptyListObject(
            Result<T> result) {
        return !isEmptyListObject(result);
    }

    /**
     * 判断结果是否为空
     *
     * @param result
     *            result
     * @return 是否为空
     */
    public static boolean isNull(
            Result<?> result) {
        return result == null || !result.isStatus() || result.getData() == null;
    }

    /**
     * 判断结果是否非空
     *
     * @param result
     *            result
     * @return 是否非空
     */
    public static boolean isNotNull(
            Result<?> result) {
        return !isNull(result);
    }
}
