package net.guerlab.spring.commons.exception;

/**
 * 异常信息KEY列表
 *
 * @author guer
 *
 */
public class Keys {

    /**
     * 默认错误
     */
    public static final String DEFAULT = "error.default";

    /**
     * 不支持的HTTP请求方式
     */
    public static final String HTTP_REQUEST_METHOD_NOT_SUPPORTED = "error.httpRequestMethodNotSupported";

    /**
     * 方法参数不匹配
     */
    public static final String METHOD_ARGUMENT_TYPE_MISMATCH = "error.methodArgumentTypeMismatch";

    /**
     * 方法参数不匹配
     */
    public static final String METHOD_ARGUMENT_TYPE_MISMATCH_WITHOUT_TYPE = "error.methodArgumentTypeMismatchWithoutType";

    /**
     * 缺失请求参数
     */
    public static final String MISSING_SERVLET_REQUEST_PARAMETER = "error.missingServletRequestParameter";

    /**
     * 未发现处理程序(404)
     */
    public static final String NO_HANDLER_FOUND = "error.noHandlerFound";

    private Keys() {
    }
}
