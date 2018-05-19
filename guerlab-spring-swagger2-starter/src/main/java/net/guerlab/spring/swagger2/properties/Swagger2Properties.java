package net.guerlab.spring.swagger2.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * swagger2配置
 *
 * @author guer
 *
 */
@RefreshScope
@ConfigurationProperties("swagger")
public class Swagger2Properties {

    /**
     * Docket配置列表
     */
    private List<DocketProperties> dockets;

    /**
     * 返回Docket配置列表
     *
     * @return Docket配置列表
     */
    public List<DocketProperties> getDockets() {
        return dockets;
    }

    /**
     * 设置Docket配置列表
     *
     * @param dockets
     *            Docket配置列表
     */
    public void setDockets(List<DocketProperties> dockets) {
        this.dockets = dockets;
    }

}
