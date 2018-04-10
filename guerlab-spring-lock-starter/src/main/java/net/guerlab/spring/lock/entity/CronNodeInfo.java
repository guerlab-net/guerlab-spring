package net.guerlab.spring.lock.entity;

import java.net.InetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 任务节点信息
 *
 * @author guer
 *
 */
public class CronNodeInfo {

    private static final Logger LOGGER = LoggerFactory.getLogger(CronNodeInfo.class);

    private String threadName = Thread.currentThread().getName();

    private String hostName = getLocalHostName();

    private String className;

    public CronNodeInfo() {
    }

    public CronNodeInfo(Class<?> clazz) {
        className = clazz.getName();
    }

    /**
     * 获取主机名称
     *
     * @return 主机名称
     */
    public static String getLocalHostName() {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            return localHost.getHostName();
        } catch (Exception e) {
            LOGGER.debug(e.getMessage(), e);
            return "unknow";
        }
    }

    /**
     * 返回线程名称
     *
     * @return 线程名称
     */
    public String getThreadName() {
        return threadName;
    }

    /**
     * 设置线程名称
     *
     * @param threadName
     *            线程名称
     */
    public void setThreadName(
            String threadName) {
        this.threadName = threadName;
    }

    /**
     * 返回主机名称
     *
     * @return 主机名称
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * 设置主机名称
     *
     * @param hostName
     *            主机名称
     */
    public void setHostName(
            String hostName) {
        this.hostName = hostName;
    }

    /**
     * 返回类名称
     *
     * @return 类名称
     */
    public String getClassName() {
        return className;
    }

    /**
     * 设置类名称
     *
     * @param className
     *            类名称
     */
    public void setClassName(
            String className) {
        this.className = className;
    }
}
