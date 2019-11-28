package net.guerlab.spring.commons.exception.handler;

import net.guerlab.commons.exception.ApplicationException;
import net.guerlab.spring.commons.exception.*;
import net.guerlab.web.result.Fail;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Collection;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * 异常统一处理配置
 *
 * @author guer
 *
 */
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    protected MessageSource messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * MethodArgumentTypeMismatchException异常处理
     *
     * @param request
     *            请求
     * @param e
     *            异常
     * @return 响应数据
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Fail<Void> methodArgumentTypeMismatchException(HttpServletRequest request,
            MethodArgumentTypeMismatchException e) {
        debug(request, e);
        return handler0(new MethodArgumentTypeMismatchExceptionInfo(e), e);
    }

    /**
     * NoHandlerFoundException异常处理
     *
     * @param request
     *            请求
     * @param e
     *            异常
     * @return 响应数据
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public Fail<Void> noHandlerFoundException(HttpServletRequest request,
            NoHandlerFoundException e) {
        debug(request, e);
        return handler0(new NoHandlerFoundExceptionInfo(e), e);
    }

    /**
     * HttpRequestMethodNotSupportedException异常处理
     *
     * @param request
     *            请求
     * @param e
     *            异常
     * @return 响应数据
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Fail<Void> httpRequestMethodNotSupportedException(HttpServletRequest request,
            HttpRequestMethodNotSupportedException e) {
        debug(request, e);
        return handler0(new HttpRequestMethodNotSupportedExceptionInfo(e), e);
    }

    /**
     * MissingServletRequestParameterException异常处理
     *
     * @param request
     *            请求
     * @param e
     *            异常
     * @return 响应数据
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Fail<Void> missingServletRequestParameterException(HttpServletRequest request,
            MissingServletRequestParameterException e) {
        debug(request, e);
        return handler0(new MissingServletRequestParameterExceptionInfo(e), e);
    }

    /**
     * MethodArgumentNotValidException异常处理
     *
     * @param request
     *            请求
     * @param e
     *            异常
     * @return 响应数据
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Fail<Collection<String>> methodArgumentNotValidException(HttpServletRequest request,
            MethodArgumentNotValidException e) {
        debug(request, e);

        BindingResult bindingResult = e.getBindingResult();

        Collection<String> displayMessageList = bindingResult.getAllErrors().stream()
                .map(this::getMethodArgumentNotValidExceptionDisplayMessage).collect(Collectors.toList());

        return handler0(new RequestParamsError(e, displayMessageList));
    }

    private String getMethodArgumentNotValidExceptionDisplayMessage(ObjectError error) {
        String defaultMessage = error.getDefaultMessage();

        Locale locale = LocaleContextHolder.getLocale();

        try {
            return messageSource.getMessage(defaultMessage, null, locale);
        } catch (NoSuchMessageException e) {
            return error.getObjectName() + error.getDefaultMessage();
        }
    }

    /**
     * ConstraintViolationException异常处理
     *
     * @param request
     *            请求
     * @param e
     *            异常
     * @return 响应数据
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Fail<Collection<String>> constraintViolationException(HttpServletRequest request,
            ConstraintViolationException e) {
        debug(request, e);

        Collection<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();

        Collection<String> displayMessageList = constraintViolations.stream().map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        return handler0(new RequestParamsError(e, displayMessageList));
    }

    /**
     * AbstractI18nApplicationException异常处理
     *
     * @param request
     *            请求\
     * @param e
     *            异常
     * @return 响应数据
     */
    @ExceptionHandler(AbstractI18nApplicationException.class)
    public Fail<Void> abstractI18nApplicationException(HttpServletRequest request,
            AbstractI18nApplicationException e) {
        debug(request, e);
        String message = e.getMessage(messageSource);
        return new Fail<>(message, e.getErrorCode());
    }

    /**
     * ApplicationException异常处理
     *
     * @param request
     *            请求
     * @param e
     *            异常
     * @return 响应数据
     */
    @ExceptionHandler(ApplicationException.class)
    public Fail<Void> applicationException(HttpServletRequest request,
            ApplicationException e) {
        debug(request, e);
        String message = getMessage(e.getLocalizedMessage());
        return new Fail<>(message, e.getErrorCode());
    }

    /**
     * 通用异常处理
     *
     * @param request
     *            请求
     * @param e
     *            异常
     * @return 响应数据
     */
    @ExceptionHandler(Exception.class)
    public Fail<Void> exception(HttpServletRequest request, Exception e) {
        debug(request, e);

        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class);

        if (responseStatus != null) {
            int errorCode = responseStatus.value().value();
            String message = responseStatus.reason();

            return new Fail<>(getMessage(message), errorCode);
        } else if (StringUtils.isBlank(e.getMessage())) {
            return handler0(new DefaultExceptionInfo(e), e);
        } else {
            return new Fail<>(getMessage(e.getLocalizedMessage()));
        }
    }

    private void debug(HttpServletRequest request, Throwable e) {
        LOGGER.debug(String.format("request uri[%s %s]", request.getMethod(), request.getRequestURI()), e);
    }

    private Fail<Collection<String>> handler0(RequestParamsError e) {
        String message = getMessage(e.getLocalizedMessage());
        Fail<Collection<String>> fail = new Fail<>(message, e.getErrorCode());
        fail.setData(e.getErrors());
        return fail;
    }

    private Fail<Void> handler0(AbstractI18nInfo i18nInfo, Throwable e) {
        String message = i18nInfo.getMessage(messageSource);
        return new Fail<>(message, i18nInfo.getErrorCode());
    }

    private String getMessage(String msg) {
        if (StringUtils.isBlank(msg)) {
            return msg;
        }

        Locale locale = LocaleContextHolder.getLocale();

        return messageSource.getMessage(msg, null, msg, locale);
    }

}
