package net.guerlab.spring.swagger2;

import net.guerlab.spring.swagger2.properties.ApiInfoProperties;
import net.guerlab.spring.swagger2.properties.DocketProperties;
import net.guerlab.spring.swagger2.properties.ParameterProperties;
import org.apache.commons.lang3.StringUtils;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Docket构造工厂
 *
 * @author guerlab
 */
public class DocketFactory {

    public static Docket build(DocketProperties properties) {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);

        ApiInfoProperties apiInfo = properties.getApiInfo();

        if (apiInfo != null) {
            docket.apiInfo(buildApiInfo(apiInfo));
        }

        if (StringUtils.isNotBlank(properties.getGroupName())) {
            docket.groupName(properties.getGroupName());
        }

        docket.useDefaultResponseMessages(properties.isApplyDefaultResponseMessages());

        List<ParameterProperties> globalOperationParameters = properties.getGlobalOperationParameters();

        if (globalOperationParameters != null && !globalOperationParameters.isEmpty()) {
            List<Parameter> operationParameters = globalOperationParameters.stream().filter(Objects::nonNull)
                    .map(DocketFactory::createParameter).collect(Collectors.toList());

            if (!operationParameters.isEmpty()) {
                docket.globalOperationParameters(operationParameters);
            }
        }

        setApiSelectorBuilder(docket, properties);

        return docket;
    }

    private static void setApiSelectorBuilder(Docket docket, DocketProperties properties) {
        ApiSelectorBuilder apiSelectorBuilder = docket.select();

        if (StringUtils.isNotBlank(properties.getBasePackage())) {
            if (DocketProperties.ALL_PACKAGE.equals(properties.getBasePackage())) {
                apiSelectorBuilder.apis(RequestHandlerSelectors.any());
            } else {
                apiSelectorBuilder.apis(RequestHandlerSelectors.basePackage(properties.getBasePackage()));
            }
        }

        if (StringUtils.isNotBlank(properties.getPathRegex())) {
            apiSelectorBuilder.paths(PathSelectors.regex(properties.getPathRegex()));
        }
        if (StringUtils.isNotBlank(properties.getPathAntPattern())) {
            apiSelectorBuilder.paths(PathSelectors.ant(properties.getPathAntPattern()));
        }

        apiSelectorBuilder.build();
    }

    private static Parameter createParameter(ParameterProperties properties) {
        ParameterBuilder builder = new ParameterBuilder();

        builder.name(properties.getName());
        builder.description(properties.getDescription());
        builder.defaultValue(properties.getDefaultValue());
        builder.required(properties.isRequired());
        builder.allowMultiple(properties.isAllowMultiple());
        builder.parameterType(properties.getParamType());
        builder.modelRef(new ModelRef(properties.getModelRef()));
        builder.hidden(properties.isHidden());
        builder.pattern(properties.getPattern());
        builder.collectionFormat(properties.getCollectionFormat());

        return builder.build();
    }

    private static ApiInfo buildApiInfo(ApiInfoProperties apiInfo) {
        ApiInfoBuilder builder = new ApiInfoBuilder();

        builder.title(apiInfo.getTitle());
        builder.description(apiInfo.getDescription());
        builder.termsOfServiceUrl(apiInfo.getTermsOfServiceUrl());
        builder.contact(apiInfo.getContact());
        builder.license(apiInfo.getLicense());
        builder.licenseUrl(apiInfo.getLicenseUrl());
        builder.version(apiInfo.getVersion());

        return builder.build();
    }
}
