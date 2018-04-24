package net.guerlab.spring.commons.exception;

/**
 * 异常信息KEY列表
 *
 * @author guer
 *
 */
public interface Keys {

    /**
     * 默认错误
     */
    String DEFAULT = "error.default";

    /**
     * 不支持的HTTP请求方式
     */
    String HTTP_REQUEST_METHOD_NOT_SUPPORTED = "error.httpRequestMethodNotSupported";

    /**
     * 方法参数不匹配
     */
    String METHOD_ARGUMENT_TYPE_MISMATCH = "error.methodArgumentTypeMismatch";

    /**
     * 方法参数不匹配
     */
    String METHOD_ARGUMENT_TYPE_MISMATCH_WITHOUT_TYPE = "error.methodArgumentTypeMismatchWithoutType";

    /**
     * 缺失请求参数
     */
    String MISSING_SERVLET_REQUEST_PARAMETER = "error.missingServletRequestParameter";

    /**
     * 未发现处理程序(404)
     */
    String NO_HANDLER_FOUND = "error.noHandlerFound";
}
