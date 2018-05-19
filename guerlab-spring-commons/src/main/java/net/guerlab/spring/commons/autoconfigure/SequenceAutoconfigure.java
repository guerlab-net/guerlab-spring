package net.guerlab.spring.commons.autoconfigure;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.guerlab.spring.commons.properties.SequenceProperties;
import net.guerlab.spring.commons.sequence.Sequence;

/**
 * 分布式序列自动配置
 *
 * @author guer
 *
 */
@Configuration
@EnableConfigurationProperties(SequenceProperties.class)
public class SequenceAutoconfigure {

    /**
     * 分布式序列自动配置
     * 
     * @param properties
     *            分布式序列自动配置属性
     * @return 分布式序列
     */
    @Bean
    @RefreshScope
    public Sequence sequence(SequenceProperties properties) {
        return new Sequence(properties.getWorkerId(), properties.getDataCenterId());
    }
}
