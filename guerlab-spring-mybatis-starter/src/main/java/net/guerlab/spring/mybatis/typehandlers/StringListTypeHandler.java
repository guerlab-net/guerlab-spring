package net.guerlab.spring.mybatis.typehandlers;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.guerlab.commons.exception.ApplicationException;
import net.guerlab.spring.commons.util.SpringApplicationContextUtil;

/**
 * String集合处理
 * 
 * @author guer
 *
 */
public class StringListTypeHandler extends BaseTypeHandler<List<String>> {

    private static final TypeReference<List<String>> TYPE_REFERENCE = new TypeReference<List<String>>() {
    };

    @Override
    public void setNonNullParameter(
            PreparedStatement ps,
            int i,
            List<String> parameter,
            JdbcType jdbcType) throws SQLException {
        ps.setString(i, toJson(parameter));
    }

    @Override
    public List<String> getNullableResult(
            ResultSet rs,
            String columnName) throws SQLException {
        return toObject(rs.getString(columnName));
    }

    @Override
    public List<String> getNullableResult(
            ResultSet rs,
            int columnIndex) throws SQLException {
        return toObject(rs.getString(columnIndex));
    }

    @Override
    public List<String> getNullableResult(
            CallableStatement cs,
            int columnIndex) throws SQLException {
        return toObject(cs.getString(columnIndex));
    }

    private static ObjectMapper getMapper() {
        return SpringApplicationContextUtil.getContext().getBean(ObjectMapper.class);
    }

    private String toJson(
            List<String> parameter) {
        try {
            return getMapper().writeValueAsString(parameter);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    private List<String> toObject(
            String content) {
        if (StringUtils.isBlank(content)) {
            return new ArrayList<>();
        }

        try {
            return getMapper().readValue(content, TYPE_REFERENCE);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
