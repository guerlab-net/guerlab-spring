package net.guerlab.spring.swagger2.cloud;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * swagger配置
 *
 * @author guer
 *
 */
@RefreshScope
@ConfigurationProperties("swagger")
public class SwaggerProperties {

    /**
     * 资源列表
     */
    private Map<String, SwaggerResourceBuild> resources;

    /**
     * 返回资源列表
     *
     * @return 资源列表
     */
    public Map<String, SwaggerResourceBuild> getResources() {
        return resources;
    }

    /**
     * 设置资源列表
     *
     * @param resources
     *            资源列表
     */
    public void setResources(Map<String, SwaggerResourceBuild> resources) {
        this.resources = resources;
    }

}
