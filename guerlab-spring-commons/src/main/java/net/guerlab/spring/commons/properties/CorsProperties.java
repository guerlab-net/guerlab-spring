package net.guerlab.spring.commons.properties;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.cors.CorsConfiguration;

/**
 * CORS配置
 * 
 * @author guer
 *
 */
@RefreshScope
@ConditionalOnProperty(name = "web.cors.enable", havingValue = "true")
@ConfigurationProperties("web.cors")
public class CorsProperties extends CorsConfiguration {

}
