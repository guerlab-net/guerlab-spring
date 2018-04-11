package net.guerlab.spring.commons.properties;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * http响应数据处理配置参数<br>
 * 符合以下条件之一将不会被包装成{@link net.guerlab.web.result.Result}类型<br>
 * <ul>
 * <li>请求路径符合排除列表前缀</li>
 * <li>使用了{@link net.guerlab.spring.commons.annotation.IgnoreResponseHandler}注解</li>
 * </ul>
 *
 * @author guer
 *
 */
@Component
@RefreshScope
@ConfigurationProperties("spring.response-advisor")
public class ResponseAdvisorProperties {

    /**
     * 默认排除路径
     */
    public static final List<String> DEFAULT_EXCLUDED = Arrays.asList("/swagger", "/v2", "/health", "/info", "/bus",
            "/service-registry");

    /**
     * 排除路径
     */
    private List<String> excluded = DEFAULT_EXCLUDED;

    /**
     * 返回排除路径
     *
     * @return 排除路径
     */
    public List<String> getExcluded() {
        return excluded;
    }

    /**
     * 设置排除路径
     *
     * @param excluded
     *            排除路径
     */
    public void setExcluded(
            List<String> excluded) {
        this.excluded = excluded;
    }
}
