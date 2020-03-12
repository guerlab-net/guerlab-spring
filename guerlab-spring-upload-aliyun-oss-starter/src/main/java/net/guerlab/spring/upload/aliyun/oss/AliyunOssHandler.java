package net.guerlab.spring.upload.aliyun.oss;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import net.guerlab.spring.upload.entity.UploadFileInfo;
import net.guerlab.spring.upload.handler.AfterUploadHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 阿里云oss上传处理
 *
 * @author guer
 */
public class AliyunOssHandler implements AfterUploadHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AliyunOssHandler.class);

    private static final String OSS_EXCEPTION_MESSAGE =
            "Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason." + "\nError Message: {}"
                    + "\nError Code:    {}" + "\nRequest ID:    {}" + "\nHost ID:       {}";

    private static final String CLIENT_EXCEPTION_MESSAGE =
            "Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network." + "\nError Message: {}";

    private final OSS ossClient;

    private final AliyunOssProperties properties;

    /**
     * create AliyunOssHandler by oss client and oss properties
     *
     * @param ossClient
     *            oss client
     * @param properties
     *            oss properties
     */
    AliyunOssHandler(OSS ossClient, AliyunOssProperties properties) {
        this.ossClient = ossClient;
        this.properties = properties;
    }

    @Override
    public boolean accept(UploadFileInfo fileInfo) {
        return properties.isEnable() && AfterUploadHandler.super.accept(fileInfo);
    }

    @Override
    public void handler(UploadFileInfo fileInfo) {
        LOGGER.debug("start put object[{}]", fileInfo);

        String path = fileInfo.getWebPath();

        String key = path.startsWith("/") || path.startsWith("\\") ? path.substring(1) : path;

        PutObjectRequest request = new PutObjectRequest(properties.getBucketName(), key, fileInfo.getSaveFile());

        handler0(request);
    }

    private void handler0(PutObjectRequest request) {
        try {
            ossClient.putObject(request);
        } catch (OSSException oe) {
            LOGGER.debug(OSS_EXCEPTION_MESSAGE, oe.getErrorCode(), oe.getErrorCode(), oe.getRequestId(), oe.getHostId());
        } catch (ClientException ce) {
            LOGGER.debug(CLIENT_EXCEPTION_MESSAGE, ce.getMessage());
        } catch (Exception e) {
            LOGGER.debug(e.getMessage(), e);
        }
    }

}
