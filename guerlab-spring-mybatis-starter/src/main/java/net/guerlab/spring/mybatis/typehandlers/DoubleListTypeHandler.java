package net.guerlab.spring.mybatis.typehandlers;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;

import net.guerlab.spring.mybatis.BaseJsonTypeHandler;

/**
 * Double集合处理
 *
 * @author guer
 *
 */
public class DoubleListTypeHandler extends BaseJsonTypeHandler<List<Double>> {

    public DoubleListTypeHandler() {
        super(new TypeReference<List<Double>>() {
        });
    }

    @Override
    protected List<Double> getDefault() {
        return new ArrayList<>();
    }
}
