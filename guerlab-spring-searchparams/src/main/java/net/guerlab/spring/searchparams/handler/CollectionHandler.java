package net.guerlab.spring.searchparams.handler;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import net.guerlab.spring.searchparams.SearchModelType;
import net.guerlab.spring.searchparams.SearchParamsHandler;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 集合处理
 *
 * @author guer
 *
 */
public class CollectionHandler implements SearchParamsHandler {

    @SuppressWarnings("unchecked")
    @Override
    public void setValue(
            final Example example,
            final String columnName,
            final Object value,
            final SearchModelType searchModelType) {
        Collection<Object> collection = (Collection<Object>) value;

        if (collection.isEmpty()) {
            return;
        }

        Collection<Object> list = collection.stream().filter(Objects::nonNull).collect(Collectors.toList());

        if (list.isEmpty()) {
            return;
        }

        Criteria criteria = example.and();
        if (searchModelType == SearchModelType.NOT_IN) {
            criteria.andNotIn(columnName, list);
        } else {
            criteria.andIn(columnName, list);
        }

    }

}