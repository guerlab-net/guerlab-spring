package net.guerlab.spring.searchparams.handler;

import net.guerlab.spring.searchparams.SearchModelType;
import net.guerlab.spring.searchparams.SearchParamsHandler;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 默认处理
 * 
 * @author guer
 *
 */
public class DefaultHandler implements SearchParamsHandler {

    @Override
    public void setValue(
            final Example example,
            final String columnName,
            final Object value,
            final SearchModelType searchModelType) {
        Criteria criteria = example.and();
        switch (searchModelType) {
            case GREATER_THAN:
                criteria.andGreaterThan(columnName, value);
                break;
            case GREATER_THAN_OR_EQUAL_TO:
                criteria.andGreaterThanOrEqualTo(columnName, value);
                break;
            case IS_NOT_NULL:
                criteria.andIsNotNull(columnName);
                break;
            case IS_NULL:
                criteria.andIsNull(columnName);
                break;
            case LESS_THAN:
                criteria.andLessThan(columnName, value);
                break;
            case LESS_THAN_OR_EQUAL_TO:
                criteria.andLessThanOrEqualTo(columnName, value);
                break;
            case EQUAL_TO:
            case LIKE:
            case START_WITH:
            case END_WITH:
                criteria.andEqualTo(columnName, value);
                break;
            case NOT_EQUAL_TO:
            case NOT_LIKE:
            case START_NOT_WITH:
            case END_NOT_WITH:
                criteria.andNotEqualTo(columnName, value);
                break;
            default:
                criteria.andEqualTo(columnName, value);
        }
    }

}