package net.guerlab.spring.searchparams.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.guerlab.spring.searchparams.OrderByType;
import net.guerlab.spring.searchparams.SearchModelType;
import net.guerlab.spring.searchparams.SearchParamsHandler;
import net.guerlab.spring.searchparams.SearchParamsUtilInstance;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * mybatis plus处理实例
 *
 * @author guer
 *
 */
public class MyBatisPlusSearchParamsUtilInstance extends SearchParamsUtilInstance {

    public MyBatisPlusSearchParamsUtilInstance() {
        setDefaultHandler(new DefaultHandler());
        addHandler(String.class, new StringHandler());
        addHandler(Collection.class, new CollectionHandler());
        addHandler(OrderByType.class, new OrderByHandler());
    }

    @Override
    public boolean accept(Object object) {
        return object instanceof QueryWrapper;
    }

    private static class DefaultHandler implements SearchParamsHandler {

        @Override
        public void setValue(Object object, String fieldName, String columnName, Object value,
                SearchModelType searchModelType, String customSql) {
            QueryWrapper wrapper = (QueryWrapper) object;
            switch (searchModelType) {
                case GREATER_THAN:
                    wrapper.gt(columnName, value);
                    break;
                case GREATER_THAN_OR_EQUAL_TO:
                    wrapper.ge(columnName, value);
                    break;
                case IS_NOT_NULL:
                    wrapper.isNotNull(columnName);
                    break;
                case IS_NULL:
                    wrapper.isNull(columnName);
                    break;
                case LESS_THAN:
                    wrapper.lt(columnName, value);
                    break;
                case LESS_THAN_OR_EQUAL_TO:
                    wrapper.le(columnName, value);
                    break;
                case NOT_EQUAL_TO:
                case NOT_LIKE:
                case START_NOT_WITH:
                case END_NOT_WITH:
                    wrapper.ne(columnName, value);
                    break;
                case CUSTOM_SQL:
                    if (customSql == null) {
                        break;
                    }

                    CustomerSqlInfo info = new CustomerSqlInfo(customSql);
                    if (info.num > 0) {
                        Object[] values = new Object[info.num];
                        for (int j = 0; j < info.num; j++) {
                            values[j] = value;
                        }
                        wrapper.apply(info.sql, values);
                    } else {
                        wrapper.apply(info.sql);
                    }
                    break;
                default:
                    wrapper.eq(columnName, value);
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

            QueryWrapper wrapper = (QueryWrapper) object;
            switch (searchModelType) {
                case NOT_IN:
                    wrapper.notIn(columnName, list);
                    break;
                case CUSTOM_SQL:
                    if (customSql == null) {
                        break;
                    }

                    CustomerSqlInfo info = new CustomerSqlInfo(customSql);
                    if (info.num > 0) {
                        Object[] values = new Object[info.num];
                        int listSize = list.size() - 1;
                        for (int j = 0; j < info.num; j++) {
                            if (j > listSize) {
                                values[j] = null;
                            } else {
                                values[j] = list.get(j);
                            }
                        }
                        wrapper.apply(info.sql, values);
                    } else {
                        wrapper.apply(info.sql);
                    }
                    break;
                default:
                    wrapper.in(columnName, list);
            }
        }

    }

    private static class OrderByHandler implements SearchParamsHandler {

        @Override
        public void setValue(Object object, String fieldName, String columnName, Object value,
                SearchModelType searchModelType, String customSql) {
            QueryWrapper wrapper = (QueryWrapper) object;
            OrderByType type = (OrderByType) value;

            if (type == OrderByType.DESC) {
                wrapper.orderByDesc(columnName);
            } else {
                wrapper.orderByAsc(columnName);
            }
        }

    }

    private static class StringHandler implements SearchParamsHandler {

        /**
         * 通用匹配符
         */
        private static final char PERCENT = '%';

        @Override
        public void setValue(Object object, String fieldName, String columnName, Object value,
                SearchModelType searchModelType, String customSql) {
            String str = StringUtils.trimToNull((String) value);

            if (str == null) {
                return;
            }

            QueryWrapper wrapper = (QueryWrapper) object;
            switch (searchModelType) {
                case GREATER_THAN:
                    wrapper.gt(columnName, str);
                    break;
                case GREATER_THAN_OR_EQUAL_TO:
                    wrapper.ge(columnName, str);
                    break;
                case IS_NOT_NULL:
                    wrapper.isNotNull(columnName);
                    break;
                case IS_NULL:
                    wrapper.isNull(columnName);
                    break;
                case LESS_THAN:
                    wrapper.lt(columnName, str);
                    break;
                case LESS_THAN_OR_EQUAL_TO:
                    wrapper.le(columnName, str);
                    break;
                case LIKE:
                    wrapper.like(columnName, str);
                    break;
                case NOT_LIKE:
                    wrapper.notLike(columnName, str);
                    break;
                case START_WITH:
                    wrapper.likeRight(columnName, str);
                    break;
                case START_NOT_WITH:
                    wrapper.apply(columnName + " NOT LIKE {0}", str + PERCENT);
                    break;
                case END_WITH:
                    wrapper.likeLeft(columnName, str);
                    break;
                case END_NOT_WITH:
                    wrapper.apply(columnName + " NOT LIKE {0}", PERCENT + str);
                    break;
                case NOT_EQUAL_TO:
                    wrapper.ne(columnName, str);
                    break;
                case CUSTOM_SQL:
                    if (customSql == null) {
                        break;
                    }

                    CustomerSqlInfo info = new CustomerSqlInfo(customSql);
                    if (info.num > 0) {
                        String[] strArray = new String[info.num];
                        for (int j = 0; j < info.num; j++) {
                            strArray[j] = str;
                        }
                        wrapper.apply(info.sql, strArray);
                    } else {
                        wrapper.apply(info.sql);
                    }
                    break;
                default:
                    wrapper.eq(columnName, str);
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
