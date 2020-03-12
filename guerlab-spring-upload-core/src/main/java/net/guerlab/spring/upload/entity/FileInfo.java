package net.guerlab.spring.upload.entity;

/**
 * 文件信息
 *
 * @author guer
 */
public class FileInfo implements IFileInfo {

    /**
     * 原始文件名
     */
    private String originalFilename;

    /**
     * 保存路径
     */
    private String savePath;

    /**
     * 保存文件名
     */
    private String saveFileName;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 后缀名
     */
    private String suffix;

    /**
     * 文件类型
     */
    private String contentType;

    /**
     * 文件大小
     */
    private long fileSize;

    /**
     * web路径
     */
    private String webPath;

    /**
     * 获取原始文件名
     *
     * @return 原始文件名
     */
    @Override
    public String getOriginalFilename() {
        return originalFilename;
    }

    /**
     * 设置原始文件名
     *
     * @param originalFilename
     *         原始文件名
     */
    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    /**
     * 返回保存路径
     *
     * @return 保存路径
     */
    @Override
    public String getSavePath() {
        return savePath;
    }

    /**
     * 设置保存路径
     *
     * @param savePath
     *         保存路径
     */
    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    /**
     * 返回保存文件名
     *
     * @return 保存文件名
     */
    @Override
    public String getSaveFileName() {
        return saveFileName;
    }

    /**
     * 设置保存文件名
     *
     * @param saveFileName
     *         保存文件名
     */
    public void setSaveFileName(String saveFileName) {
        this.saveFileName = saveFileName;
    }

    /**
     * 返回文件名
     *
     * @return 文件名
     */
    @Override
    public String getFileName() {
        return fileName;
    }

    /**
     * 设置文件名
     *
     * @param fileName
     *         文件名
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 返回后缀名
     *
     * @return 后缀名
     */
    @Override
    public String getSuffix() {
        return suffix;
    }

    /**
     * 设置后缀名
     *
     * @param suffix
     *         后缀名
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    /**
     * 返回文件类型
     *
     * @return 文件类型
     */
    @Override
    public String getContentType() {
        return contentType;
    }

    /**
     * 设置文件类型
     *
     * @param contentType
     *         文件类型
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * 返回文件大小
     *
     * @return 文件大小
     */
    @Override
    public long getFileSize() {
        return fileSize;
    }

    /**
     * 设置文件大小
     *
     * @param fileSize
     *         文件大小
     */
    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * 获取web路径
     *
     * @return web路径
     */
    @Override
    public String getWebPath() {
        return webPath;
    }

    /**
     * 设置web路径
     *
     * @param webPath
     *         web路径
     */
    public void setWebPath(String webPath) {
        this.webPath = webPath;
    }

    @Override
    public String toString() {
        return "PathInfo [path=" + getWebPath() + "]";
    }
}
