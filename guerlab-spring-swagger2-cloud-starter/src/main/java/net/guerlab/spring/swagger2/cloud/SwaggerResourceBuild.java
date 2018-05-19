package net.guerlab.spring.swagger2.cloud;

import org.apache.commons.lang3.StringUtils;

import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SwaggerResource;

/**
 * Swagger资源构造器
 *
 * @author guer
 *
 */
public class SwaggerResourceBuild {

    /**
     * 应用名称
     */
    private String name;

    /**
     * 资源路径
     */
    private String path;

    /**
     * 应用组
     */
    private String group = Docket.DEFAULT_GROUP_NAME;

    /**
     * swagger版本
     */
    private String version = "2.0";

    /**
     * 返回应用名称
     *
     * @return 应用名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置应用名称
     *
     * @param name
     *            应用名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 返回资源路径
     *
     * @return 资源路径
     */
    public String getPath() {
        return path;
    }

    /**
     * 设置资源路径
     *
     * @param path
     *            资源路径
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 返回应用组
     *
     * @return 应用组
     */
    public String getGroup() {
        return group;
    }

    /**
     * 设置应用组
     *
     * @param group
     *            应用组
     */
    public void setGroup(String group) {
        this.group = group;
    }

    /**
     * 返回swagger版本
     *
     * @return swagger版本
     */
    public String getVersion() {
        return version;
    }

    /**
     * 设置swagger版本
     *
     * @param version
     *            swagger版本
     */
    public void setVersion(String version) {
        this.version = version;
    }

    public SwaggerResource build() {
        StringBuilder locationBuild = new StringBuilder();

        if (StringUtils.isNotBlank(path)) {
            if (path.startsWith("http") || path.startsWith("://")) {
                locationBuild.append(path);
            } else {
                if (!path.startsWith("/")) {
                    locationBuild.append("/");
                }
                locationBuild.append(path);
            }
        }

        if (locationBuild.charAt(locationBuild.length() - 1) != '/') {
            locationBuild.append("/");
        }

        locationBuild.append("swagger-cloud/v2/api-docs");

        locationBuild.append("?group=");
        if (StringUtils.isNotBlank(group)) {
            locationBuild.append(group);
        } else {
            locationBuild.append(Docket.DEFAULT_GROUP_NAME);
        }

        SwaggerResource swaggerResource = new SwaggerResource();

        swaggerResource.setName(name);
        swaggerResource.setLocation(locationBuild.toString());
        swaggerResource.setSwaggerVersion(version);

        return swaggerResource;
    }
}
