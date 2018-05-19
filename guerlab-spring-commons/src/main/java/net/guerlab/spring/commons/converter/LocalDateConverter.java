package net.guerlab.spring.commons.converter;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.core.convert.converter.Converter;

import net.guerlab.commons.time.TimeHelper;

/**
 * 日期转换
 *
 * @author guer
 *
 */
public class LocalDateConverter implements Converter<String, LocalDate> {

    @Override
    public LocalDate convert(String source) {
        LocalDateTime time = TimeHelper.parseLocalDateTime(source);

        return time == null ? null : time.toLocalDate();
    }

}