package net.guerlab.spring.mybatis.typehandlers;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.core.type.TypeReference;

import net.guerlab.spring.mybatis.BaseJsonTypeHandler;

/**
 * Long集合处理
 *
 * @author guer
 *
 */
public class LongSetTypeHandler extends BaseJsonTypeHandler<Set<Long>> {

    public LongSetTypeHandler() {
        super(new TypeReference<Set<Long>>() {
        });
    }

    @Override
    protected Set<Long> getDefault() {
        return new HashSet<>();
    }
}
