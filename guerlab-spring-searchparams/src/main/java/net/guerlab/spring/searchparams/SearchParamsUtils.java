package net.guerlab.spring.searchparams;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.persistence.Column;

import org.apache.commons.lang3.StringUtils;

import net.guerlab.commons.collection.CollectionUtil;
import net.guerlab.commons.reflection.FieldUtil;
import tk.mybatis.mapper.entity.Example;

/**
 * SearchParams工具类
 *
 * @author guer
 *
 */
public class SearchParamsUtils {

    private static final Predicate<Field> STATIC_FILTER = e -> e != null && !Modifier.isStatic(e.getModifiers());

    private static final Predicate<Field> PAGE_PARAMS_FILTER = e -> !AbstractSearchParams.class.getName()
            .equals(e.getDeclaringClass().getName());

    private SearchParamsUtils() {
    }

    /**
     * 将searchParams的参数转变为example的查询条件
     *
     * @param searchParams
     *            参数列表对象
     * @param example
     *            example
     */
    public static void exampleAdvice(final AbstractSearchParams searchParams, final Example example) {
        exampleAdvice(searchParams, example, SearchParamsParseConfig.getGlobalInstance());
    }

    /**
     * 将searchParams的参数转变为example的查询条件
     *
     * @param searchParams
     *            参数列表对象
     * @param example
     *            example
     * @param config
     *            SearchParams类解析配置
     */
    public static void exampleAdvice(final AbstractSearchParams searchParams, final Example example,
            final SearchParamsParseConfig config) {
        if (searchParams == null || example == null) {
            return;
        }

        Map<Boolean, List<Field>> fieldMap = getFields(searchParams).stream()
                .collect(Collectors.partitioningBy(field -> OrderByType.class == field.getType()));

        CollectionUtil.forEach(fieldMap.get(false), field -> setCriteriaValue(field, example, searchParams, config));

        List<Field> orderByFields = fieldMap.get(true);

        if (orderByFields == null) {
            return;
        }

        orderByFields.stream().sorted(Comparator.comparingInt(SearchParamsUtils::getOrderByIndexValue))
                .forEach(field -> setCriteriaValue(field, example, searchParams, config));
    }

    /**
     * 获取OrderByIndex注解的参数值
     *
     * @param field
     *            字段
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
     * 将参数列表对象转换为map对象,包含pageId/pageSize字段
     *
     * @param searchParams
     *            参数列表对象
     * @return 参数表
     */
    public static Map<String, Object> toAllMap(final AbstractSearchParams searchParams) {
        return toAllMap(searchParams, SearchParamsParseConfig.getGlobalInstance());
    }

    /**
     * 将参数列表对象转换为map对象,包含pageId/pageSize字段
     *
     * @param searchParams
     *            参数列表对象
     * @param config
     *            SearchParams类解析配置
     * @return 参数表
     */
    public static Map<String, Object> toAllMap(final AbstractSearchParams searchParams,
            final SearchParamsParseConfig config) {
        Map<String, Object> map = toMap(searchParams, config);

        map.put("pageId", searchParams.getPageId());
        map.put("pageSize", searchParams.getPageSize());

        return map;
    }

    /**
     * 将参数列表对象转换为map对象
     *
     * @param searchParams
     *            参数列表对象
     * @return 参数表
     */
    public static Map<String, Object> toMap(final AbstractSearchParams searchParams) {
        return toMap(searchParams, SearchParamsParseConfig.getGlobalInstance());
    }

    /**
     * 将参数列表对象转换为map对象
     *
     * @param searchParams
     *            参数列表对象
     * @param config
     *            SearchParams类解析配置
     * @return 参数表
     */
    public static Map<String, Object> toMap(final AbstractSearchParams searchParams,
            final SearchParamsParseConfig config) {
        if (searchParams == null) {
            return new HashMap<>();
        }

        Map<String, Object> map = new HashMap<>();

        getFields(searchParams).forEach(field -> setMapValue(field, map, searchParams, config));

        return map;
    }

    private static List<Field> getFields(final AbstractSearchParams searchParams) {
        return FieldUtil.getFiledsWithFilter(searchParams.getClass(), STATIC_FILTER, PAGE_PARAMS_FILTER);
    }

    private static SearchParamsHandler getHandler(final Field field, final SearchParamsParseConfig config) {
        SearchParamsParseConfig useConfig = config == null ? SearchParamsParseConfig.getGlobalInstance() : config;

        return useConfig.getHandler(field.getType());
    }

    private static SearchModelType getSearchModelType(final Field field) {
        SearchModel searchModel = field.getAnnotation(SearchModel.class);
        return searchModel == null || searchModel.value() == null ? SearchModelType.EQUAL_TO : searchModel.value();
    }

    private static String getColumnName(final Field field, final String name) {
        Column column = field.getAnnotation(Column.class);
        return column != null && StringUtils.isNotBlank(column.name()) ? column.name() : name;
    }

    private static void setCriteriaValue(final Field field, final Example example,
            final AbstractSearchParams searchParams, final SearchParamsParseConfig config) {
        String name = field.getName();

        SearchModelType searchModelType = getSearchModelType(field);

        if (searchModelType == SearchModelType.IGNORE) {
            return;
        }

        SearchParamsHandler handler = getHandler(field, config);

        if (handler == null) {
            return;
        }

        Object value = FieldUtil.get(searchParams, name);

        if (value == null) {
            return;
        }

        handler.setValue(example, getColumnName(field, name), value, searchModelType);
    }

    private static void setMapValue(final Field field, final Map<String, Object> map,
            final AbstractSearchParams searchParams, final SearchParamsParseConfig config) {
        String name = field.getName();

        SearchModelType searchModelType = getSearchModelType(field);

        if (searchModelType == SearchModelType.IGNORE) {
            return;
        }

        SearchParamsHandler handler = getHandler(field, config);

        if (handler == null) {
            return;
        }

        Object value = FieldUtil.get(searchParams, name);

        if (value == null) {
            return;
        }

        handler.setValue(map, name, value, searchModelType);
    }
}
