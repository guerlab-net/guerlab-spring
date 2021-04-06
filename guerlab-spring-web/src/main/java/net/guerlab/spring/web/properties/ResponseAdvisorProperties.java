/*
 * Copyright 2018-2021 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.guerlab.spring.web.properties;

import net.guerlab.spring.web.annotation.IgnoreResponseHandler;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * http响应数据处理配置参数<br>
 * 符合以下条件之一将不会被包装成{@link net.guerlab.web.result.Result}类型<br>
 * <ul>
 * <li>请求路径符合排除列表前缀</li>
 * <li>使用了{@link IgnoreResponseHandler}注解</li>
 * </ul>
 *
 * @author guer
 */
@RefreshScope
@ConfigurationProperties("spring.response-advisor")
public class ResponseAdvisorProperties {

    /**
     * 排除路径
     */
    private List<String> excluded = new ArrayList<>();

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
    public void setExcluded(List<String> excluded) {
        if (excluded != null) {
            this.excluded = excluded;
        }
    }

    /**
     * 添加排除路径
     *
     * @param excluded
     *            排除路径
     */
    public void addExcluded(List<String> excluded) {
        this.excluded.addAll(excluded);
    }

    /**
     * 添加排除路径
     *
     * @param excluded
     *            排除路径
     */
    public void addExcluded(String... excluded) {
        this.excluded.addAll(Arrays.asList(excluded));
    }
}
