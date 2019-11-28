package net.guerlab.spring.swagger2.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.List;

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
     * 是否启用swagger
     */
    private boolean enable;

    /**
     * Docket配置列表
     */
    private List<DocketProperties> dockets;

    /**
     * 返回是否启用swagger
     *
     * @return 是否启用swagger
     */
    public boolean isEnable() {
        return enable;
    }

    /**
     * 设置是否启用swagger
     *
     * @param enable
     *            是否启用swagger
     */
    public void setEnable(boolean enable) {
        this.enable = enable;
    }

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
