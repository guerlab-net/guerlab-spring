package net.guerlab.spring.locks.redis.entity;

import java.net.InetAddress;
import java.time.LocalDateTime;

/**
 * 节点信息
 *
 * @author guer
 */
public class NodeInfo {

    private String threadName;

    private Long threadId;

    private String hostName;

    private String applicationName;

    private LocalDateTime createTime;

    public NodeInfo() {
        this(null);
    }

    public NodeInfo(String applicationName) {
        threadName = Thread.currentThread().getName();
        threadId = Thread.currentThread().getId();
        hostName = getLocalHostName();
        this.applicationName = applicationName;
        createTime = LocalDateTime.now();
    }

    /**
     * 获取主机名称
     *
     * @return 主机名称
     */
    public static String getLocalHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
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
     *         线程名称
     */
    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    /**
     * 获取线程ID
     *
     * @return 线程ID
     */
    public Long getThreadId() {
        return threadId;
    }

    /**
     * 设置线程ID
     *
     * @param threadId
     *         线程ID
     */
    public void setThreadId(Long threadId) {
        this.threadId = threadId;
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
     *         主机名称
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
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
     *         应用名称
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
     *         创建时间
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
