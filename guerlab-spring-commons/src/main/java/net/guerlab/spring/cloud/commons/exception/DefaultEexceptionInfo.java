package net.guerlab.spring.cloud.commons.exception;

/**
 * 默认错误
 *
 * @author guer
 *
 */
public class DefaultEexceptionInfo extends AbstractI18nInfo {

    /**
     * 通过异常信息初始化
     *
     * @param cause
     *            Throwable
     */
    public DefaultEexceptionInfo(Throwable cause) {
        super(cause, 500);
    }

    @Override
    protected String getKey() {
        return Keys.DEFAULT;
    }

    @Override
    protected Object[] getArgs() {
        return null;
    }

    @Override
    protected String getDefaultMessage() {
        return "系统繁忙,请稍后再试";
    }
}