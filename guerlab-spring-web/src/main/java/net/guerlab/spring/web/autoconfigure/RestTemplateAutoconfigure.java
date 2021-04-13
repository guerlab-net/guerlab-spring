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
package net.guerlab.spring.web.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.guerlab.spring.commons.autoconfigure.ObjectMapperAutoconfigure;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * RestTemplate自动配置
 *
 * @author guer
 *
 */
@Configuration
@AutoConfigureAfter(ObjectMapperAutoconfigure.class)
public class RestTemplateAutoconfigure {

    /**
     * 初始化LoadBalancedRestTemplate
     *
     * @param objectMapper objectMapper
     *
     * @return LoadBalancedRestTemplate
     */
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    @LoadBalanced
    @ConditionalOnBean(ObjectMapper.class)
    @ConditionalOnClass({ LoadBalanced.class, RestTemplate.class })
    @ConditionalOnMissingBean(value = RestTemplate.class, annotation = LoadBalanced.class)
    public RestTemplate loadBalancedRestTemplate(ObjectMapper objectMapper) {
        return createRestTemplate(objectMapper);
    }

    /**
     * 初始化RestTemplate
     *
     * @param objectMapper objectMapper
     *
     * @return RestTemplate
     */
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    @Primary
    @ConditionalOnBean(ObjectMapper.class)
    @ConditionalOnClass(RestTemplate.class)
    @ConditionalOnMissingBean(value = RestTemplate.class)
    public RestTemplate restTemplate(ObjectMapper objectMapper) {
        return createRestTemplate(objectMapper);
    }

    private RestTemplate createRestTemplate(ObjectMapper objectMapper) {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.setMessageConverters(Arrays.asList(new MappingJackson2HttpMessageConverter(objectMapper), new StringHttpMessageConverter(), new FormHttpMessageConverter()));

        return restTemplate;
    }
}
