package net.guerlab.spring.upload.handler;

import net.guerlab.spring.upload.entity.FileInfo;

/**
 * 上传支持接口<br>
 * 多个处理接口按照{@link #order()}获取的值，由大到小逐一执行
 *
 * @author guer
 *
 */
@FunctionalInterface
public interface UploadHandler {

    /**
     * 默认排序值
     */
    int DEFAULT_ORDER = -100;

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
     * 获取排序值<br>
     * 默认值:{@link #DEFAULT_ORDER}
     *
     * @return 排序值
     */
    default int order() {
        return DEFAULT_ORDER;
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
