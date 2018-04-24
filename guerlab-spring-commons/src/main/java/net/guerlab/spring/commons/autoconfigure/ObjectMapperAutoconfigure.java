package net.guerlab.spring.commons.autoconfigure;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

import net.guerlab.commons.time.jackson.deserializer.LocalDateDeserializer;
import net.guerlab.commons.time.jackson.deserializer.LocalDateTimeDeserializer;
import net.guerlab.commons.time.jackson.deserializer.LocalTimeDeserializer;
import net.guerlab.commons.time.jackson.deserializer.MonthDeserializer;
import net.guerlab.commons.time.jackson.deserializer.YearDeserializer;
import net.guerlab.commons.time.jackson.serializer.LocalDateSerializer;
import net.guerlab.commons.time.jackson.serializer.LocalDateTimeSerializer;
import net.guerlab.commons.time.jackson.serializer.LocalTimeSerializer;
import net.guerlab.commons.time.jackson.serializer.MonthSerializer;
import net.guerlab.commons.time.jackson.serializer.YearSerializer;
import net.guerlab.spring.commons.jackson.serializer.BigDecimalStringSerializer;
import net.guerlab.spring.commons.jackson.serializer.BigIntegerStringSerializer;
import net.guerlab.spring.commons.jackson.serializer.LongStringSerializer;

/**
 * ObjectMapper配置
 *
 * @author guer
 *
 */
@Configuration
@AutoConfigureBefore(JacksonAutoConfiguration.class)
public class ObjectMapperAutoconfigure {

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        module.addDeserializer(LocalTime.class, new LocalTimeDeserializer());
        module.addDeserializer(Month.class, new MonthDeserializer());
        module.addDeserializer(Year.class, new YearDeserializer());

        module.addSerializer(LocalDate.class, new LocalDateSerializer());
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        module.addSerializer(LocalTime.class, new LocalTimeSerializer());
        module.addSerializer(Month.class, new MonthSerializer());
        module.addSerializer(Year.class, new YearSerializer());
        module.addSerializer(Long.class, new LongStringSerializer());
        module.addSerializer(BigInteger.class, new BigIntegerStringSerializer());
        module.addSerializer(BigDecimal.class, new BigDecimalStringSerializer());

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.registerModule(module);

        mapper.setSerializationInclusion(Include.NON_NULL);

        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
        mapper.configure(SerializationFeature.WRITE_CHAR_ARRAYS_AS_JSON_ARRAYS, true);

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        mapper.configure(Feature.WRITE_BIGDECIMAL_AS_PLAIN, true);

        return mapper;
    }
}
