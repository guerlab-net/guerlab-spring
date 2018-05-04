package net.guerlab.spring.commons.exception.handler;

import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import net.guerlab.commons.exception.ApplicationException;
import net.guerlab.spring.commons.exception.AbstractI18nInfo;
import net.guerlab.spring.commons.exception.DefaultEexceptionInfo;
import net.guerlab.spring.commons.exception.HttpRequestMethodNotSupportedExceptionInfo;
import net.guerlab.spring.commons.exception.MethodArgumentTypeMismatchExceptionInfo;
import net.guerlab.spring.commons.exception.MissingServletRequestParameterExceptionInfo;
import net.guerlab.spring.commons.exception.NoHandlerFoundExceptionInfo;
import net.guerlab.spring.commons.exception.RequestParamsError;
import net.guerlab.web.result.Error;

/**
 * 异常统一处理配置
 *
 * @author guer
 *
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    private MessageSource messageSource;

    /**
     * MethodArgumentTypeMismatchException异常处理
     *
     * @param request
     *            请求
     * @param response
     *            响应
     * @param e
     *            异常
     * @return 响应数据
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Error<Void> methodArgumentTypeMismatchException(
            HttpServletRequest request,
            HttpServletResponse response,
            MethodArgumentTypeMismatchException e) {
        beforeLog(request, e);

        return handler0(new MethodArgumentTypeMismatchExceptionInfo(e));
    }

    /**
     * NoHandlerFoundException异常处理
     *
     * @param request
     *            请求
     * @param response
     *            响应
     * @param e
     *            异常
     * @return 响应数据
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public Error<Void> noHandlerFoundException(
            HttpServletRequest request,
            HttpServletResponse response,
            NoHandlerFoundException e) {
        beforeLog(request, e);

        return handler0(new NoHandlerFoundExceptionInfo(e));
    }

    /**
     * HttpRequestMethodNotSupportedException异常处理
     *
     * @param request
     *            请求
     * @param response
     *            响应
     * @param e
     *            异常
     * @return 响应数据
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Error<Void> httpRequestMethodNotSupportedException(
            HttpServletRequest request,
            HttpServletResponse response,
            HttpRequestMethodNotSupportedException e) {
        beforeLog(request, e);

        return handler0(new HttpRequestMethodNotSupportedExceptionInfo(e));
    }

    /**
     * MissingServletRequestParameterException异常处理
     *
     * @param request
     *            请求
     * @param response
     *            响应
     * @param e
     *            异常
     * @return 响应数据
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Error<Void> missingServletRequestParameterException(
            HttpServletRequest request,
            HttpServletResponse response,
            MissingServletRequestParameterException e) {
        beforeLog(request, e);

        return handler0(new MissingServletRequestParameterExceptionInfo(e));
    }

    /**
     * MethodArgumentNotValidException异常处理
     *
     * @param request
     *            请求
     * @param response
     *            响应
     * @param e
     *            异常
     * @return 响应数据
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Error<Void> methodArgumentNotValidException(
            HttpServletRequest request,
            HttpServletResponse response,
            MethodArgumentNotValidException e) {
        beforeLog(request, e);

        BindingResult bindingResult = e.getBindingResult();

        String message = StringUtils.joinWith("\n",
                bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).toArray());

        return handler0(new RequestParamsError(message, e));
    }

    /**
     * ConstraintViolationException异常处理
     *
     * @param request
     *            请求
     * @param response
     *            响应
     * @param e
     *            异常
     * @return 响应数据
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Error<Void> constraintViolationException(
            HttpServletRequest request,
            HttpServletResponse response,
            ConstraintViolationException e) {
        beforeLog(request, e);

        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();

        String message = StringUtils.joinWith("\n",
                constraintViolations.stream().map(ConstraintViolation::getMessage).toArray());

        return handler0(new RequestParamsError(message, e));
    }

    /**
     * 通用异常处理
     *
     * @param request
     *            请求
     * @param response
     *            响应
     * @param e
     *            异常
     * @return 响应数据
     */
    @ExceptionHandler(Exception.class)
    public Error<Void> exception(
            HttpServletRequest request,
            HttpServletResponse response,
            Exception e) {
        beforeLog(request, e);

        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class);

        if (responseStatus != null) {
            int errorCode = responseStatus.value().value();
            String message = responseStatus.reason();

            return new Error<>(getMessage(message), errorCode);
        } else if (e instanceof ApplicationException) {
            return handler0((ApplicationException) e);
        } else if (StringUtils.isBlank(e.getMessage())) {
            return handler0(new DefaultEexceptionInfo(e));
        } else {
            return handler0(e);
        }
    }

    private void beforeLog(
            HttpServletRequest request,
            Throwable e) {
        LOGGER.debug("request uri[{} {}]", request.getMethod(), request.getRequestURI());
        LOGGER.debug(e.getLocalizedMessage(), e);
    }

    private Error<Void> handler0(
            Throwable ex) {
        return new Error<>(getMessage(ex.getLocalizedMessage()));
    }

    private Error<Void> handler0(
            ApplicationException ex) {
        return new Error<>(getMessage(ex.getLocalizedMessage()), ex.getErrorCode());
    }

    private Error<Void> handler0(
            AbstractI18nInfo i18nInfo) {
        return new Error<>(i18nInfo.getMessage(messageSource), i18nInfo.getErrorCode());
    }

    private String getMessage(
            String msg) {
        if (StringUtils.isBlank(msg)) {
            return msg;
        }

        Locale locale = LocaleContextHolder.getLocale();

        return messageSource.getMessage(msg, null, msg, locale);
    }

}
