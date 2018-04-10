package net.guerlab.spring.swagger2;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;

import com.google.common.base.Strings;

import io.swagger.models.Swagger;
import net.guerlab.commons.exception.ApplicationException;
import net.guerlab.spring.cloud.commons.annotation.IgnoreResponseHandler;
import springfox.documentation.service.Documentation;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;

/**
 * swagger2 controller with spring cloud
 *
 * @author guer
 *
 */
@ConditionalOnClass(EnableSwagger2.class)
public class CloudSwagger2AutoConfigure {

    /**
     * 基础路径
     */
    public static final String BASE_PATH = "/swagger-cloud";

    @IgnoreResponseHandler
    @RestController
    @RequestMapping(CloudSwagger2AutoConfigure.BASE_PATH)
    public static class CloudSwagger2Controller {

        @Autowired
        private DocumentationCache documentationCache;

        @Autowired
        private ServiceModelToSwagger2Mapper mapper;

        @GetMapping("/v2/api-docs")
        public Swagger getDocumentation(
                @RequestParam(value = "group", required = false) String swaggerGroup,
                HttpServletRequest servletRequest) {
            String groupName = Optional.ofNullable(swaggerGroup).orElse(Docket.DEFAULT_GROUP_NAME);

            Documentation documentation = documentationCache.documentationByGroup(groupName);

            if (documentation == null) {
                throw new ApplicationException("not find group[group=" + groupName + "]");
            }

            Swagger swagger = mapper.mapDocumentation(documentation);
            Swagger swagger2 = new Swagger();

            BeanUtils.copyProperties(swagger, swagger2);

            UriComponents uriComponents = HostNameProvider.componentsFrom(servletRequest, swagger2.getBasePath());

            String forwardedHost = servletRequest.getHeader("x-forwarded-host");
            String forwardedProto = servletRequest.getHeader("x-forwarded-proto");
            String forwardedPrefix = servletRequest.getHeader("x-forwarded-prefix");

            if (StringUtils.isNoneBlank(forwardedHost, forwardedProto, forwardedPrefix)) {
                swagger2.host(forwardedHost);
                swagger2.setBasePath(forwardedPrefix);
            } else {
                swagger2.basePath(Strings.isNullOrEmpty(uriComponents.getPath()) ? "/" : uriComponents.getPath());

                if (isNullOrEmpty(swagger2.getHost())) {
                    swagger2.host(hostName(uriComponents));
                }
            }

            return swagger2;
        }

        private String hostName(
                UriComponents uriComponents) {
            String host = uriComponents.getHost();
            int port = uriComponents.getPort();
            if (port > -1) {
                return String.format("%s:%d", host, port);
            }
            return host;
        }
    }
}
