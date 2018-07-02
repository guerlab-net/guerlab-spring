package net.guerlab.spring.mybatis.typehandlers;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.core.type.TypeReference;

import net.guerlab.spring.mybatis.BaseJsonTypeHandler;

/**
 * Double集合处理
 *
 * @author guer
 *
 */
public class DoubleSetTypeHandler extends BaseJsonTypeHandler<Set<Double>> {

    public DoubleSetTypeHandler() {
        super(new TypeReference<Set<Double>>() {
        });
    }

    @Override
    protected Set<Double> getDefault() {
        return new HashSet<>();
    }
}
