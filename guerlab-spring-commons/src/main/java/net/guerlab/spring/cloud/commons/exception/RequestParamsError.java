package net.guerlab.spring.cloud.commons.exception;

import javax.validation.ConstraintViolationException;

import org.springframework.web.bind.MethodArgumentNotValidException;

import net.guerlab.commons.exception.ApplicationException;

/**
 * @author guer
 *
 */
public class RequestParamsError extends ApplicationException {

    private static final long serialVersionUID = 1L;

    public RequestParamsError(String message, ConstraintViolationException cause) {
        super(message, cause, 400);
    }

    public RequestParamsError(String message, MethodArgumentNotValidException cause) {
        super(message, cause, 400);
    }
}