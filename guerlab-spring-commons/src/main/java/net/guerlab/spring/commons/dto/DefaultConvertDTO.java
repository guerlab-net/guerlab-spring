package net.guerlab.spring.commons.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.guerlab.commons.exception.ApplicationException;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 转换为DTO对象
 *
 * @author guer
 *
 * @param <D>
 *            DTO对象类型
 */
public interface DefaultConvertDTO<D> extends ConvertDTO<D> {

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

            if (DefaultConvertDTO.class.equals(rawType)) {
                clazz = (Class<D>) parameterizedType.getActualTypeArguments()[0];
                break;
            }
        }

        if (clazz == null) {
            return null;
        }

        D dto;

        try {
            dto = clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new ApplicationException(e.getLocalizedMessage(), e);
        }

        BeanUtils.copyProperties(this, dto);

        return dto;

    }
}
