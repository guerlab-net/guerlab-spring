package net.guerlab.spring.mybatis.typehandlers;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.core.type.TypeReference;

import net.guerlab.spring.mybatis.BaseJsonTypeHandler;

/**
 * Short集合处理
 *
 * @author guer
 *
 */
public class ShortSetTypeHandler extends BaseJsonTypeHandler<Set<Short>> {

    public ShortSetTypeHandler() {
        super(new TypeReference<Set<Short>>() {
        });
    }

    @Override
    protected Set<Short> getDefault() {
        return new HashSet<>();
    }
}
