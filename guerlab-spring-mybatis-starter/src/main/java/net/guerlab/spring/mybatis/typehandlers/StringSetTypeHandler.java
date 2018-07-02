package net.guerlab.spring.mybatis.typehandlers;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.core.type.TypeReference;

import net.guerlab.spring.mybatis.BaseJsonTypeHandler;

/**
 * String集合处理
 *
 * @author guer
 *
 */
public class StringSetTypeHandler extends BaseJsonTypeHandler<Set<String>> {

    public StringSetTypeHandler() {
        super(new TypeReference<Set<String>>() {
        });
    }

    @Override
    protected Set<String> getDefault() {
        return new HashSet<>();
    }
}
