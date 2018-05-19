package net.guerlab.spring.swagger2.properties;

import java.util.List;

import springfox.documentation.spring.web.plugins.Docket;

/**
 * Docket配置
 *
 * @author guer
 *
 */
public class DocketProperties {

    public static final String ALL_PACKAGE = "*";

    /**
     * 分组名称
     */
    private String groupName = Docket.DEFAULT_GROUP_NAME;

    /**
     * 基础包路径
     */
    private String basePackage = ALL_PACKAGE;

    /**
     * 请求路径正则表达式
     */
    private String pathRegex;

    /**
     * 请求路径ANT表达式
     */
    private String pathAntPattern;

    /**
     * 是否允许默认响应信息
     */
    private boolean applyDefaultResponseMessages;

    /**
     * api信息
     */
    private ApiInfoProperties apiInfo;

    /**
     * 全局请求参数
     */
    private List<ParameterProperties> globalOperationParameters;

    /**
     * 返回分组名称
     *
     * @return 分组名称
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * 设置分组名称
     *
     * @param groupName
     *            分组名称
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * 返回基础包路径
     *
     * @return 基础包路径
     */
    public String getBasePackage() {
        return basePackage;
    }

    /**
     * 设置基础包路径
     *
     * @param basePackage
     *            基础包路径
     */
    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    /**
     * 返回请求路径正则表达式
     *
     * @return 请求路径正则表达式
     */
    public String getPathRegex() {
        return pathRegex;
    }

    /**
     * 设置请求路径正则表达式
     *
     * @param pathRegex
     *            请求路径正则表达式
     */
    public void setPathRegex(String pathRegex) {
        this.pathRegex = pathRegex;
    }

    /**
     * 返回请求路径ANT表达式
     *
     * @return 请求路径ANT表达式
     */
    public String getPathAntPattern() {
        return pathAntPattern;
    }

    /**
     * 设置请求路径ANT表达式
     *
     * @param pathAntPattern
     *            请求路径ANT表达式
     */
    public void setPathAntPattern(String pathAntPattern) {
        this.pathAntPattern = pathAntPattern;
    }

    /**
     * 返回是否允许默认响应信息
     *
     * @return 是否允许默认响应信息
     */
    public boolean isApplyDefaultResponseMessages() {
        return applyDefaultResponseMessages;
    }

    /**
     * 设置是否允许默认响应信息
     *
     * @param applyDefaultResponseMessages
     *            是否允许默认响应信息
     */
    public void setApplyDefaultResponseMessages(boolean applyDefaultResponseMessages) {
        this.applyDefaultResponseMessages = applyDefaultResponseMessages;
    }

    /**
     * 返回api信息
     *
     * @return api信息
     */
    public ApiInfoProperties getApiInfo() {
        return apiInfo;
    }

    /**
     * 设置api信息
     *
     * @param apiInfo
     *            api信息
     */
    public void setApiInfo(ApiInfoProperties apiInfo) {
        this.apiInfo = apiInfo;
    }

    /**
     * 返回全局请求参数
     *
     * @return 全局请求参数
     */
    public List<ParameterProperties> getGlobalOperationParameters() {
        return globalOperationParameters;
    }

    /**
     * 设置全局请求参数
     *
     * @param globalOperationParameters
     *            全局请求参数
     */
    public void setGlobalOperationParameters(List<ParameterProperties> globalOperationParameters) {
        this.globalOperationParameters = globalOperationParameters;
    }
}
