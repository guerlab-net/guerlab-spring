package net.guerlab.spring.cloud.commons.exception;

/**
 * @author guer
 *
 */
public class DefaultEexceptionInfo extends AbstractI18nInfo {

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