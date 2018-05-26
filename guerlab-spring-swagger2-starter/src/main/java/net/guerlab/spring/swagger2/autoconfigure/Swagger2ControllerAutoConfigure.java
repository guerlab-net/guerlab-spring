package net.guerlab.spring.swagger2.autoconfigure;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UriComponents;

import io.swagger.models.Swagger;
import net.guerlab.commons.exception.ApplicationException;
import net.guerlab.spring.commons.annotation.IgnoreResponseHandler;
import net.guerlab.spring.swagger2.HostNameProvider;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.service.Documentation;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;

/**
 * swagger2 controller with spring cloud
 *
 * @author guer
 *
 */
@Configuration
@Conditional(SwaggerEnableCondition.class)
public class Swagger2ControllerAutoConfigure implements WebMvcConfigurer {

    /**
     * 基础路径
     */
    public static final String BASE_PATH = "/swagger-cloud";

    /**
     * swagger2 controller with spring cloud
     *
     * @author guer
     *
     */
    @ApiIgnore
    @IgnoreResponseHandler
    @RestController
    @RequestMapping(Swagger2ControllerAutoConfigure.BASE_PATH)
    public static class Swagger2Controller {

        @Autowired
        private DocumentationCache documentationCache;

        @Autowired
        private ServiceModelToSwagger2Mapper mapper;

        /**
         * get Documentation
         *
         * @param swaggerGroup
         *            swagger Group
         * @param servletRequest
         *            request
         * @return swagger
         */
        @GetMapping("/v2/api-docs")
        public Swagger getDocumentation(@RequestParam(value = "group", required = false) String swaggerGroup,
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
                swagger2.basePath(StringUtils.isBlank(uriComponents.getPath()) ? "/" : uriComponents.getPath());

                if (StringUtils.isBlank(swagger2.getHost())) {
                    swagger2.host(hostName(uriComponents));
                }
            }

            return swagger2;
        }

        private String hostName(UriComponents uriComponents) {
            String host = uriComponents.getHost();
            int port = uriComponents.getPort();
            if (port > -1) {
                return String.format("%s:%d", host, port);
            }
            return host;
        }
    }
}
