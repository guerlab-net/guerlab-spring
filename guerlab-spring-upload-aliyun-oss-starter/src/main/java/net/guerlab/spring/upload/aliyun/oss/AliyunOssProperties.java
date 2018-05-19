package net.guerlab.spring.upload.aliyun.oss;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import com.aliyun.oss.ClientConfiguration;

/**
 * 阿里云Oss配置
 *
 * @author guer
 *
 */
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "upload.aliyun.oss")
public class AliyunOssProperties {

    /**
     * 是否启用
     */
    private boolean enable = true;

    /**
     * endpoint
     */
    private String endpoint;

    /**
     * accessKeyId
     */
    private String accessKeyId;

    /**
     * accessKeySecret
     */
    private String accessKeySecret;

    /**
     * bucketName
     */
    private String bucketName;

    /**
     * OSSClient配置<br>
     * 配置列表通过<a href=
     * "https://help.aliyun.com/document_detail/32010.html?spm=a2c4g.11186623.6.673.OqIgwj">https://help.aliyun.com/document_detail/32010.html?spm=a2c4g.11186623.6.673.OqIgwj</a>查看
     */
    private ClientConfiguration clientConfig;

    /**
     * 返回是否启用
     *
     * @return 是否启用
     */
    public boolean isEnable() {
        return enable;
    }

    /**
     * 设置是否启用
     *
     * @param enable
     *            是否启用
     */
    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    /**
     * 返回endpoint
     *
     * @return endpoint
     */
    public String getEndpoint() {
        return endpoint;
    }

    /**
     * 设置endpoint
     *
     * @param endpoint
     *            endpoint
     */
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * 返回accessKeyId
     *
     * @return accessKeyId
     */
    public String getAccessKeyId() {
        return accessKeyId;
    }

    /**
     * 设置accessKeyId
     *
     * @param accessKeyId
     *            accessKeyId
     */
    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    /**
     * 返回accessKeySecret
     *
     * @return accessKeySecret
     */
    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    /**
     * 设置accessKeySecret
     *
     * @param accessKeySecret
     *            accessKeySecret
     */
    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    /**
     * 返回bucketName
     *
     * @return bucketName
     */
    public String getBucketName() {
        return bucketName;
    }

    /**
     * 设置bucketName
     *
     * @param bucketName
     *            bucketName
     */
    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    /**
     * 返回OSSClient配置<br>
     * 配置列表通过<a href=
     * "https://help.aliyun.com/document_detail/32010.html?spm=a2c4g.11186623.6.673.OqIgwj">https://help.aliyun.com/document_detail/32010.html?spm=a2c4g.11186623.6.673.OqIgwj</a>查看
     *
     * @return OSSClient配置
     */
    public ClientConfiguration getClientConfig() {
        return clientConfig;
    }

    /**
     * 设置OSSClient配置<br>
     * 配置列表通过<a href=
     * "https://help.aliyun.com/document_detail/32010.html?spm=a2c4g.11186623.6.673.OqIgwj">https://help.aliyun.com/document_detail/32010.html?spm=a2c4g.11186623.6.673.OqIgwj</a>查看
     *
     * @param clientConfig
     *            OSSClient配置
     */
    public void setClientConfig(ClientConfiguration clientConfig) {
        this.clientConfig = clientConfig;
    }
}
