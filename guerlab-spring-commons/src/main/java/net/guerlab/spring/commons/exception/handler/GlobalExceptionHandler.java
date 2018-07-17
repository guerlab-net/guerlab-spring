package net.guerlab.spring.commons.exception.handler;

import java.util.List;
import java.util.Locale;
import java.util.Set;
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
    public Fail<Void> noHandlerFoundException(HttpServletRequest request, HttpServletResponse response,
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
    public Fail<Void> httpRequestMethodNotSupportedException(HttpServletRequest request, HttpServletResponse response,
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
    public Fail<Void> missingServletRequestParameterException(HttpServletRequest request, HttpServletResponse response,
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
    public Fail<List<String>> methodArgumentNotValidException(HttpServletRequest request, HttpServletResponse response,
            MethodArgumentNotValidException e) {
        beforeLog(request, e);

        BindingResult bindingResult = e.getBindingResult();

        List<String> displayMessageList = bindingResult.getAllErrors().stream()
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
    public Fail<List<String>> constraintViolationException(HttpServletRequest request, HttpServletResponse response,
            ConstraintViolationException e) {
        beforeLog(request, e);

        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();

        List<String> displayMessageList = constraintViolations.stream().map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        return handler0(new RequestParamsError(e, displayMessageList));
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
        beforeLog(request, e);

        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class);

        if (responseStatus != null) {
            int errorCode = responseStatus.value().value();
            String message = responseStatus.reason();

            return new Fail<>(getMessage(message), errorCode);
        } else if (e instanceof AbstractI18nApplicationException) {
            return handler0((AbstractI18nApplicationException) e);
        } else if (e instanceof ApplicationException) {
            return handler0((ApplicationException) e);
        } else if (StringUtils.isBlank(e.getMessage())) {
            return handler0(new DefaultEexceptionInfo(e));
        } else {
            return handler0(e);
        }
    }

    protected final void beforeLog(HttpServletRequest request, Throwable e) {
        LOGGER.debug("request uri[{} {}]", request.getMethod(), request.getRequestURI());
        LOGGER.debug(e.getLocalizedMessage(), e);
    }

    protected final Fail<Void> handler0(Throwable ex) {
        return new Fail<>(getMessage(ex.getLocalizedMessage()));
    }

    protected final Fail<List<String>> handler0(RequestParamsError ex) {
        Fail<List<String>> fail = new Fail<>(getMessage(ex.getLocalizedMessage()), ex.getErrorCode());

        fail.setData(ex.getErrors());

        return fail;
    }

    protected final Fail<Void> handler0(AbstractI18nApplicationException ex) {
        return new Fail<>(ex.getMessage(messageSource), ex.getErrorCode());
    }

    protected final Fail<Void> handler0(ApplicationException ex) {
        return new Fail<>(getMessage(ex.getLocalizedMessage()), ex.getErrorCode());
    }

    protected final Fail<Void> handler0(AbstractI18nInfo i18nInfo) {
        return new Fail<>(i18nInfo.getMessage(messageSource), i18nInfo.getErrorCode());
    }

    protected final String getMessage(String msg) {
        if (StringUtils.isBlank(msg)) {
            return msg;
        }

        Locale locale = LocaleContextHolder.getLocale();

        return messageSource.getMessage(msg, null, msg, locale);
    }

}
