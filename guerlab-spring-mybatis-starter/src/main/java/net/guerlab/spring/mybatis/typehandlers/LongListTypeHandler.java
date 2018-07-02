package net.guerlab.spring.mybatis.typehandlers;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;

import net.guerlab.spring.mybatis.BaseJsonTypeHandler;

/**
 * Long集合处理
 *
 * @author guer
 *
 */
public class LongListTypeHandler extends BaseJsonTypeHandler<List<Long>> {

    public LongListTypeHandler() {
        super(new TypeReference<List<Long>>() {
        });
    }

    @Override
    protected List<Long> getDefault() {
        return new ArrayList<>();
    }
}
