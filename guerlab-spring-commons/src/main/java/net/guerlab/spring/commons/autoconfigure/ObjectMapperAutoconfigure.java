package net.guerlab.spring.commons.autoconfigure;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;
import java.util.Date;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

import net.guerlab.commons.collection.CollectionUtil;
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
import net.guerlab.spring.commons.properties.NumberJsonStringFormatProperties;
import net.guerlab.spring.commons.util.SpringApplicationContextUtil;

/**
 * ObjectMapper配置
 *
 * @author guer
 *
 */
@Configuration
@EnableConfigurationProperties(NumberJsonStringFormatProperties.class)
public class ObjectMapperAutoconfigure {

    /**
     * 设置ObjectMapper属性
     *
     * @param objectMapper
     *            objectMapper
     * @param numberJsonStringFormatProperties
     *            数值json格式化配置
     */
    public static void setProperties(final ObjectMapper objectMapper,
            NumberJsonStringFormatProperties numberJsonStringFormatProperties) {

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

        moduleAdvice(module, numberJsonStringFormatProperties);

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

    private static void moduleAdvice(SimpleModule module, NumberJsonStringFormatProperties properties) {
        if (properties == null) {
            return;
        }

        NumberStringSerializer numberStringSerializer = new NumberStringSerializer();

        if (properties.isFormatAllNumber()) {
            module.addSerializer(Number.class, numberStringSerializer);
        }
        if (properties.isFormatBigDecimal()) {
            module.addSerializer(BigDecimal.class, numberStringSerializer);
        }
        if (properties.isFormatBigInteger()) {
            module.addSerializer(BigInteger.class, numberStringSerializer);
        }
        if (properties.isFormatByte()) {
            module.addSerializer(Byte.class, numberStringSerializer);
            module.addSerializer(Byte.TYPE, numberStringSerializer);
        }
        if (properties.isFormatDouble()) {
            module.addSerializer(Double.class, numberStringSerializer);
            module.addSerializer(Double.TYPE, numberStringSerializer);
        }
        if (properties.isFormatFloat()) {
            module.addSerializer(Float.class, numberStringSerializer);
            module.addSerializer(Float.TYPE, numberStringSerializer);
        }
        if (properties.isFormatInteger()) {
            module.addSerializer(Integer.class, numberStringSerializer);
            module.addSerializer(Integer.TYPE, numberStringSerializer);
        }
        if (properties.isFormatLong()) {
            module.addSerializer(Long.class, numberStringSerializer);
            module.addSerializer(Long.TYPE, numberStringSerializer);
        }
        if (properties.isFormatShort()) {
            module.addSerializer(Short.class, numberStringSerializer);
            module.addSerializer(Short.TYPE, numberStringSerializer);
        }

        if (CollectionUtil.isNotEmpty(properties.getFormatNumberClassList())) {
            properties.getFormatNumberClassList().stream().filter(Objects::nonNull)
                    .forEach(clazz -> module.addSerializer(clazz, numberStringSerializer));
        }

    }

    /**
     * 设置ObjectMapper属性
     *
     * @param objectMapper
     *            objectMapper
     */
    public static void setProperties(final ObjectMapper objectMapper) {
        ApplicationContext context = SpringApplicationContextUtil.getContext();

        NumberJsonStringFormatProperties numberJsonStringFormatProperties = null;

        if (context != null) {
            numberJsonStringFormatProperties = context.getBean(NumberJsonStringFormatProperties.class);
        }

        setProperties(objectMapper, numberJsonStringFormatProperties);
    }

    /**
     * objectMapper扩展设置
     *
     * @param objectMapper
     *            objectMapper
     * @param numberJsonStringFormatProperties
     *            数值json格式化配置
     */
    @Autowired
    public void objectMapperAdvice(ObjectMapper objectMapper,
            NumberJsonStringFormatProperties numberJsonStringFormatProperties) {
        setProperties(objectMapper, numberJsonStringFormatProperties);
    }
}
