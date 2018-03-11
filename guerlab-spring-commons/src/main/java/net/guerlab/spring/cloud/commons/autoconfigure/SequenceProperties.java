package net.guerlab.spring.cloud.commons.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
@ConfigurationProperties("sequence")
public class SequenceProperties {

    private long workerId;

    private long dataCenterId;

    /**
     * 返回workerId
     *
     * @return workerId
     */
    public long getWorkerId() {
        return workerId;
    }

    /**
     * 设置workerId
     *
     * @param workerId
     *            workerId
     */
    public void setWorkerId(
            long workerId) {
        this.workerId = workerId;
    }

    /**
     * 返回dataCenterId
     *
     * @return dataCenterId
     */
    public long getDataCenterId() {
        return dataCenterId;
    }

    /**
     * 设置dataCenterId
     *
     * @param dataCenterId
     *            dataCenterId
     */
    public void setDataCenterId(
            long dataCenterId) {
        this.dataCenterId = dataCenterId;
    }
}
