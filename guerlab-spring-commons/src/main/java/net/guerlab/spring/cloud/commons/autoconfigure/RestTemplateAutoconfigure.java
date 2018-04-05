package net.guerlab.spring.cloud.commons.autoconfigure;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@AutoConfigureAfter(ObjectMapperAutoconfigure.class)
public class RestTemplateAutoconfigure {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 初始化AsyncRestTemplate
     *
     * @return AsyncRestTemplate
     */
    @Bean
    @ConditionalOnMissingBean
    public AsyncRestTemplate loadBalancedAsyncRestTemplate() {
        AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();

        asyncRestTemplate.setMessageConverters(Arrays.asList(new MappingJackson2HttpMessageConverter(objectMapper),
                new StringHttpMessageConverter(), new FormHttpMessageConverter()));

        return asyncRestTemplate;
    }

    /**
     * 初始化LoadBalancedRestTemplate
     *
     * @return LoadBalancedRestTemplate
     */
    @Bean
    @LoadBalanced
    @ConditionalOnMissingBean(value = RestTemplate.class, annotation = LoadBalanced.class)
    public RestTemplate loadBalancedRestTemplate() {
        return createRestTemplate();
    }

    /**
     * 初始化RestTemplate
     *
     * @return RestTemplate
     */
    @Bean
    @Primary
    @ConditionalOnMissingBean(value = RestTemplate.class)
    public RestTemplate restTemplate() {
        return createRestTemplate();
    }

    private RestTemplate createRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.setMessageConverters(Arrays.asList(new MappingJackson2HttpMessageConverter(objectMapper),
                new StringHttpMessageConverter(), new FormHttpMessageConverter()));

        return restTemplate;
    }
}
