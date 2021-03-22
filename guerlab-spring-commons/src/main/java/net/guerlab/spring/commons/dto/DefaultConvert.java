package net.guerlab.spring.commons.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.guerlab.commons.exception.ApplicationException;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 转换为对象
 *
 * @param <D>
 *         对象类型
 * @author guer
 */
public interface DefaultConvert<D> extends Convert<D> {

    /**
     * 转换
     *
     * @return 转换对象
     */
    @Override
    @JsonIgnore
    @SuppressWarnings("unchecked")
    default D convert() {
        Type[] types = getClass().getGenericInterfaces();

        Class<D> clazz = null;

        for (Type type : types) {
            if (!(type instanceof ParameterizedType)) {
                continue;
            }

            ParameterizedType parameterizedType = (ParameterizedType) type;

            Type rawType = parameterizedType.getRawType();

            if (DefaultConvert.class.equals(rawType)) {
                clazz = (Class<D>) parameterizedType.getActualTypeArguments()[0];
                break;
            }
        }

        if (clazz == null) {
            return null;
        }

        D target;

        try {
            target = clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new ApplicationException(e.getLocalizedMessage(), e);
        }

        BeanUtils.copyProperties(this, target);

        return target;
    }
}
