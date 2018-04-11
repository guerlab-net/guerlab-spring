package net.guerlab.spring.commons.geo;

import java.math.BigDecimal;

/**
 * 经纬度
 * 
 * @author guer
 *
 */
public class Geo {

    /**
     * 纬度
     */
    private BigDecimal latitude;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 构造经纬度
     * 
     * @param latitude
     *            纬度
     * @param longitude
     *            经度
     */
    public Geo(
            BigDecimal latitude,
            BigDecimal longitude) {
        super();
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * 返回 latitude
     *
     * @return latitude
     */
    public BigDecimal getLatitude() {
        return latitude;
    }

    /**
     * 设置latitude
     *
     * @param latitude
     *            latitude
     */
    public void setLatitude(
            BigDecimal latitude) {
        this.latitude = latitude;
    }

    /**
     * 返回 longitude
     *
     * @return longitude
     */
    public BigDecimal getLongitude() {
        return longitude;
    }

    /**
     * 设置longitude
     *
     * @param longitude
     *            longitude
     */
    public void setLongitude(
            BigDecimal longitude) {
        this.longitude = longitude;
    }
}
