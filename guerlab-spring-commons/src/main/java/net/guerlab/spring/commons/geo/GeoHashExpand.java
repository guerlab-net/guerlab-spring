package net.guerlab.spring.commons.geo;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class GeoHashExpand {

    @JsonIgnore
    private String center;

    @JsonIgnore
    private String top;

    @JsonIgnore
    private String bottom;

    @JsonIgnore
    private String right;

    @JsonIgnore
    private String left;

    @JsonIgnore
    private String topLeft;

    @JsonIgnore
    private String topRight;

    @JsonIgnore
    private String bottomRight;

    @JsonIgnore
    private String bottomLeft;

    public GeoHashExpand() {
    }

    public GeoHashExpand(
            String center,
            String top,
            String bottom,
            String right,
            String left,
            String topLeft,
            String topRight,
            String bottomRight,
            String bottomLeft) {
        this.center = center;
        this.top = top;
        this.bottom = bottom;
        this.right = right;
        this.left = left;
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomRight = bottomRight;
        this.bottomLeft = bottomLeft;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(
            String center) {
        this.center = center;
    }

    public String getTop() {
        return top;
    }

    public void setTop(
            String top) {
        this.top = top;
    }

    public String getBottom() {
        return bottom;
    }

    public void setBottom(
            String bottom) {
        this.bottom = bottom;
    }

    public String getRight() {
        return right;
    }

    public void setRight(
            String right) {
        this.right = right;
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(
            String left) {
        this.left = left;
    }

    public String getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(
            String topLeft) {
        this.topLeft = topLeft;
    }

    public String getTopRight() {
        return topRight;
    }

    public void setTopRight(
            String topRight) {
        this.topRight = topRight;
    }

    public String getBottomRight() {
        return bottomRight;
    }

    public void setBottomRight(
            String bottomRight) {
        this.bottomRight = bottomRight;
    }

    public String getBottomLeft() {
        return bottomLeft;
    }

    public void setBottomLeft(
            String bottomLeft) {
        this.bottomLeft = bottomLeft;
    }
}
