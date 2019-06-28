package net.guerlab.spring.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.guerlab.commons.exception.ApplicationException;
import net.guerlab.spring.commons.util.SpringApplicationContextUtil;

/**
 * json处理
 *
 * @author guer
 *
 */
public class BaseJsonTypeHandler<T> extends BaseTypeHandler<T> {

    private Class<T> clazz;

    private TypeReference<T> typeReference;

    public BaseJsonTypeHandler(Class<T> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.clazz = clazz;
    }

    public BaseJsonTypeHandler(TypeReference<T> typeReference) {
        if (typeReference == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.typeReference = typeReference;
    }

    /**
     * 获取默认值, 默认返回null
     *
     * @return 默认值
     */
    protected T getDefault() {
        return null;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, toJson(parameter));
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return toObject(rs.getString(columnName));
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return toObject(rs.getString(columnIndex));
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return toObject(cs.getString(columnIndex));
    }

    private static ObjectMapper getMapper() {
        return SpringApplicationContextUtil.getContext().getBean(ObjectMapper.class);
    }

    private String toJson(T parameter) {
        try {
            return getMapper().writeValueAsString(parameter);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    private T toObject(String content) {
        if (StringUtils.isBlank(content)) {
            return getDefault();
        }

        try {
            if (clazz != null) {
                return getMapper().readValue(content, clazz);
            } else {
                return getMapper().readValue(content, typeReference);
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
