package net.guerlab.spring.searchparams.tk.mapper;

import net.guerlab.spring.searchparams.OrderByType;
import net.guerlab.spring.searchparams.SearchModelType;
import net.guerlab.spring.searchparams.SearchParamsHandler;
import net.guerlab.spring.searchparams.SearchParamsUtilInstance;
import org.apache.commons.lang3.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * tk.mapper处理实例
 *
 * @author guer
 *
 */
public class TkMapperSearchParamsUtilInstance extends SearchParamsUtilInstance {

    public TkMapperSearchParamsUtilInstance() {
        setDefaultHandler(new DefaultHandler());
        addHandler(String.class, new StringHandler());
        addHandler(Collection.class, new CollectionHandler());
        addHandler(OrderByType.class, new OrderByHandler());
    }

    @Override
    public boolean accept(Object object) {
        return object instanceof Example;
    }

    private static class DefaultHandler implements SearchParamsHandler {

        @Override
        public void setValue(Object object, String fieldName, String columnName, Object value,
                SearchModelType searchModelType, String customSql) {
            Example example = (Example) object;
            Example.Criteria criteria = example.and();
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
                case NOT_EQUAL_TO:
                case NOT_LIKE:
                case START_NOT_WITH:
                case END_NOT_WITH:
                    criteria.andNotEqualTo(columnName, value);
                    break;
                case CUSTOM_SQL:
                    if (customSql == null) {
                        break;
                    }

                    criteria.andCondition(customSql.replaceAll("\\?", "'" + value + "'"));
                    break;
                default:
                    criteria.andEqualTo(columnName, value);
            }
        }
    }

    private static class CollectionHandler implements SearchParamsHandler {

        @SuppressWarnings("unchecked")
        @Override
        public void setValue(Object object, String fieldName, String columnName, Object value,
                SearchModelType searchModelType, String customSql) {
            Collection<Object> collection = (Collection<Object>) value;

            if (collection.isEmpty()) {
                return;
            }

            List<Object> list = collection.stream().filter(Objects::nonNull).collect(Collectors.toList());

            if (list.isEmpty()) {
                return;
            }

            Example example = (Example) object;
            Example.Criteria criteria = example.and();
            switch (searchModelType) {
                case NOT_IN:
                    criteria.andNotIn(columnName, list);
                    break;
                case CUSTOM_SQL:
                    if (customSql == null) {
                        break;
                    }

                    CustomerSqlInfo info = new CustomerSqlInfo(customSql);
                    if (info.num > 0) {
                        int listSize = list.size() - 1;
                        for (int j = 0; j < info.num; j++) {
                            Object val;
                            if (j > listSize) {
                                val = null;
                            } else {
                                val = list.get(j);
                            }
                            customSql = customSql.replaceFirst("\\?", "'" + val + "'");
                        }
                    }
                    criteria.andCondition(customSql);
                    break;
                default:
                    criteria.andIn(columnName, list);
            }
        }

    }

    private static class OrderByHandler implements SearchParamsHandler {

        @Override
        public void setValue(Object object, String fieldName, String columnName, Object value,
                SearchModelType searchModelType, String customSql) {
            Example example = (Example) object;
            OrderByType type = (OrderByType) value;

            if (type == OrderByType.DESC) {
                example.orderBy(columnName).desc();
            } else {
                example.orderBy(columnName).asc();
            }

        }

    }

    private static class StringHandler implements SearchParamsHandler {

        /**
         * 通用匹配符
         */
        private static final char PERCENT = '%';

        @Override
        public void setValue(Object object, String fieldName, String columnName, Object value, SearchModelType searchModelType, String customSql) {
            String str = StringUtils.trimToNull((String) value);

            if (str == null) {
                return;
            }

            Example example = (Example) object;
            Example.Criteria criteria = example.and();
            switch (searchModelType) {
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
                case CUSTOM_SQL:
                    if (customSql == null) {
                        break;
                    }

                    criteria.andCondition(customSql.replaceAll("\\?", "'" + str + "'"));
                    break;
                default:
                    criteria.andEqualTo(columnName, str);
            }

        }

    }

    private static class CustomerSqlInfo {

        private int num = 0;

        private String sql;

        public CustomerSqlInfo(String sql) {
            this.sql = sql;
            format();
        }

        public void format() {
            if (sql == null) {
                return;
            }

            while (true) {
                int index = sql.indexOf("?");
                if (index < 0) {
                    break;
                }
                sql = sql.replaceFirst("\\?", "{" + num + "}");
                num++;
            }
        }
    }
}
