package net.guerlab.spring.searchparams.handler;

import net.guerlab.spring.searchparams.OrderByType;
import net.guerlab.spring.searchparams.SearchModelType;
import net.guerlab.spring.searchparams.SearchParamsHandler;
import tk.mybatis.mapper.entity.Example;

/**
 * 排序处理
 * 
 * @author guer
 *
 */
public class OrderByHandler implements SearchParamsHandler {

    @Override
    public void setValue(
            final Example example,
            final String columnName,
            final Object value,
            final SearchModelType searchModelType) {
        OrderByType type = (OrderByType) value;

        if (type == OrderByType.DESC) {
            example.orderBy(columnName).desc();
        } else {
            example.orderBy(columnName).asc();
        }

    }

}