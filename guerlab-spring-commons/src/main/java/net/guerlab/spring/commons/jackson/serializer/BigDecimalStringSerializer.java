package net.guerlab.spring.commons.jackson.serializer;

import java.io.IOException;
import java.math.BigDecimal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * BigDecimal类型字符串序列化
 *
 * @author guer
 *
 */
public class BigDecimalStringSerializer extends JsonSerializer<BigDecimal> {

    public static final BigDecimalStringSerializer INSTANCE = new BigDecimalStringSerializer();

    @Override
    public void serialize(
            BigDecimal value,
            JsonGenerator gen,
            SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
            return;
        }

        gen.writeString(value.toString());
    }
}