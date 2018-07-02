package net.guerlab.spring.mybatis.typehandlers;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.core.type.TypeReference;

import net.guerlab.spring.mybatis.BaseJsonTypeHandler;

/**
 * BigDecimal集合处理
 *
 * @author guer
 *
 */
public class BigDecimalSetTypeHandler extends BaseJsonTypeHandler<Set<BigDecimal>> {

    public BigDecimalSetTypeHandler() {
        super(new TypeReference<Set<BigDecimal>>() {
        });
    }

    @Override
    protected Set<BigDecimal> getDefault() {
        return new HashSet<>();
    }
}
