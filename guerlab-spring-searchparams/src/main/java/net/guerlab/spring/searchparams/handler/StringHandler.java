package net.guerlab.spring.searchparams.handler;

import org.apache.commons.lang3.StringUtils;

import net.guerlab.spring.searchparams.SearchModelType;
import net.guerlab.spring.searchparams.SearchParamsHandler;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @author guer
 *
 */
public class StringHandler implements SearchParamsHandler {

    /**
     * 默认处理实例
     */
    public static final StringHandler INSTANCE = new StringHandler();

    /**
     * 通用匹配符
     */
    private static final char PERCENT = '%';

    @Override
    public void setValue(
            final Example example,
            final String columnName,
            final Object value,
            final SearchModelType searchModelType) {
        String str = (String) value;

        if (StringUtils.isBlank(str)) {
            return;
        }

        str = str.trim();

        Criteria criteria = example.and();
        switch (searchModelType) {
            case EQUAL_TO:
                criteria.andEqualTo(columnName, str);
                break;
            case GREATER_THAN:
                criteria.andGreaterThan(columnName, str);
                break;
            case GREATER_THAN_OR_EQUAL_TO:
                criteria.andGreaterThanOrEqualTo(columnName, str);
                break;
            case IS_NOT_NULL:
                criteria.andIsNotNull(columnName);
                break;
            case IS_NULL:
                criteria.andIsNull(columnName);
                break;
            case LESS_THAN:
                criteria.andLessThan(columnName, str);
                break;
            case LESS_THAN_OR_EQUAL_TO:
                criteria.andLessThanOrEqualTo(columnName, str);
                break;
            case LIKE:
                criteria.andLike(columnName, PERCENT + str + PERCENT);
                break;
            case NOT_LIKE:
                criteria.andNotLike(columnName, PERCENT + str + PERCENT);
                break;
            case START_WITH:
                criteria.andLike(columnName, str + PERCENT);
                break;
            case START_NOT_WITH:
                criteria.andNotLike(columnName, str + PERCENT);
                break;
            case END_WITH:
                criteria.andLike(columnName, PERCENT + str);
                break;
            case END_NOT_WITH:
                criteria.andNotLike(columnName, PERCENT + str);
                break;
            case NOT_EQUAL_TO:
                criteria.andNotEqualTo(columnName, str);
                break;
            default:
                criteria.andEqualTo(columnName, str);
        }

    }

}
