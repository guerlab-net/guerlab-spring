package net.guerlab.spring.task.entity;

import java.net.InetAddress;
import java.time.LocalDateTime;

/**
 * 任务节点信息
 *
 * @author guer
 *
 */
public class TaskNodeInfo {

    private String threadName = Thread.currentThread().getName();

    private String hostName = getLocalHostName();

    private String className;

    private String applicationName;

    private LocalDateTime createTime = LocalDateTime.now();

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
            return "unKnow";
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
    public void setThreadName(String threadName) {
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
    public void setHostName(String hostName) {
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
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * 返回应用名称
     *
     * @return 应用名称
     */
    public String getApplicationName() {
        return applicationName;
    }

    /**
     * 设置应用名称
     *
     * @param applicationName
     *            应用名称
     */
    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    /**
     * 返回创建时间
     *
     * @return 创建时间
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime
     *            创建时间
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
