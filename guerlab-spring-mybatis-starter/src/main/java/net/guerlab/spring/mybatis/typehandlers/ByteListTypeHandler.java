package net.guerlab.spring.mybatis.typehandlers;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;

import net.guerlab.spring.mybatis.BaseJsonTypeHandler;

/**
 * Byte集合处理
 *
 * @author guer
 *
 */
public class ByteListTypeHandler extends BaseJsonTypeHandler<List<Byte>> {

    public ByteListTypeHandler() {
        super(new TypeReference<List<Byte>>() {
        });
    }

    @Override
    protected List<Byte> getDefault() {
        return new ArrayList<>();
    }
}
