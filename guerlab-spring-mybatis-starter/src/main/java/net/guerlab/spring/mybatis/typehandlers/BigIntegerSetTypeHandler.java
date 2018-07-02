package net.guerlab.spring.mybatis.typehandlers;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.core.type.TypeReference;

import net.guerlab.spring.mybatis.BaseJsonTypeHandler;

/**
 * BigInteger集合处理
 *
 * @author guer
 *
 */
public class BigIntegerSetTypeHandler extends BaseJsonTypeHandler<Set<BigInteger>> {

    public BigIntegerSetTypeHandler() {
        super(new TypeReference<Set<BigInteger>>() {
        });
    }

    @Override
    protected Set<BigInteger> getDefault() {
        return new HashSet<>();
    }
}
