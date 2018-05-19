package net.guerlab.spring.searchparams;

import javax.persistence.Transient;

/**
 * 抽象通用搜索参数对象
 *
 * @author guer
 *
 */
public abstract class AbstractSearchParams {

    /**
     * 分页ID
     */
    @Transient
    protected transient int pageId = 1;

    /**
     * 分页内容数量
     */
    @Transient
    protected transient int pageSize = 10;

    /**
     * 获取分页ID
     *
     * @return 分页ID
     */
    public final int getPageId() {
        return pageId;
    }

    /**
     * 设置分页ID
     *
     * @param pageId
     *            分页ID
     */
    public final void setPageId(final int pageId) {
        this.pageId = pageId;
    }

    /**
     * 获取分页内容数量
     *
     * @return 分页内容数量
     */
    public final int getPageSize() {
        return pageSize;
    }

    /**
     * 设置分页内容数量
     *
     * @param pageSize
     *            分页内容数量
     */
    public final void setPageSize(final int pageSize) {
        this.pageSize = pageSize;
    }
}
