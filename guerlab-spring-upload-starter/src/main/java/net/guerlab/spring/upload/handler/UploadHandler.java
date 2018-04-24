package net.guerlab.spring.upload.handler;

import net.guerlab.spring.upload.entity.FileInfo;

/**
 * 上传支持接口
 *
 * @author guer
 *
 */
@FunctionalInterface
public interface UploadHandler {

    /**
     * 是否进行处理
     *
     * @param fileInfo
     *            文件信息对象
     * @return 是否进行处理
     */
    default boolean accept(
            FileInfo fileInfo) {
        return fileInfo != null && fileInfo.getFileSize() > 0 && fileInfo.getSaveFile() != null;
    }

    /**
     * 处理
     *
     * @param fileInfo
     *            文件信息对象
     */
    void handler(
            FileInfo fileInfo);
}
