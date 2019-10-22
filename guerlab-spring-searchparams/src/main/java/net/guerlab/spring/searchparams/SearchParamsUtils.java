package net.guerlab.spring.searchparams;

import net.guerlab.commons.reflection.FieldUtil;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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

        ServiceLoader<SearchParamsUtilInstance> serviceLoader = ServiceLoader.load(SearchParamsUtilInstance.class);
        Stream<SearchParamsUtilInstance> stream = StreamSupport.stream(serviceLoader.spliterator(), false);
        Optional<SearchParamsUtilInstance> instanceOptional = stream.filter(instance -> instance.accept(object))
                .findFirst();

        if (!instanceOptional.isPresent()) {
            return;
        }

        SearchParamsUtilInstance instance = instanceOptional.get();

        Map<Boolean, List<Field>> fieldMap = getFields(searchParams).stream()
                .collect(Collectors.partitioningBy(field -> Objects.equals(OrderByType.class, field.getType())));

        fieldMap.getOrDefault(false, Collections.emptyList())
                .forEach(field -> setValue(field, object, searchParams, instance));
        fieldMap.getOrDefault(true, Collections.emptyList()).stream()
                .sorted(Comparator.comparingInt(SearchParamsUtils::getOrderByIndexValue))
                .forEach(field -> setValue(field, object, searchParams, instance));
        instance.afterHandler(searchParams, object);
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
     * 获取类字段列表
     *
     * @param searchParams
     *         搜索参数对象
     * @return 类字段列表
     */
    private static List<Field> getFields(final AbstractSearchParams searchParams) {
        return FieldUtil.getFiledsWithFilter(searchParams.getClass(), STATIC_FILTER, PAGE_PARAMS_FILTER);
    }

    /**
     * 获取类字段对应的搜索方式
     *
     * @param field
     *         类字段
     * @return 搜索方式
     */
    private static SearchModelType getSearchModelType(final Field field) {
        SearchModel searchModel = field.getAnnotation(SearchModel.class);
        return searchModel == null ? SearchModelType.EQUAL_TO : searchModel.value();
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

    private static void setValue(final Field field, final Object object, final AbstractSearchParams searchParams,
            final SearchParamsUtilInstance instance) {
        String name = field.getName();

        SearchModelType searchModelType = getSearchModelType(field);

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

        handler.setValue(object, name, getColumnName(field), value, searchModelType);
    }
}
