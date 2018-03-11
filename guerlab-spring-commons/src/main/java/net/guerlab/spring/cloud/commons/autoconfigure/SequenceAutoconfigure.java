package net.guerlab.spring.cloud.commons.autoconfigure;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.guerlab.spring.cloud.commons.sequence.Sequence;

@Configuration
@EnableConfigurationProperties(SequenceProperties.class)
public class SequenceAutoconfigure {

    @Bean
    @RefreshScope
    public Sequence sequence(
            SequenceProperties properties) {
        return new Sequence(properties.getWorkerId(), properties.getDataCenterId());
    }
}
