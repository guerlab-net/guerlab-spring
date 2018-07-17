package net.guerlab.spring.commons.autoconfigure;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

import net.guerlab.commons.time.jackson.deserializer.DateDeserializer;
import net.guerlab.commons.time.jackson.deserializer.LocalDateDeserializer;
import net.guerlab.commons.time.jackson.deserializer.LocalDateTimeDeserializer;
import net.guerlab.commons.time.jackson.deserializer.LocalTimeDeserializer;
import net.guerlab.commons.time.jackson.deserializer.MonthDeserializer;
import net.guerlab.commons.time.jackson.deserializer.YearDeserializer;
import net.guerlab.commons.time.jackson.serializer.DateSerializer;
import net.guerlab.commons.time.jackson.serializer.LocalDateSerializer;
import net.guerlab.commons.time.jackson.serializer.LocalDateTimeSerializer;
import net.guerlab.commons.time.jackson.serializer.LocalTimeSerializer;
import net.guerlab.commons.time.jackson.serializer.MonthSerializer;
import net.guerlab.commons.time.jackson.serializer.YearSerializer;
import net.guerlab.spring.commons.jackson.serializer.NumberStringSerializer;

/**
 * ObjectMapper配置
 *
 * @author guer
 *
 */
@Configuration
public class ObjectMapperAutoconfigure {

    /**
     * 设置ObjectMapper属性
     *
     * @param objectMapper
     *            objectMapper
     */
    public static void setProperties(final ObjectMapper objectMapper) {
        NumberStringSerializer numberStringSerializer = new NumberStringSerializer();

        SimpleModule module = new SimpleModule();
        module.addDeserializer(Date.class, new DateDeserializer());
        module.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        module.addDeserializer(LocalTime.class, new LocalTimeDeserializer());
        module.addDeserializer(Month.class, new MonthDeserializer());
        module.addDeserializer(Year.class, new YearDeserializer());

        module.addSerializer(Date.class, new DateSerializer());
        module.addSerializer(LocalDate.class, new LocalDateSerializer());
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        module.addSerializer(LocalTime.class, new LocalTimeSerializer());
        module.addSerializer(Month.class, new MonthSerializer());
        module.addSerializer(Year.class, new YearSerializer());

        module.addSerializer(Long.class, numberStringSerializer);
        module.addSerializer(BigInteger.class, numberStringSerializer);
        module.addSerializer(BigDecimal.class, numberStringSerializer);

        objectMapper.findAndRegisterModules();
        objectMapper.registerModule(module);

        objectMapper.setSerializationInclusion(Include.NON_NULL);

        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        objectMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
        objectMapper.configure(SerializationFeature.WRITE_CHAR_ARRAYS_AS_JSON_ARRAYS, true);

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        objectMapper.configure(Feature.WRITE_BIGDECIMAL_AS_PLAIN, true);
    }

    /**
     * objectMapper扩展设置
     *
     * @param objectMapper
     *            objectMapper
     */
    @Autowired
    public void objectMapperAdvice(ObjectMapper objectMapper) {
        setProperties(objectMapper);
    }
}
