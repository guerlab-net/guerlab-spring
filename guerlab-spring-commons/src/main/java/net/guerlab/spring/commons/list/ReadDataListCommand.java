package net.guerlab.spring.commons.list;

import java.util.List;

/**
 * 读取数据列表命令
 *
 * @author guer
 *
 * @param <T>
 *            参数类型
 */
@FunctionalInterface
public interface ReadDataListCommand<T> {

    /**
     * 获取数据列表
     *
     * @return 数据列表
     */
    List<T> getData();
}
