package net.guerlab.spring.mybatis.typehandlers;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.core.type.TypeReference;

import net.guerlab.spring.mybatis.BaseJsonTypeHandler;

/**
 * Integer集合处理
 *
 * @author guer
 *
 */
public class IntegerSetTypeHandler extends BaseJsonTypeHandler<Set<Integer>> {

    public IntegerSetTypeHandler() {
        super(new TypeReference<Set<Integer>>() {
        });
    }

    @Override
    protected Set<Integer> getDefault() {
        return new HashSet<>();
    }
}
