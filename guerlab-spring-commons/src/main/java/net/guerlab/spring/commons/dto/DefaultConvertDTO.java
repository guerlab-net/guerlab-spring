package net.guerlab.spring.commons.dto;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import net.guerlab.commons.exception.ApplicationException;

/**
 * 转换为DTO对象
 *
 * @author guer
 *
 * @param <D>
 *            DTO对象类型
 */
public interface DefaultConvertDTO<D> extends ConvertDTO<D> {

    Logger LOGGER = LoggerFactory.getLogger(DefaultConvertDTO.class);

    /**
     * 转换
     *
     * @return DTO对象
     */
    @Override
    @JsonIgnore
    @SuppressWarnings("unchecked")
    default D toDTO() {
        Type[] types = getClass().getGenericInterfaces();

        Class<D> clazz = null;

        for (Type type : types) {
            if (!(type instanceof ParameterizedType)) {
                continue;
            }

            ParameterizedType parameterizedType = (ParameterizedType) type;

            Type rawType = parameterizedType.getRawType();

            if (rawType == DefaultConvertDTO.class) {
                clazz = (Class<D>) parameterizedType.getActualTypeArguments()[0];
                break;
            }
        }

        D dto;
        
        try {
            dto = clazz.newInstance();
        } catch (Exception e) {
            LOGGER.error(e.getLocalizedMessage(), e);
            throw new ApplicationException(e.getLocalizedMessage(), e);
        }

        BeanUtils.copyProperties(this, dto);

        return dto;

    }
}
