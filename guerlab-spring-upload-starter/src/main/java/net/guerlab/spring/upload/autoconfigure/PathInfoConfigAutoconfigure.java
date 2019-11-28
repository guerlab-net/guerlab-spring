package net.guerlab.spring.upload.autoconfigure;

import net.guerlab.spring.upload.config.PathInfoConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author guer
 */
@Configuration
@EnableConfigurationProperties(PathInfoConfig.class)
public class PathInfoConfigAutoconfigure {}
