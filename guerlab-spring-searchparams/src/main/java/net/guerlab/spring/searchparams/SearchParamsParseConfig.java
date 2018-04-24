package net.guerlab.spring.searchparams;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.guerlab.spring.searchparams.handler.CollectionHandler;
import net.guerlab.spring.searchparams.handler.DefaultHandler;
import net.guerlab.spring.searchparams.handler.OrderByHandler;
import net.guerlab.spring.searchparams.handler.StringHandler;

/**
 * SearchParams解析配置
 *
 * @author guer
 *
 */
public class SearchParamsParseConfig {

    private static final SearchParamsParseConfig GLOBAL;

    private final Map<Type, SearchParamsHandler> handlers = new HashMap<>();

    private SearchParamsHandler defaultHandler = new DefaultHandler();

    static {
        GLOBAL = new SearchParamsParseConfig();
    }

    /**
     * 实例化SearchParams类解析配置
     */
    public SearchParamsParseConfig() {
        handlers.put(String.class, new StringHandler());
        handlers.put(Collection.class, new CollectionHandler());
        handlers.put(OrderByType.class, new OrderByHandler());
    }

    /**
     * 获取全局实例
     *
     * @return 全局实例
     */
    public static SearchParamsParseConfig getGlobalInstance() {
        return GLOBAL;
    }

    /**
     * 设置默认处理器对象
     *
     * @param handler
     *            处理器
     */
    public void setDefaultHandler(
            SearchParamsHandler handler) {
        defaultHandler = handler;
    }

    /**
     * 添加处理器对象
     *
     * @param type
     *            数据类型
     * @param handler
     *            处理器
     */
    public void addHandler(
            Type type,
            SearchParamsHandler handler) {
        if (type != null && handler != null) {
            handlers.put(type, handler);
        }
    }

    /**
     * 获取处理器对象
     *
     * @param type
     *            数据类型
     * @return 处理器对象
     */
    public SearchParamsHandler getHandler(
            Type type) {
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
}
