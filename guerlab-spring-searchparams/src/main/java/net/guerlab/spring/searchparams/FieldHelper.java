package net.guerlab.spring.searchparams;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.guerlab.commons.exception.ApplicationException;

/**
 * 类字段助手
 *
 * @author guer
 *
 */
public class FieldHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(FieldHelper.class);

    private static final Class<?>[] BASE_NUMBER_CLASS_LIST = new Class<?>[] {
            Byte.TYPE, Short.TYPE, Long.TYPE, Long.TYPE, Float.TYPE, Double.TYPE
    };

    private static final Map<Class<?>, List<Field>> CACHE = new ConcurrentHashMap<>();

    private static final BaseFilter BASE_FILTER = new BaseFilter();

    private FieldHelper() {
        throw new SecurityException();
    }

    /**
     * 复制对象属性值
     *
     * @param in
     *            源对象
     * @param out
     *            目标对象
     */
    public static void valueCopy(
            final Object in,
            final Object out) {
        if (in == null || out == null) {
            return;
        }

        List<Field> inFields = getFiledsWithFilter(in.getClass(), BASE_FILTER);
        List<Field> outFields = getFiledsWithFilter(out.getClass(), BASE_FILTER);

        if (inFields.isEmpty() || outFields.isEmpty()) {
            return;
        }

        Map<String, Field> outFieldMap = outFields.stream().collect(Collectors.toMap(Field::getName, field -> field));

        inFields.stream().forEach(e -> valueCopyOnce(e, outFieldMap, in, out));
    }

    private static void valueCopyOnce(
            Field inField,
            Map<String, Field> outFieldMap,
            Object in,
            Object out) {
        if (inField == null) {
            return;
        }

        String name = inField.getName();
        Class<?> inType = inField.getType();
        Field outField = outFieldMap.get(name);
        Class<?> outType = outField == null ? null : outField.getType();

        if (outField == null || !inType.equals(outType)) {
            return;
        }

        try {
            Object object = read(in, name);
            write(out, name, object);
        } catch (Exception e) {
            LOGGER.trace(e.getMessage(), e);
        }
    }

    /**
     * 读取属性值
     *
     * @param object
     *            对象
     * @param name
     *            属性名
     * @return 属性值
     */
    public static Object get(
            Object object,
            String name) {
        try {
            return read(object, name);
        } catch (Exception e) {
            LOGGER.trace(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 读取属性值
     *
     * @param object
     *            对象
     * @param name
     *            属性名
     * @return 属性值
     */
    public static Object read(
            Object object,
            String name) {
        try {
            PropertyDescriptor propertyDescriptor = new PropertyDescriptor(name, object.getClass());
            Method method = propertyDescriptor.getReadMethod();
            return method.invoke(object, new Object[0]);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 设置属性
     *
     * @param object
     *            对象
     * @param name
     *            属性名
     * @param value
     *            属性值
     * @return 返回值
     */
    public static Object put(
            Object object,
            String name,
            Object value) {
        try {
            return write(object, name, value);
        } catch (Exception e) {
            LOGGER.trace(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 设置属性
     *
     * @param object
     *            对象
     * @param name
     *            属性名
     * @param value
     *            属性值
     * @return 返回值
     */
    public static Object write(
            Object object,
            String name,
            Object value) {
        try {
            PropertyDescriptor propertyDescriptor = new PropertyDescriptor(name, object.getClass());
            Method method = propertyDescriptor.getWriteMethod();
            return method.invoke(object, value);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 判断某个类型是否为数值类
     *
     * @param fieldType
     *            字段类型
     * @return 是否为数值类型
     */
    public static boolean isNumberClass(
            Class<?> fieldType) {
        if (fieldType == null) {
            return false;
        }

        if (Number.class.isAssignableFrom(fieldType)) {
            return true;
        }

        return Arrays.stream(BASE_NUMBER_CLASS_LIST).anyMatch(element -> element == fieldType);
    }

    /**
     * 根据类对象获取字段列表
     *
     * @param clazz
     *            类对象
     * @return 字段列表
     */
    public static List<Field> getFileds(
            Class<?> clazz) {
        if (clazz == null) {
            return Collections.emptyList();
        }

        List<Field> allFields = CACHE.get(clazz);

        if (allFields != null) {
            return allFields;
        }

        allFields = new ArrayList<>();

        Deque<Class<?>> classes = new LinkedList<>();

        Class<?> paramsClass = clazz;

        while (true) {
            Class<?> superClass = paramsClass.getSuperclass();

            classes.add(paramsClass);

            if (Object.class.equals(superClass)) {
                break;
            }

            paramsClass = paramsClass.getSuperclass();
        }

        while (!classes.isEmpty()) {
            for (Field field : classes.pollFirst().getDeclaredFields()) {
                allFields.add(field);
            }
        }

        CACHE.put(clazz, allFields);

        return allFields;
    }

    /**
     * 获取过滤后的字段列表
     *
     * @param clazz
     *            类对象
     * @param filters
     *            过滤器列表
     * @return 字段列表
     */
    @SafeVarargs
    public static List<Field> getFiledsWithFilter(
            Class<?> clazz,
            Predicate<Field>... filters) {

        List<Field> list = getFileds(clazz);

        if (list.isEmpty()) {
            return Collections.emptyList();
        }

        Stream<Field> stream = list.stream();

        if (filters != null && filters.length > 0) {
            for (Predicate<Field> filter : filters) {
                if (filter != null) {
                    stream = stream.filter(filter);
                }
            }
        }

        return stream.collect(Collectors.toList());
    }

    /**
     * 使用默认过滤器过滤后的字段列表
     *
     * @param clazz
     *            类对象
     * @return 字段列表
     */
    public static List<Field> getFiledsWithFilter(
            Class<?> clazz) {
        return getFiledsWithFilter(clazz, BASE_FILTER, new CollectionAndArrayFilter());
    }

    /**
     * 字段过滤器，返回过滤后的字段
     *
     * @author guer
     *
     */
    private static class BaseFilter implements Predicate<Field> {

        @Override
        public boolean test(
                Field field) {
            if (field == null) {
                return false;
            }

            Class<?> fieldType = field.getType();

            int mod = field.getModifiers();

            /*
             * 过滤静态和不可变量
             */
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                return false;
            }

            /*
             * 判断是否为系统自带
             */
            boolean isSys = fieldType.getName().startsWith("java") || fieldType.isPrimitive();
            /*
             * 判断是否为枚举
             */
            boolean isEnum = fieldType.isEnum();

            return isSys || isEnum;
        }
    }

    /**
     * 集合类型字段过滤器
     *
     * @author guer
     *
     */
    private static class CollectionAndArrayFilter implements Predicate<Field> {

        @Override
        public boolean test(
                Field field) {
            return field != null && !Collection.class.isAssignableFrom(field.getType()) && !field.getType().isArray();
        }
    }
}
