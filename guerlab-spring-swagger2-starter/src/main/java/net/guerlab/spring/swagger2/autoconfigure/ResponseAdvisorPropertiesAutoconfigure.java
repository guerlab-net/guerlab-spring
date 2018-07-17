package net.guerlab.spring.swagger2.autoconfigure;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

import net.guerlab.spring.commons.properties.ResponseAdvisorProperties;

/**
 * @author guer
 *
 */
@Configuration
@ConditionalOnClass(ResponseAdvisorProperties.class)
public class ResponseAdvisorPropertiesAutoconfigure {

    @Autowired
    public void advisor(ResponseAdvisorProperties responseAdvisorProperties) {
        List<String> list = new ArrayList<>();
        list.add(Swagger2ControllerAutoConfigure.BASE_PATH);
        list.add("/swagger");
        list.add("/v2");
        list.add("/webjars/springfox-swagger-ui");

        responseAdvisorProperties.addExcluded(list);
    }
}
