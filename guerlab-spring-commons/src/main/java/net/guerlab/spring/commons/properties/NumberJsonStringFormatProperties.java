package net.guerlab.spring.commons.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * 数值json格式化配置,用于将数值类型在json格式化处理的时候处理为字符串类型,避免精度溢出
 *
 * @author guer
 *
 */
@RefreshScope
@ConfigurationProperties("spring.jackson.format")
public class NumberJsonStringFormatProperties {

    /**
     * 是否格式化所有数值类型
     */
    private boolean formatAllNumber;

    /**
     * 是否格式化BigDecimal
     */
    private boolean formatBigDecimal;

    /**
     * 是否格式化BigInteger
     */
    private boolean formatBigInteger;

    /**
     * 是否格式化Byte包装类
     */
    private boolean formatByteClass;

    /**
     * 是否格式化Byte基本类型
     */
    private boolean formatByteType;

    /**
     * 是否格式化Short包装类
     */
    private boolean formatShortClass;

    /**
     * 是否格式化Short基本类型
     */
    private boolean formatShortType;

    /**
     * 是否格式化Integer包装类
     */
    private boolean formatIntegerClass;

    /**
     * 是否格式化Integer基本类型
     */
    private boolean formatIntegerType;

    /**
     * 是否格式化Long包装类
     */
    private boolean formatLongClass;

    /**
     * 是否格式化Long基本类型
     */
    private boolean formatLongType;

    /**
     * 是否格式化Float包装类
     */
    private boolean formatFloatClass;

    /**
     * 是否格式化Float基本类型
     */
    private boolean formatFloatType;

    /**
     * 是否格式化Double包装类
     */
    private boolean formatDoubleClass;

    /**
     * 是否格式化Double基本类型
     */
    private boolean formatDoubleType;

    /**
     * 待格式化数值类型类
     */
    private List<Class<? extends Number>> formatNumberClassList;

    /**
     * 返回是否格式化所有数值类型
     *
     * @return 是否格式化所有数值类型
     */
    public boolean isFormatAllNumber() {
        return formatAllNumber;
    }

    /**
     * 设置是否格式化所有数值类型
     *
     * @param formatAllNumber
     *            是否格式化所有数值类型
     */
    public void setFormatAllNumber(boolean formatAllNumber) {
        this.formatAllNumber = formatAllNumber;
    }

    /**
     * 返回是否格式化BigDecimal
     *
     * @return 是否格式化BigDecimal
     */
    public boolean isFormatBigDecimal() {
        return formatBigDecimal;
    }

    /**
     * 设置是否格式化BigDecimal
     *
     * @param formatBigDecimal
     *            是否格式化BigDecimal
     */
    public void setFormatBigDecimal(boolean formatBigDecimal) {
        this.formatBigDecimal = formatBigDecimal;
    }

    /**
     * 返回是否格式化BigInteger
     *
     * @return 是否格式化BigInteger
     */
    public boolean isFormatBigInteger() {
        return formatBigInteger;
    }

    /**
     * 设置是否格式化BigInteger
     *
     * @param formatBigInteger
     *            是否格式化BigInteger
     */
    public void setFormatBigInteger(boolean formatBigInteger) {
        this.formatBigInteger = formatBigInteger;
    }

    /**
     * 返回是否格式化Byte包装类
     *
     * @return 是否格式化Byte包装类
     */
    public boolean isFormatByteClass() {
        return formatByteClass;
    }

    /**
     * 设置是否格式化Byte包装类
     *
     * @param formatByteClass
     *            是否格式化Byte包装类
     */
    public void setFormatByteClass(boolean formatByteClass) {
        this.formatByteClass = formatByteClass;
    }

    /**
     * 返回是否格式化Byte基本类型
     *
     * @return 是否格式化Byte基本类型
     */
    public boolean isFormatByteType() {
        return formatByteType;
    }

    /**
     * 设置是否格式化Byte基本类型
     *
     * @param formatByteType
     *            是否格式化Byte基本类型
     */
    public void setFormatByteType(boolean formatByteType) {
        this.formatByteType = formatByteType;
    }

    /**
     * 返回是否格式化Short包装类
     *
     * @return 是否格式化Short包装类
     */
    public boolean isFormatShortClass() {
        return formatShortClass;
    }

    /**
     * 设置是否格式化Short包装类
     *
     * @param formatShortClass
     *            是否格式化Short包装类
     */
    public void setFormatShortClass(boolean formatShortClass) {
        this.formatShortClass = formatShortClass;
    }

    /**
     * 返回是否格式化Short基本类型
     *
     * @return 是否格式化Short基本类型
     */
    public boolean isFormatShortType() {
        return formatShortType;
    }

    /**
     * 设置是否格式化Short基本类型
     *
     * @param formatShortType
     *            是否格式化Short基本类型
     */
    public void setFormatShortType(boolean formatShortType) {
        this.formatShortType = formatShortType;
    }

    /**
     * 返回是否格式化Integer包装类
     *
     * @return 是否格式化Integer包装类
     */
    public boolean isFormatIntegerClass() {
        return formatIntegerClass;
    }

    /**
     * 设置是否格式化Integer包装类
     *
     * @param formatIntegerClass
     *            是否格式化Integer包装类
     */
    public void setFormatIntegerClass(boolean formatIntegerClass) {
        this.formatIntegerClass = formatIntegerClass;
    }

    /**
     * 返回是否格式化Integer基本类型
     *
     * @return 是否格式化Integer基本类型
     */
    public boolean isFormatIntegerType() {
        return formatIntegerType;
    }

    /**
     * 设置是否格式化Integer基本类型
     *
     * @param formatIntegerType
     *            是否格式化Integer基本类型
     */
    public void setFormatIntegerType(boolean formatIntegerType) {
        this.formatIntegerType = formatIntegerType;
    }

    /**
     * 返回是否格式化Long包装类
     *
     * @return 是否格式化Long包装类
     */
    public boolean isFormatLongClass() {
        return formatLongClass;
    }

    /**
     * 设置是否格式化Long包装类
     *
     * @param formatLongClass
     *            是否格式化Long包装类
     */
    public void setFormatLongClass(boolean formatLongClass) {
        this.formatLongClass = formatLongClass;
    }

    /**
     * 返回是否格式化Long基本类型
     *
     * @return 是否格式化Long基本类型
     */
    public boolean isFormatLongType() {
        return formatLongType;
    }

    /**
     * 设置是否格式化Long基本类型
     *
     * @param formatLongType
     *            是否格式化Long基本类型
     */
    public void setFormatLongType(boolean formatLongType) {
        this.formatLongType = formatLongType;
    }

    /**
     * 返回是否格式化Float包装类
     *
     * @return 是否格式化Float包装类
     */
    public boolean isFormatFloatClass() {
        return formatFloatClass;
    }

    /**
     * 设置是否格式化Float包装类
     *
     * @param formatFloatClass
     *            是否格式化Float包装类
     */
    public void setFormatFloatClass(boolean formatFloatClass) {
        this.formatFloatClass = formatFloatClass;
    }

    /**
     * 返回是否格式化Float基本类型
     *
     * @return 是否格式化Float基本类型
     */
    public boolean isFormatFloatType() {
        return formatFloatType;
    }

    /**
     * 设置是否格式化Float基本类型
     *
     * @param formatFloatType
     *            是否格式化Float基本类型
     */
    public void setFormatFloatType(boolean formatFloatType) {
        this.formatFloatType = formatFloatType;
    }

    /**
     * 返回是否格式化Double包装类
     *
     * @return 是否格式化Double包装类
     */
    public boolean isFormatDoubleClass() {
        return formatDoubleClass;
    }

    /**
     * 设置是否格式化Double包装类
     *
     * @param formatDoubleClass
     *            是否格式化Double包装类
     */
    public void setFormatDoubleClass(boolean formatDoubleClass) {
        this.formatDoubleClass = formatDoubleClass;
    }

    /**
     * 返回是否格式化Double基本类型
     *
     * @return 是否格式化Double基本类型
     */
    public boolean isFormatDoubleType() {
        return formatDoubleType;
    }

    /**
     * 设置是否格式化Double基本类型
     *
     * @param formatDoubleType
     *            是否格式化Double基本类型
     */
    public void setFormatDoubleType(boolean formatDoubleType) {
        this.formatDoubleType = formatDoubleType;
    }

    /**
     * 返回待格式化数值类型类
     *
     * @return 待格式化数值类型类
     */
    public List<Class<? extends Number>> getFormatNumberClassList() {
        return formatNumberClassList;
    }

    /**
     * 设置待格式化数值类型类
     *
     * @param formatNumberClassList
     *            待格式化数值类型类
     */
    public void setFormatNumberClassList(List<Class<? extends Number>> formatNumberClassList) {
        this.formatNumberClassList = formatNumberClassList;
    }
}
