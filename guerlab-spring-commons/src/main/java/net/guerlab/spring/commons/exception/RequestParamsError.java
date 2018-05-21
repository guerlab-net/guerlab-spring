package net.guerlab.spring.commons.exception;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;

import net.guerlab.commons.collection.CollectionUtil;
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
     * 错误消息列表
     */
    private List<String> errors;

    /**
     * 通过消息内容和ConstraintViolationException初始化
     *
     * @param cause
     *            ConstraintViolationException
     * @param displayMessageList
     *            错误消息列表
     */
    public RequestParamsError(ConstraintViolationException cause, List<String> displayMessageList) {
        super(getMessage(displayMessageList), cause, 400);
        errors = displayMessageList;
    }

    /**
     * 通过消息内容和MethodArgumentNotValidException初始化
     *
     * @param cause
     *            MethodArgumentNotValidException
     * @param displayMessageList
     *            错误消息列表
     */
    public RequestParamsError(MethodArgumentNotValidException cause, List<String> displayMessageList) {
        super(getMessage(displayMessageList), cause, 400);
        errors = displayMessageList;
    }

    private static String getMessage(
            List<String> displayMessageList) {
        if (CollectionUtil.isEmpty(displayMessageList)) {
            return "";
        }
        return StringUtils.joinWith("\n", displayMessageList.toArray());
    }

    /**
     * 返回错误消息列表
     *
     * @return 错误消息列表
     */
    public List<String> getErrors() {
        return errors;
    }
}