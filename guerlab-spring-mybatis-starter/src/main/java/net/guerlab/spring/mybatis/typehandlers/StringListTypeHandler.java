package net.guerlab.spring.mybatis.typehandlers;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;

import net.guerlab.spring.mybatis.BaseJsonTypeHandler;

/**
 * String集合处理
 *
 * @author guer
 *
 */
public class StringListTypeHandler extends BaseJsonTypeHandler<List<String>> {

    public StringListTypeHandler() {
        super(new TypeReference<List<String>>() {
        });
    }

    @Override
    protected List<String> getDefault() {
        return new ArrayList<>();
    }
}
