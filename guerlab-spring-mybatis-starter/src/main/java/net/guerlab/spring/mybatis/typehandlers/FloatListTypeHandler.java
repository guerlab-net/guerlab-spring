package net.guerlab.spring.mybatis.typehandlers;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;

import net.guerlab.spring.mybatis.BaseJsonTypeHandler;

/**
 * Float集合处理
 *
 * @author guer
 *
 */
public class FloatListTypeHandler extends BaseJsonTypeHandler<List<Float>> {

    public FloatListTypeHandler() {
        super(new TypeReference<List<Float>>() {
        });
    }

    @Override
    protected List<Float> getDefault() {
        return new ArrayList<>();
    }
}
