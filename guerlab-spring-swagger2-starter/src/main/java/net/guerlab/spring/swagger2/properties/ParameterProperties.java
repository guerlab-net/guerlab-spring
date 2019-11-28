package net.guerlab.spring.swagger2.properties;

/**
 * swagger参数配置
 *
 * @author guerlab
 */
public class ParameterProperties {

    private String name;

    private String description = "";

    private String defaultValue = "";

    private boolean required;

    private boolean allowMultiple;

    private String paramType;

    private String paramAccess;

    private String modelRef = "string";

    private boolean hidden;

    private String pattern;

    private String collectionFormat;

    /**
     * 返回name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置name
     *
     * @param name
     *            name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 返回description
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置description
     *
     * @param description
     *            description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 返回defaultValue
     *
     * @return defaultValue
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * 设置defaultValue
     *
     * @param defaultValue
     *            defaultValue
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * 返回required
     *
     * @return required
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * 设置required
     *
     * @param required
     *            required
     */
    public void setRequired(boolean required) {
        this.required = required;
    }

    /**
     * 返回allowMultiple
     *
     * @return allowMultiple
     */
    public boolean isAllowMultiple() {
        return allowMultiple;
    }

    /**
     * 设置allowMultiple
     *
     * @param allowMultiple
     *            allowMultiple
     */
    public void setAllowMultiple(boolean allowMultiple) {
        this.allowMultiple = allowMultiple;
    }

    /**
     * 返回paramType
     *
     * @return paramType
     */
    public String getParamType() {
        return paramType;
    }

    /**
     * 设置paramType
     *
     * @param paramType
     *            paramType
     */
    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    /**
     * 返回paramAccess
     *
     * @return paramAccess
     */
    public String getParamAccess() {
        return paramAccess;
    }

    /**
     * 设置paramAccess
     *
     * @param paramAccess
     *            paramAccess
     */
    public void setParamAccess(String paramAccess) {
        this.paramAccess = paramAccess;
    }

    /**
     * 返回modelRef
     *
     * @return modelRef
     */
    public String getModelRef() {
        return modelRef;
    }

    /**
     * 设置modelRef
     *
     * @param modelRef
     *            modelRef
     */
    public void setModelRef(String modelRef) {
        this.modelRef = modelRef;
    }

    /**
     * 返回hidden
     *
     * @return hidden
     */
    public boolean isHidden() {
        return hidden;
    }

    /**
     * 设置hidden
     *
     * @param hidden
     *            hidden
     */
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    /**
     * 返回pattern
     *
     * @return pattern
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * 设置pattern
     *
     * @param pattern
     *            pattern
     */
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    /**
     * 返回collectionFormat
     *
     * @return collectionFormat
     */
    public String getCollectionFormat() {
        return collectionFormat;
    }

    /**
     * 设置collectionFormat
     *
     * @param collectionFormat
     *            collectionFormat
     */
    public void setCollectionFormat(String collectionFormat) {
        this.collectionFormat = collectionFormat;
    }
}
