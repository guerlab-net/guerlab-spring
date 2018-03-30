package net.guerlab.spring.cloud.commons.converter;

import java.time.LocalDate;

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
    public LocalDate convert(
            String source) {
        return TimeHelper.parseLocalDateTime(source).toLocalDate();
    }

    @Override
    public String toString() {
        return "LocalDateConverter [form=java.lang.String, to=java.time.LocalDate]";
    }

}
