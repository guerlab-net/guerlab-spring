package net.guerlab.spring.mybatis.typehandlers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;

import net.guerlab.spring.mybatis.BaseJsonTypeHandler;

/**
 * BigDecimal集合处理
 *
 * @author guer
 *
 */
public class BigDecimalListTypeHandler extends BaseJsonTypeHandler<List<BigDecimal>> {

    public BigDecimalListTypeHandler() {
        super(new TypeReference<List<BigDecimal>>() {
        });
    }

    @Override
    protected List<BigDecimal> getDefault() {
        return new ArrayList<>();
    }
}
