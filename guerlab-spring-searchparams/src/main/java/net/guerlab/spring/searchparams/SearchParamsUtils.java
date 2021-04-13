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

import net.guerlab.commons.reflection.FieldUtil;
import net.guerlab.spring.searchparams.exception.ParamsLengthOverflowException;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * SearchParams工具类
 *
 * @author guer
 *
 */
public class SearchParamsUtils {

    private static final Predicate<Field> STATIC_FILTER = e -> e != null && !Modifier.isStatic(e.getModifiers());

    private static final Predicate<Field> PAGE_PARAMS_FILTER = e -> !AbstractSearchParams.class.getName().equals(e.getDeclaringClass().getName());

    private static final HashMap<Class<? extends SearchParamsUtilInstance>, SearchParamsUtilInstance> INSTANCES_CACHE = new HashMap<>();

    /**
     * 启用参数长度检查
     */
    private static boolean enableParamLengthCheck;

    /**
     * 参数最大长度
     */
    private static int paramMaxLength;

    static {
        StreamSupport.stream(ServiceLoader.load(SearchParamsUtilInstance.class).spliterator(), false).forEach((instance) -> INSTANCES_CACHE.put(instance.getClass(), instance));
    }

    private SearchParamsUtils() {
    }

    /**
     * 注册实例
     *
     * @param instance
     *         实例
     */
    public static void register(SearchParamsUtilInstance instance) {
        if (instance == null) {
            return;
        }

        INSTANCES_CACHE.put(instance.getClass(), instance);
    }

    /**
     * 注销实例
     *
     * @param instance
     *         实例
     */
    public static void unRegister(SearchParamsUtilInstance instance) {
        if (instance != null) {
            INSTANCES_CACHE.remove(instance.getClass());
        }
    }

    /**
     * 注销指定类实例
     *
     * @param type
     *         类
     */
    public static void unRegister(Class<? extends SearchParamsUtilInstance> type) {
        if (type != null) {
            INSTANCES_CACHE.remove(type);
        }
    }

    /**
     * 获取指定类实例
     *
     * @param type
     *         类
     * @return 实例
     */
    public static SearchParamsUtilInstance getInstance(Class<? extends SearchParamsUtilInstance> type) {
        return type == null ? null : INSTANCES_CACHE.get(type);
    }

    /**
     * 获取实例列表
     *
     * @return 实例列表
     */
    public static Collection<SearchParamsUtilInstance> getInstances() {
        return Collections.unmodifiableCollection(INSTANCES_CACHE.values());
    }

    /**
     * 对searchParams进行处理
     *
     * @param searchParams
     *         参数列表对象
     * @param object
     *         输出对象
     */
    public static void handler(final AbstractSearchParams searchParams, final Object object) {
        if (searchParams == null || object == null) {
            return;
        }
        INSTANCES_CACHE.values().stream().filter(instance -> instance.accept(object)).findFirst().ifPresent(searchParamsUtilInstance -> handler(searchParams, object, searchParamsUtilInstance));
    }

    /**
     * 以指定实例对searchParams进行处理
     *
     * @param searchParams
     *         参数列表对象
     * @param object
     *         输出对象
     * @param instance
     *         处理实例
     */
    public static void handler(final AbstractSearchParams searchParams, final Object object, final SearchParamsUtilInstance instance) {
        if (searchParams == null || object == null || instance == null) {
            return;
        }

        Map<Boolean, List<Field>> fieldMap = getFields(searchParams).stream().collect(Collectors.partitioningBy(field -> Objects.equals(OrderByType.class, field.getType())));

        fieldMap.getOrDefault(false, Collections.emptyList()).forEach(field -> setValue(field, object, searchParams, instance));
        fieldMap.getOrDefault(true, Collections.emptyList()).stream().sorted(comparingInt(SearchParamsUtils::getOrderByIndexValue))
                .forEach(field -> setValue(field, object, searchParams, instance));
        instance.afterHandler(searchParams, object);
    }

    private static <T> Comparator<T> comparingInt(ToIntFunction<? super T> keyExtractor) {
        Objects.requireNonNull(keyExtractor);
        return (Comparator<T> & Serializable) (c1, c2) -> Integer.compare(keyExtractor.applyAsInt(c2), keyExtractor.applyAsInt(c1));
    }

    /**
     * 获取OrderByIndex注解的参数值
     *
     * @param field
     *         字段
     * @return OrderByIndex注解的参数值
     */
    private static int getOrderByIndexValue(Field field) {
        if (field == null) {
            return 0;
        }

        OrderByIndex orderByIndex = field.getAnnotation(OrderByIndex.class);
        return orderByIndex == null ? 0 : orderByIndex.value();
    }

    /**
     * 获取类字段列表
     *
     * @param searchParams
     *         搜索参数对象
     * @return 类字段列表
     */
    private static List<Field> getFields(final AbstractSearchParams searchParams) {
        return FieldUtil.getFieldsWithFilter(searchParams.getClass(), STATIC_FILTER, PAGE_PARAMS_FILTER);
    }

    /**
     * 获取类字段对应的搜索模式
     *
     * @param field
     *         类字段
     * @return 搜索模式
     */
    private static SearchModel getSearchModel(final Field field) {
        return field.getAnnotation(SearchModel.class);
    }

    /**
     * 获取类字段对应的搜索方式
     *
     * @param searchModel
     *         搜索模式
     * @return 搜索方式
     */
    private static SearchModelType getSearchModelType(final SearchModel searchModel) {
        return searchModel == null ? SearchModelType.EQUAL_TO : searchModel.value();
    }

    /**
     * 获取类字段对应的自定义sql
     *
     * @param searchModel
     *         搜索模式
     * @return 自定义sql
     */
    private static String getCustomSql(final SearchModel searchModel) {
        return searchModel == null ? null : searchModel.sql();
    }

    /**
     * 获取类字段对应的数据库字段名称
     *
     * @param field
     *         类字段
     * @return 数据库字段名称
     */
    private static String getColumnName(final Field field) {
        Column column = field.getAnnotation(Column.class);
        return column != null && StringUtils.isNotBlank(column.name()) ? column.name() : field.getName();
    }

    private static void setValue(final Field field, final Object object, final AbstractSearchParams searchParams, final SearchParamsUtilInstance instance) {
        String name = field.getName();

        SearchModel searchModel = getSearchModel(field);
        SearchModelType searchModelType = getSearchModelType(searchModel);

        if (searchModelType == SearchModelType.IGNORE) {
            return;
        }

        SearchParamsHandler handler = instance.getHandler(field.getType());

        if (handler == null) {
            return;
        }

        Object value = FieldUtil.get(searchParams, name);

        if (value == null) {
            return;
        }

        if (enableParamLengthCheck && value instanceof CharSequence) {
            if (paramMaxLength > 0 && ((CharSequence) value).length() > paramMaxLength) {
                throw new ParamsLengthOverflowException(name, paramMaxLength);
            }
        }

        handler.setValue(object, name, getColumnName(field), value, searchModelType, StringUtils.trimToNull(getCustomSql(searchModel)));
    }

    /**
     * 获取启用参数长度检查
     *
     * @return 启用参数长度检查
     */
    @SuppressWarnings("unused")
    public static boolean isEnableParamLengthCheck() {
        return enableParamLengthCheck;
    }

    /**
     * 设置启用参数长度检查
     *
     * @param enableParamLengthCheck
     *         启用参数长度检查
     */
    @SuppressWarnings("unused")
    public static void setEnableParamLengthCheck(boolean enableParamLengthCheck) {
        SearchParamsUtils.enableParamLengthCheck = enableParamLengthCheck;
    }

    /**
     * 获取参数最大长度
     *
     * @return 参数最大长度
     */
    @SuppressWarnings("unused")
    public static int getParamMaxLength() {
        return paramMaxLength;
    }

    /**
     * 设置参数最大长度
     *
     * @param paramMaxLength
     *         参数最大长度
     */
    @SuppressWarnings("unused")
    public static void setParamMaxLength(int paramMaxLength) {
        SearchParamsUtils.paramMaxLength = paramMaxLength;
    }
}
