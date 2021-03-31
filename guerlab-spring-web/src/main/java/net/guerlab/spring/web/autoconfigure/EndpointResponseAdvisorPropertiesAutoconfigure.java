package net.guerlab.spring.web.autoconfigure;

import net.guerlab.spring.web.properties.ResponseAdvisorProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * endpoint's response advisor ignore configure
 *
 * @author guer
 */
@Configuration
@EnableConfigurationProperties(ResponseAdvisorProperties.class)
@ConditionalOnBean({ WebEndpointProperties.class })
public class EndpointResponseAdvisorPropertiesAutoconfigure {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    public void advisor(WebEndpointProperties webEndpointProperties,
            ResponseAdvisorProperties responseAdvisorProperties) {
        String basePath = webEndpointProperties.getBasePath();

        if (StringUtils.isBlank(basePath)) {
            return;
        }

        List<String> excluded = responseAdvisorProperties.getExcluded();

        List<String> list = excluded == null ? new ArrayList<>() : excluded;
        list.add(basePath);

        responseAdvisorProperties.setExcluded(list);
    }
}
