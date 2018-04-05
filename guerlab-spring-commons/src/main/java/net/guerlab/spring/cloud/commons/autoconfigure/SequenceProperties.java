package net.guerlab.spring.cloud.commons.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * 序列配置
 *
 * @author guer
 *
 */
@Component
@RefreshScope
@ConfigurationProperties("sequence")
public class SequenceProperties {

    /**
     * 工作节点ID
     */
    private long workerId;

    /**
     * 数据中心ID
     */
    private long dataCenterId;

    /**
     * 返回工作节点ID
     *
     * @return 工作节点ID
     */
    public long getWorkerId() {
        return workerId;
    }

    /**
     * 设置工作节点ID
     *
     * @param workerId
     *            工作节点ID
     */
    public void setWorkerId(
            long workerId) {
        this.workerId = workerId;
    }

    /**
     * 返回数据中心ID
     *
     * @return 数据中心ID
     */
    public long getDataCenterId() {
        return dataCenterId;
    }

    /**
     * 设置数据中心ID
     *
     * @param dataCenterId
     *            数据中心ID
     */
    public void setDataCenterId(
            long dataCenterId) {
        this.dataCenterId = dataCenterId;
    }
}
