package net.guerlab.spring.commons.converter;

import java.time.LocalDateTime;

import org.springframework.core.convert.converter.Converter;

import net.guerlab.commons.time.TimeHelper;

/**
 * 日期时间转换
 *
 * @author guer
 *
 */
public class LocalDateTimeConverter implements Converter<String, LocalDateTime> {

    @Override
    public LocalDateTime convert(String source) {
        return TimeHelper.parseLocalDateTime(source);
    }

}