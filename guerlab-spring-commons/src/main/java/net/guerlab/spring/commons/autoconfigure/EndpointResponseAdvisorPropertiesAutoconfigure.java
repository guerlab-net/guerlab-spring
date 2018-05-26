package net.guerlab.spring.commons.autoconfigure;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

import net.guerlab.spring.commons.properties.ResponseAdvisorProperties;

/**
 * @author guer
 *
 */
@Configuration
@ConditionalOnClass(WebEndpointProperties.class)
public class EndpointResponseAdvisorPropertiesAutoconfigure {

    @Autowired
    public void advisor(WebEndpointProperties webEndpointProperties,
            ResponseAdvisorProperties responseAdvisorProperties) {
        String basePath = webEndpointProperties.getBasePath();

        if (StringUtils.isBlank(basePath)) {
            return;
        }

        List<String> excluded = responseAdvisorProperties.getExcluded();

        List<String> list = excluded == null ? new ArrayList<>() : new ArrayList<>(excluded);
        list.add(basePath);

        responseAdvisorProperties.setExcluded(list);
    }
}
