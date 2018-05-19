package net.guerlab.spring.swagger2;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import net.guerlab.commons.collection.CollectionUtil;
import net.guerlab.spring.swagger2.properties.ApiInfoProperties;
import net.guerlab.spring.swagger2.properties.DocketProperties;
import net.guerlab.spring.swagger2.properties.ParameterProperties;
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

        if (CollectionUtil.isNotEmpty(properties.getGlobalOperationParameters())) {
            setGlobalOperationParameters(docket, properties.getGlobalOperationParameters());
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

    private static void setGlobalOperationParameters(Docket docket, List<ParameterProperties> propertiesList) {
        List<Parameter> operationParameters = propertiesList.stream().map(DocketFactory::createParameter)
                .collect(Collectors.toList());

        docket.globalOperationParameters(operationParameters);
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
