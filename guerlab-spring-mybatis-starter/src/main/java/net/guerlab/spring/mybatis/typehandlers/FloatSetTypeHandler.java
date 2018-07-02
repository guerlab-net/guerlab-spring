package net.guerlab.spring.mybatis.typehandlers;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.core.type.TypeReference;

import net.guerlab.spring.mybatis.BaseJsonTypeHandler;

/**
 * Float集合处理
 *
 * @author guer
 *
 */
public class FloatSetTypeHandler extends BaseJsonTypeHandler<Set<Float>> {

    public FloatSetTypeHandler() {
        super(new TypeReference<Set<Float>>() {
        });
    }

    @Override
    protected Set<Float> getDefault() {
        return new HashSet<>();
    }
}
