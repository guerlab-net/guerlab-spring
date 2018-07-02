package net.guerlab.spring.mybatis.typehandlers;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.core.type.TypeReference;

import net.guerlab.spring.mybatis.BaseJsonTypeHandler;

/**
 * Byte集合处理
 *
 * @author guer
 *
 */
public class ByteSetTypeHandler extends BaseJsonTypeHandler<Set<Byte>> {

    public ByteSetTypeHandler() {
        super(new TypeReference<Set<Byte>>() {
        });
    }

    @Override
    protected Set<Byte> getDefault() {
        return new HashSet<>();
    }
}
