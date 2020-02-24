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

    /**
     * 启用参数长度检查
     */
    private static boolean enableParamLengthCheck;

    /**
     * 参数最大长度
     */
    private static int paramMaxLength;

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
                .sorted(comparingInt(SearchParamsUtils::getOrderByIndexValue))
                .forEach(field -> setValue(field, object, searchParams, instance));
        instance.afterHandler(searchParams, object);
    }

    private static <T> Comparator<T> comparingInt(ToIntFunction<? super T> keyExtractor) {
        Objects.requireNonNull(keyExtractor);
        return (Comparator<T> & Serializable) (c1, c2) -> Integer
                .compare(keyExtractor.applyAsInt(c2), keyExtractor.applyAsInt(c1));
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
        return FieldUtil.getFiledsWithFilter(searchParams.getClass(), STATIC_FILTER, PAGE_PARAMS_FILTER);
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

    private static void setValue(final Field field, final Object object, final AbstractSearchParams searchParams,
            final SearchParamsUtilInstance instance) {
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

        handler.setValue(object, name, getColumnName(field), value, searchModelType,
                StringUtils.trimToNull(getCustomSql(searchModel)));
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
