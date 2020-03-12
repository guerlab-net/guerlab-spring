package net.guerlab.spring.upload.entity;

/**
 * 文件信息
 *
 * @author guer
 */
public interface IFileInfo {

    /**
     * 获取原始文件名
     *
     * @return 原始文件名
     */
    String getOriginalFilename();

    /**
     * 返回保存路径
     *
     * @return 保存路径
     */
    String getSavePath();

    /**
     * 返回保存文件名
     *
     * @return 保存文件名
     */
    String getSaveFileName();

    /**
     * 返回文件名
     *
     * @return 文件名
     */
    String getFileName();

    /**
     * 返回后缀名
     *
     * @return 后缀名
     */
    String getSuffix();

    /**
     * 返回文件类型
     *
     * @return 文件类型
     */
    String getContentType();

    /**
     * 返回文件大小
     *
     * @return 文件大小
     */
    long getFileSize();

    /**
     * 获取web路径
     *
     * @return web路径
     */
    String getWebPath();
}
