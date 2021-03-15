package net.guerlab.spring.locks.utils;

import net.guerlab.spring.commons.util.SpringApplicationContextUtil;
import net.guerlab.spring.locks.factory.GlobalLockFactory;

/**
 * 全局锁工厂工具类
 *
 * @author guer
 */
public class GlobalLockFactoryUtils {

    private GlobalLockFactoryUtils() {

    }

    /**
     * 获取全局锁工厂
     *
     * @return 全局锁工厂
     */
    public static GlobalLockFactory getFactory() {
        return SpringApplicationContextUtil.getContext().getBean(GlobalLockFactory.class);
    }
}
