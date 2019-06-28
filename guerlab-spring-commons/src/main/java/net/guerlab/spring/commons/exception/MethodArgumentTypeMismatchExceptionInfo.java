package net.guerlab.spring.commons.exception;

import org.springframework.util.ClassUtils;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * 方法参数不匹配
 *
 * @author guer
 *
 */
public class MethodArgumentTypeMismatchExceptionInfo extends AbstractI18nInfo {

    private final Object value;

    private final Class<?> requiredType;

    /**
     * 通过MethodArgumentTypeMismatchException初始化
     * 
     * @param cause
     *            MethodArgumentTypeMismatchException
     */
    public MethodArgumentTypeMismatchExceptionInfo(MethodArgumentTypeMismatchException cause) {
        super(cause, 403);
        value = cause.getValue();
        requiredType = cause.getRequiredType();
    }

    @Override
    protected String getKey() {
        return requiredType == null ? Keys.METHOD_ARGUMENT_TYPE_MISMATCH_WITHOUT_TYPE
                : Keys.METHOD_ARGUMENT_TYPE_MISMATCH;
    }

    @Override
    protected Object[] getArgs() {
        if (requiredType == null) {
            return new Object[] {
                    ClassUtils.getDescriptiveType(value),
            };
        } else {
            return new Object[] {
                    ClassUtils.getDescriptiveType(value), ClassUtils.getQualifiedName(requiredType)
            };
        }
    }

}
