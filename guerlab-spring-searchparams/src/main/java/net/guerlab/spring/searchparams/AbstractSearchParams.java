/*
 * Copyright 2018-2021 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.guerlab.spring.searchparams;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.Transient;

/**
 * 抽象通用搜索参数对象
 *
 * @author guer
 *
 */
@Schema(name = "AbstractSearchParams", description = "抽象通用搜索参数对象")
public abstract class AbstractSearchParams {

    /**
     * 分页ID
     */
    @Transient
    @Schema(name = "pageId", description = "分页ID", defaultValue = "1")
    protected int pageId = 1;

    /**
     * 分页内容数量
     */
    @Transient
    @Schema(name = "pageId", description = "分页内容数量", defaultValue = "10")
    protected int pageSize = 10;

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
