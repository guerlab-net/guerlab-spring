package net.guerlab.spring.swagger2.autoconfigure;

import io.swagger.models.Swagger;
import net.guerlab.spring.swagger2.HostNameProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UriComponents;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.service.Documentation;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * swagger2 controller with spring cloud
 *
 * @author guer
 *
 */
@Configuration
@Conditional(SwaggerEnableCondition.class)
public class Swagger2ControllerAutoConfigure implements WebMvcConfigurer, ApplicationContextAware {

    /**
     * 基础路径
     */
    static final String BASE_PATH = "/swagger-cloud";

    private String appName;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        Environment environment = context.getEnvironment();
        appName = environment
                .getProperty("eureka.instance.appname", environment.getProperty("spring.application.name"));
    }

    /**
     * swagger2 controller with spring cloud
     *
     * @author guer
     *
     */
    @ApiIgnore
    @RestController
    @RequestMapping(Swagger2ControllerAutoConfigure.BASE_PATH)
    public class Swagger2Controller {

        private DocumentationCache documentationCache;

        private ServiceModelToSwagger2Mapper mapper;

        @Autowired
        public void setDocumentationCache(DocumentationCache documentationCache) {
            this.documentationCache = documentationCache;
        }

        @Autowired
        public void setMapper(ServiceModelToSwagger2Mapper mapper) {
            this.mapper = mapper;
        }

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
                throw new RuntimeException("not find group[group=" + groupName + "]");
            }

            Swagger originSwagger = mapper.mapDocumentation(documentation);
            Swagger cloudSwagger = new Swagger();

            BeanUtils.copyProperties(originSwagger, cloudSwagger);

            UriComponents uriComponents = HostNameProvider.componentsFrom(servletRequest, cloudSwagger.getBasePath());

            String forwardedHost = servletRequest.getHeader("x-forwarded-host");

            if (StringUtils.isNotBlank(forwardedHost)) {
                cloudSwagger.host(forwardedHost);
                setBasePath(cloudSwagger, servletRequest.getHeader("x-forwarded-prefix"));
            } else {
                cloudSwagger.basePath(StringUtils.isBlank(uriComponents.getPath()) ? "/" : uriComponents.getPath());

                if (StringUtils.isBlank(cloudSwagger.getHost())) {
                    cloudSwagger.host(hostName(uriComponents));
                }
            }

            return cloudSwagger;
        }

        private void setBasePath(Swagger swagger2, String forwardedPrefix) {
            if (setBasePathSpringCloudZuulSupport(swagger2, forwardedPrefix)) {
                return;
            }

            setBasePathGeneralSupport(swagger2);
        }

        /**
         * spring cloud 1.X 下zull的支持,需要在zuul增加zuul.add-host-header=true配置
         */
        private boolean setBasePathSpringCloudZuulSupport(Swagger swagger2, String forwardedPrefix) {
            if (StringUtils.isNotBlank(forwardedPrefix)) {
                swagger2.setBasePath(forwardedPrefix);
                return true;
            }
            return false;
        }

        /**
         * 通用支持,用于支持在服务注册后根据注册名称自动做匹配,适用于1.X的zuul的自动发现和2.X的gateway的自动支持<br>
         * 2.X gateway的自动发现请参考<a href=
         * "http://cloud.spring.io/spring-cloud-static/Finchley.RC2/single/spring-cloud.html#_discoveryclient_route_definition_locator">http://cloud.spring.io/spring-cloud-static/Finchley.RC2/single/spring-cloud.html#_discoveryclient_route_definition_locator</a>
         */
        private void setBasePathGeneralSupport(Swagger swagger2) {
            if (StringUtils.isNotBlank(appName)) {
                swagger2.setBasePath("/" + appName.toUpperCase());
            }
        }

        private String hostName(UriComponents uriComponents) {
            String host = uriComponents.getHost();
            int port = uriComponents.getPort();
            return port > -1 ? String.format("%s:%d", host, port) : host;
        }
    }
}
