package net.guerlab.spring.mybatis.typehandlers;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;

import net.guerlab.spring.mybatis.BaseJsonTypeHandler;

/**
 * Integer集合处理
 *
 * @author guer
 *
 */
public class IntegerListTypeHandler extends BaseJsonTypeHandler<List<Integer>> {

    public IntegerListTypeHandler() {
        super(new TypeReference<List<Integer>>() {
        });
    }

    @Override
    protected List<Integer> getDefault() {
        return new ArrayList<>();
    }
}
