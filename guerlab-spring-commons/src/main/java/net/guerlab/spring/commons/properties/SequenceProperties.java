/*
 * Copyright 2018-2021 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.guerlab.spring.commons.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * 序列配置
 *
 * @author guer
 *
 */
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
    public void setWorkerId(long workerId) {
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
    public void setDataCenterId(long dataCenterId) {
        this.dataCenterId = dataCenterId;
    }
}
