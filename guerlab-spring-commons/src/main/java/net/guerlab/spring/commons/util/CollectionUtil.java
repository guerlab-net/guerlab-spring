package net.guerlab.spring.commons.util;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 集合工具类
 *
 * @author guer
 *
 */
public class CollectionUtil {

    private CollectionUtil() {
    }

    /**
     * 判断是否为空集合
     *
     * @param coll
     *            集合
     * @return 否为空集合
     */
    public static boolean isEmpty(
            Collection<?> coll) {
        return coll == null || coll.isEmpty();
    }

    /**
     * 判断是否为非空集合
     *
     * @param coll
     *            集合
     * @return 否为非空集合
     */
    public static boolean isNotEmpty(
            Collection<?> coll) {
        return !isEmpty(coll);
    }

    /**
     * 集合转换为Map结构
     *
     * @param <T>
     *            集合元素类型
     * @param <K>
     *            map结果集key类型
     * @param coll
     *            集合
     * @param keyMapper
     *            key映射关系
     * @return Map结构数据
     */
    public static <T, K> Map<K, T> toMap(
            Collection<T> coll,
            Function<T, K> keyMapper) {
        return toMap(coll, keyMapper, e -> e);
    }

    /**
     * 集合转换为Map结构
     *
     * @param <T>
     *            集合元素类型
     * @param <K>
     *            map结果集key类型
     * @param <U>
     *            map结果集value类型
     * @param coll
     *            集合
     * @param keyMapper
     *            key映射关系
     * @param valueMapper
     *            value映射关系
     * @return Map结构数据
     */
    public static <T, K, U> Map<K, U> toMap(
            Collection<T> coll,
            Function<T, K> keyMapper,
            Function<T, U> valueMapper) {
        return toMap(coll, keyMapper, valueMapper, throwingMerger(), HashMap::new);
    }

    /**
     * 集合转换为Map结构
     *
     * @param <T>
     *            集合元素类型
     * @param <K>
     *            map结果集key类型
     * @param <U>
     *            map结果集value类型
     * @param <M>
     *            map结果集类型
     * @param coll
     *            集合
     * @param keyMapper
     *            key映射关系
     * @param valueMapper
     *            value映射关系
     * @param mergeFunction
     *            值合并方法
     * @param mapSupplier
     *            map构造方法
     * @return Map结构数据
     */
    public static <T, K, U, M extends Map<K, U>> Map<K, U> toMap(
            Collection<T> coll,
            Function<? super T, ? extends K> keyMapper,
            Function<? super T, ? extends U> valueMapper,
            BinaryOperator<U> mergeFunction,
            Supplier<M> mapSupplier) {
        if (isEmpty(coll)) {
            return Collections.emptyMap();
        }

        return stream(coll).collect(Collectors.toMap(keyMapper, valueMapper,
                mergeFunction != null ? mergeFunction : throwingMerger(), mapSupplier));
    }

    private static <V> BinaryOperator<V> throwingMerger() {
        return (
                u,
                v) -> {
            throw new IllegalStateException(String.format("Duplicate key %s", u));
        };
    }

    /**
     * 集合转变为list集合<br>
     * 不转换的内容可在映射关系中返回null来过滤该值
     *
     * @param <I>
     *            输入集合元素类型
     * @param <O>
     *            输出list元素类型
     * @param coll
     *            集合
     * @param mapper
     *            映射关系
     * @return list集合
     */
    public static <I, O> List<O> toList(
            Collection<I> coll,
            Function<I, O> mapper) {
        if (isEmpty(coll)) {
            return Collections.emptyList();
        }

        return stream(coll).map(mapper).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * 集合转变为set集合<br>
     * 不转换的内容可在映射关系中返回null来过滤该值
     *
     * @param <I>
     *            输入集合元素类型
     * @param <O>
     *            输出set元素类型
     * @param coll
     *            集合
     * @param mapper
     *            映射关系
     * @return set集合
     */
    public static <I, O> Set<O> toSet(
            Collection<I> coll,
            Function<I, O> mapper) {
        if (isEmpty(coll)) {
            return Collections.emptySet();
        }

        return stream(coll).map(mapper).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    /**
     * 将集合进行分组
     *
     * @param <E>
     *            输入集合元素类型
     * @param <K>
     *            分组key类型
     * @param coll
     *            集合
     * @param mapper
     *            分组关系
     * @return 分组后的集合map
     */
    public static <K, E> Map<K, List<E>> group(
            Collection<E> coll,
            Function<E, K> mapper) {
        if (isEmpty(coll)) {
            return Collections.emptyMap();
        }

        return stream(coll).collect(Collectors.groupingBy(mapper));
    }

    private static <E> Stream<E> stream(
            Collection<E> coll) {
        return coll.stream().filter(Objects::nonNull);
    }
}
