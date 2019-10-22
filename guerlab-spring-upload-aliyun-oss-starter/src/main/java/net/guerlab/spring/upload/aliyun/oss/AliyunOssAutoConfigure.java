package net.guerlab.spring.upload.aliyun.oss;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云Oss自动配置
 *
 * @author guer
 *
 */
@Configuration
@EnableConfigurationProperties(AliyunOssProperties.class)
public class AliyunOssAutoConfigure {

    /**
     * create OSS client
     *
     * @param properties
     *            OSS properties
     * @return OSS client
     */
    @RefreshScope
    @Bean
    public OSS ossClient(AliyunOssProperties properties) {
        ClientConfiguration config = properties.getClientConfig();

        String endpoint = properties.getEndpoint();
        String accessKeyId = properties.getAccessKeyId();
        String accessKeySecret = properties.getAccessKeySecret();

        DefaultCredentialProvider credentialProvider = new DefaultCredentialProvider(accessKeyId, accessKeySecret);

        if (config == null) {
            config = new ClientConfiguration();
        }

        return new OSSClient(endpoint, credentialProvider, config);
    }

    /**
     * create AliyunOssHandler by oss client and oss properties
     *
     * @param ossClient
     *            OSS client
     * @param properties
     *            OSS properties
     * @return AliyunOssHandler AliyunOssHandler
     */
    @Bean
    public AliyunOssHandler aliyunOssHandler(OSS ossClient, AliyunOssProperties properties) {
        return new AliyunOssHandler(ossClient, properties);
    }
}
