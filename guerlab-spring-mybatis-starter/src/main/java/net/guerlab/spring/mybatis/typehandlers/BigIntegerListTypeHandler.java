package net.guerlab.spring.mybatis.typehandlers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;

import net.guerlab.spring.mybatis.BaseJsonTypeHandler;

/**
 * BigInteger集合处理
 *
 * @author guer
 *
 */
public class BigIntegerListTypeHandler extends BaseJsonTypeHandler<List<BigInteger>> {

    public BigIntegerListTypeHandler() {
        super(new TypeReference<List<BigInteger>>() {
        });
    }

    @Override
    protected List<BigInteger> getDefault() {
        return new ArrayList<>();
    }
}
