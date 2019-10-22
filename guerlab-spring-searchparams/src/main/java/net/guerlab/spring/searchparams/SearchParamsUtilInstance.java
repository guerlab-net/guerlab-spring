package net.guerlab.spring.searchparams;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 搜索参数工具实例
 *
 * @author guer
 *
 */
public abstract class SearchParamsUtilInstance {

    private final Map<Type, SearchParamsHandler> handlers = new HashMap<>();

    private SearchParamsHandler defaultHandler;

    /**
     * 判断是否允许
     *
     * @param object
     *         输出对象
     * @return 返回true表示进行处理，否则不进行处理
     */
    public abstract boolean accept(Object object);

    /**
     * 设置默认处理器对象
     *
     * @param handler
     *         处理器
     */
    public void setDefaultHandler(SearchParamsHandler handler) {
        defaultHandler = handler;
    }

    /**
     * 添加处理器对象
     *
     * @param type
     *         数据类型
     * @param handler
     *         处理器
     */
    public void addHandler(Type type, SearchParamsHandler handler) {
        if (type != null && handler != null) {
            handlers.put(type, handler);
        }
    }

    /**
     * 获取处理器对象
     *
     * @param type
     *         数据类型
     * @return 处理器对象
     */
    public SearchParamsHandler getHandler(Type type) {
        SearchParamsHandler handler = handlers.get(type);

        if (handler != null) {
            return handler;
        }

        if (!(type instanceof Class<?>)) {
            return defaultHandler;
        }

        Class<?> clazz = (Class<?>) type;

        if (clazz.isEnum()) {
            handler = handlers.get(Enum.class);
        } else if (Collection.class.isAssignableFrom(clazz)) {
            handler = handlers.get(Collection.class);
        }

        return handler == null ? defaultHandler : handler;
    }

    /**
     * 后置处理
     *
     * @param searchParams
     *         搜索参数
     * @param object
     *         输出对象
     */
    public void afterHandler(final AbstractSearchParams searchParams, final Object object) {

    }

}
