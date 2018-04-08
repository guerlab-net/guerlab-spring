package net.guerlab.spring.cloud.commons.exception;

import javax.validation.ConstraintViolationException;

import org.springframework.web.bind.MethodArgumentNotValidException;

import net.guerlab.commons.exception.ApplicationException;

/**
 * 请求参数错误
 *
 * @author guer
 *
 */
public class RequestParamsError extends ApplicationException {

    private static final long serialVersionUID = 1L;

    /**
     * 通过消息内容和ConstraintViolationException初始化
     *
     * @param message
     *            消息内容
     * @param cause
     *            ConstraintViolationException
     */
    public RequestParamsError(String message, ConstraintViolationException cause) {
        super(message, cause, 400);
    }

    /**
     * 通过消息内容和MethodArgumentNotValidException初始化
     *
     * @param message
     *            消息内容
     * @param cause
     *            MethodArgumentNotValidException
     */
    public RequestParamsError(String message, MethodArgumentNotValidException cause) {
        super(message, cause, 400);
    }
}