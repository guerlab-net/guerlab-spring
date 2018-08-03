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
     * 是否格式化Byte
     */
    private boolean formatByte;

    /**
     * 是否格式化Short
     */
    private boolean formatShort;

    /**
     * 是否格式化Integer
     */
    private boolean formatInteger;

    /**
     * 是否格式化Long
     */
    private boolean formatLong;

    /**
     * 是否格式化Float
     */
    private boolean formatFloat;

    /**
     * 是否格式化Double
     */
    private boolean formatDouble;

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
     * 返回是否格式化Byte
     *
     * @return 是否格式化Byte
     */
    public boolean isFormatByte() {
        return formatByte;
    }

    /**
     * 设置是否格式化Byte
     *
     * @param formatByte
     *            是否格式化Byte
     */
    public void setFormatByte(boolean formatByte) {
        this.formatByte = formatByte;
    }

    /**
     * 返回是否格式化Short
     *
     * @return 是否格式化Short
     */
    public boolean isFormatShort() {
        return formatShort;
    }

    /**
     * 设置是否格式化Short
     *
     * @param formatShort
     *            是否格式化Short
     */
    public void setFormatShort(boolean formatShort) {
        this.formatShort = formatShort;
    }

    /**
     * 返回是否格式化Integer
     *
     * @return 是否格式化Integer
     */
    public boolean isFormatInteger() {
        return formatInteger;
    }

    /**
     * 设置是否格式化Integer
     *
     * @param formatInteger
     *            是否格式化Integer
     */
    public void setFormatInteger(boolean formatInteger) {
        this.formatInteger = formatInteger;
    }

    /**
     * 返回是否格式化Long
     *
     * @return 是否格式化Long
     */
    public boolean isFormatLong() {
        return formatLong;
    }

    /**
     * 设置是否格式化Long
     *
     * @param formatLong
     *            是否格式化Long
     */
    public void setFormatLong(boolean formatLong) {
        this.formatLong = formatLong;
    }

    /**
     * 返回是否格式化Float
     *
     * @return 是否格式化Float
     */
    public boolean isFormatFloat() {
        return formatFloat;
    }

    /**
     * 设置是否格式化Float
     *
     * @param formatFloat
     *            是否格式化Float
     */
    public void setFormatFloat(boolean formatFloat) {
        this.formatFloat = formatFloat;
    }

    /**
     * 返回是否格式化Double
     *
     * @return 是否格式化Double
     */
    public boolean isFormatDouble() {
        return formatDouble;
    }

    /**
     * 设置是否格式化Double
     *
     * @param formatDouble
     *            是否格式化Double
     */
    public void setFormatDouble(boolean formatDouble) {
        this.formatDouble = formatDouble;
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
