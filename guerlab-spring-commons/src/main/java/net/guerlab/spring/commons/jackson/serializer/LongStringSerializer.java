package net.guerlab.spring.commons.jackson.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Long类型字符串序列化
 *
 * @author guer
 *
 */
public class LongStringSerializer extends JsonSerializer<Long> {

    public static final LongStringSerializer INSTANCE = new LongStringSerializer();

    @Override
    public void serialize(
            Long value,
            JsonGenerator gen,
            SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
            return;
        }

        gen.writeString(String.valueOf(value));

    }
}