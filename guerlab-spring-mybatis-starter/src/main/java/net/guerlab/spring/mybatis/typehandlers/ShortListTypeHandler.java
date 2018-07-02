package net.guerlab.spring.mybatis.typehandlers;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;

import net.guerlab.spring.mybatis.BaseJsonTypeHandler;

/**
 * Short集合处理
 *
 * @author guer
 *
 */
public class ShortListTypeHandler extends BaseJsonTypeHandler<List<Short>> {

    public ShortListTypeHandler() {
        super(new TypeReference<List<Short>>() {
        });
    }

    @Override
    protected List<Short> getDefault() {
        return new ArrayList<>();
    }
}
