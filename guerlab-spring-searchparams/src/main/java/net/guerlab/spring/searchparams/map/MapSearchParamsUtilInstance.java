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
package net.guerlab.spring.searchparams.map;

import net.guerlab.spring.searchparams.AbstractSearchParams;
import net.guerlab.spring.searchparams.SearchModelType;
import net.guerlab.spring.searchparams.SearchParamsHandler;
import net.guerlab.spring.searchparams.SearchParamsUtilInstance;

import java.util.Map;

/**
 * Map处理实例
 *
 * @author guer
 *
 */
public class MapSearchParamsUtilInstance extends SearchParamsUtilInstance {

    /**
     * 分页关键字-页面ID
     */
    public static final String KEY_PAGE_ID = "pageId";

    /**
     * 分页关键字-页面尺寸
     */
    public static final String KEY_PAGE_SIZE = "pageSize";

    /**
     * 分页关键字-偏移量
     */
    public static final String KEY_PAGE_OFFSET = "pageOffset";

    /**
     * 分页关键字-起始位索引
     */
    public static final String KEY_PAGE_START_INDEX = "pageStartIndex";

    /**
     * 分页关键字-结束位索引
     */
    public static final String KEY_PAGE_END_INDEX = "pageEndIndex";

    public MapSearchParamsUtilInstance() {
        setDefaultHandler(new DefaultHandler());
    }

    @Override
    public boolean accept(Object object) {
        return object instanceof Map;
    }

    private static class DefaultHandler implements SearchParamsHandler {

        @Override
        public void setValue(Object object, String fieldName, String columnName, Object value, SearchModelType searchModelType, String customSql) {
            ((Map) object).put(fieldName, value);
        }
    }

    @Override
    public void afterHandler(AbstractSearchParams searchParams, Object object) {
        Map map = (Map) object;

        int pageId = Math.max(searchParams.getPageId(), 1);
        int pageSize = searchParams.getPageSize();
        int pageOffset = (pageId - 1) * pageSize;
        int pageEndIndex = pageOffset + pageSize;

        map.put(KEY_PAGE_ID, pageId);
        map.put(KEY_PAGE_SIZE, pageSize);
        map.put(KEY_PAGE_OFFSET, pageOffset);
        map.put(KEY_PAGE_START_INDEX, pageOffset);
        map.put(KEY_PAGE_END_INDEX, pageEndIndex);
    }
}
