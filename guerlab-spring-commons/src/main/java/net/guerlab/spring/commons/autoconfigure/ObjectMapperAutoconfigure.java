/*
 * Copyright 2018-2021 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.guerlab.spring.commons.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import net.guerlab.commons.collection.CollectionUtil;
import net.guerlab.commons.time.jackson.deserializer.*;
import net.guerlab.commons.time.jackson.serializer.*;
import net.guerlab.spring.commons.properties.NumberJsonStringFormatProperties;
import net.guerlab.spring.commons.util.SpringApplicationContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;
import java.util.Date;
import java.util.Objects;

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
    public static void setProperties(final ObjectMapper objectMapper, NumberJsonStringFormatProperties numberJsonStringFormatProperties) {

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
    }

    private static void moduleAdvice(SimpleModule module, NumberJsonStringFormatProperties properties) {
        if (properties == null) {
            return;
        }

        ToStringSerializer serializer = ToStringSerializer.instance;

        if (properties.isFormatAllNumber()) {
            module.addSerializer(Number.class, serializer);
        }
        if (properties.isFormatBigDecimal()) {
            module.addSerializer(BigDecimal.class, serializer);
        }
        if (properties.isFormatBigInteger()) {
            module.addSerializer(BigInteger.class, serializer);
        }
        if (properties.isFormatByteClass()) {
            module.addSerializer(Byte.class, serializer);
        }
        if (properties.isFormatByteType()) {
            module.addSerializer(Byte.TYPE, serializer);
        }
        if (properties.isFormatDoubleClass()) {
            module.addSerializer(Double.class, serializer);
        }
        if (properties.isFormatDoubleType()) {
            module.addSerializer(Double.TYPE, serializer);
        }
        if (properties.isFormatFloatClass()) {
            module.addSerializer(Float.class, serializer);
        }
        if (properties.isFormatFloatType()) {
            module.addSerializer(Float.TYPE, serializer);
        }
        if (properties.isFormatIntegerClass()) {
            module.addSerializer(Integer.class, serializer);
        }
        if (properties.isFormatIntegerType()) {
            module.addSerializer(Integer.TYPE, serializer);
        }
        if (properties.isFormatLongClass()) {
            module.addSerializer(Long.class, serializer);
        }
        if (properties.isFormatLongType()) {
            module.addSerializer(Long.TYPE, serializer);
        }
        if (properties.isFormatShortClass()) {
            module.addSerializer(Short.class, serializer);
        }
        if (properties.isFormatShortType()) {
            module.addSerializer(Short.TYPE, serializer);
        }

        if (CollectionUtil.isNotEmpty(properties.getFormatNumberClassList())) {
            properties.getFormatNumberClassList().stream().filter(Objects::nonNull).forEach(clazz -> module.addSerializer(clazz, serializer));
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
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    public void objectMapperAdvice(ObjectMapper objectMapper, NumberJsonStringFormatProperties numberJsonStringFormatProperties) {
        setProperties(objectMapper, numberJsonStringFormatProperties);
    }
}
