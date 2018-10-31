package net.guerlab.spring.commons.exception.handler;

import java.util.Collection;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import net.guerlab.commons.exception.ApplicationException;
import net.guerlab.spring.commons.exception.AbstractI18nApplicationException;
import net.guerlab.spring.commons.exception.AbstractI18nInfo;
import net.guerlab.spring.commons.exception.DefaultEexceptionInfo;
import net.guerlab.spring.commons.exception.HttpRequestMethodNotSupportedExceptionInfo;
import net.guerlab.spring.commons.exception.MethodArgumentTypeMismatchExceptionInfo;
import net.guerlab.spring.commons.exception.MissingServletRequestParameterExceptionInfo;
import net.guerlab.spring.commons.exception.NoHandlerFoundExceptionInfo;
import net.guerlab.spring.commons.exception.RequestParamsError;
import net.guerlab.web.result.Fail;

/**
 * 异常统一处理配置
 *
 * @author guer
 *
 */
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    protected MessageSource messageSource;

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
    public Fail<Void> methodArgumentTypeMismatchException(HttpServletRequest request, HttpServletResponse response,
            MethodArgumentTypeMismatchException e) {
        requestURILog(request);
        return handler0(new MethodArgumentTypeMismatchExceptionInfo(e), e);
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
    public Fail<Void> noHandlerFoundException(HttpServletRequest request, HttpServletResponse response,
            NoHandlerFoundException e) {
        requestURILog(request);
        return handler0(new NoHandlerFoundExceptionInfo(e), e);
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
    public Fail<Void> httpRequestMethodNotSupportedException(HttpServletRequest request, HttpServletResponse response,
            HttpRequestMethodNotSupportedException e) {
        requestURILog(request);
        return handler0(new HttpRequestMethodNotSupportedExceptionInfo(e), e);
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
    public Fail<Void> missingServletRequestParameterException(HttpServletRequest request, HttpServletResponse response,
            MissingServletRequestParameterException e) {
        requestURILog(request);
        return handler0(new MissingServletRequestParameterExceptionInfo(e), e);
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
    public Fail<Collection<String>> methodArgumentNotValidException(HttpServletRequest request,
            HttpServletResponse response, MethodArgumentNotValidException e) {
        requestURILog(request);

        BindingResult bindingResult = e.getBindingResult();

        Collection<String> displayMessageList = bindingResult.getAllErrors().stream()
                .map(this::getMethodArgumentNotValidExceptionDisplayMessage).collect(Collectors.toList());

        return handler0(new RequestParamsError(e, displayMessageList));
    }

    private String getMethodArgumentNotValidExceptionDisplayMessage(ObjectError error) {
        String defaultMessage = error.getDefaultMessage();

        Locale locale = LocaleContextHolder.getLocale();

        try {
            /*
             * use custom message
             */
            return messageSource.getMessage(defaultMessage, null, locale);
        } catch (NoSuchMessageException e) {
            /*
             * use ObjectError default message
             */
            return error.getObjectName() + error.getDefaultMessage();
        }
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
    public Fail<Collection<String>> constraintViolationException(HttpServletRequest request,
            HttpServletResponse response, ConstraintViolationException e) {
        requestURILog(request);

        Collection<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();

        Collection<String> displayMessageList = constraintViolations.stream().map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        return handler0(new RequestParamsError(e, displayMessageList));
    }

    /**
     * AbstractI18nApplicationException异常处理
     *
     * @param request
     *            请求
     * @param response
     *            响应
     * @param e
     *            异常
     * @return 响应数据
     */
    @ExceptionHandler(AbstractI18nApplicationException.class)
    public Fail<Void> abstractI18nApplicationException(HttpServletRequest request, HttpServletResponse response,
            AbstractI18nApplicationException e) {
        requestURILog(request);

        String message = e.getMessage(messageSource);

        LOGGER.debug(message, e);

        return new Fail<>(message, e.getErrorCode());
    }

    /**
     * ApplicationException异常处理
     *
     * @param request
     *            请求
     * @param response
     *            响应
     * @param e
     *            异常
     * @return 响应数据
     */
    @ExceptionHandler(ApplicationException.class)
    public Fail<Void> applicationException(HttpServletRequest request, HttpServletResponse response,
            ApplicationException e) {
        requestURILog(request);

        String message = getMessage(e.getLocalizedMessage());

        LOGGER.debug(message, e);

        return new Fail<>(message, e.getErrorCode());
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
    public Fail<Void> exception(HttpServletRequest request, HttpServletResponse response, Exception e) {
        e.printStackTrace();
        requestURILog(request);

        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class);

        if (responseStatus != null) {
            int errorCode = responseStatus.value().value();
            String message = responseStatus.reason();

            return new Fail<>(getMessage(message), errorCode);
        } else if (StringUtils.isBlank(e.getMessage())) {
            return handler0(new DefaultEexceptionInfo(e), e);
        } else {
            return new Fail<>(getMessage(e.getLocalizedMessage()));
        }
    }

    protected final void requestURILog(HttpServletRequest request) {
        LOGGER.debug("request uri[{} {}]", request.getMethod(), request.getRequestURI());
    }

    protected final Fail<Collection<String>> handler0(RequestParamsError e) {
        String message = getMessage(e.getLocalizedMessage());

        LOGGER.debug(message, e);

        Fail<Collection<String>> fail = new Fail<>(message, e.getErrorCode());

        fail.setData(e.getErrors());

        return fail;
    }

    protected final Fail<Void> handler0(AbstractI18nInfo i18nInfo, Throwable e) {
        String message = i18nInfo.getMessage(messageSource);

        LOGGER.debug(message, e);

        return new Fail<>(message, i18nInfo.getErrorCode());
    }

    protected final String getMessage(String msg) {
        if (StringUtils.isBlank(msg)) {
            return msg;
        }

        Locale locale = LocaleContextHolder.getLocale();

        return messageSource.getMessage(msg, null, msg, locale);
    }

}
