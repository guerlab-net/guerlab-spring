package net.guerlab.spring.upload.entity;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import net.guerlab.spring.cloud.commons.sequence.SnHelper;
import net.guerlab.spring.upload.config.PathInfoConfig;

/**
 * 文件信息
 *
 * @author guer
 *
 */
@ApiModel("文件信息")
public class FileInfo {

    private static final boolean IS_WINDOWS;

    /**
     * 保存路径
     */
    @ApiModelProperty(value = "保存路径", readOnly = true)
    private String savePath;

    /**
     * 保存文件名
     */
    @ApiModelProperty(value = "保存文件名", readOnly = true)
    private String saveFileName;

    /**
     * 文件名
     */
    @ApiModelProperty(value = "文件名", readOnly = true)
    private String fileName;

    /**
     * 后缀名
     */
    @ApiModelProperty(value = "后缀名", readOnly = true)
    private String suffix;

    /**
     * 文件类型
     */
    @ApiModelProperty(value = "文件类型", readOnly = true)
    private String contentType;

    /**
     * 文件大小
     */
    @ApiModelProperty(value = "文件大小", readOnly = true)
    private long fileSize;

    /**
     * 保存目录
     */
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private File saveDir;

    /**
     * 保存文件对象
     */
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private File saveFile;

    static {
        String os = System.getProperty("os.name");

        IS_WINDOWS = os != null && os.toLowerCase().startsWith("win");
    }

    /**
     * 通过保存路径和文件名后缀初始化路径信息
     *
     * @param path
     *            保存路径
     * @param suffix
     *            后缀
     * @param contentType
     *            文件类型
     * @param fileSize
     *            文件大小
     */
    public FileInfo(String path, String suffix, String contentType, long fileSize) {
        setFileName(SnHelper.createSn(), suffix);
        setPath(path);
        this.fileSize = fileSize;
        this.contentType = contentType;
    }

    /**
     * 通过保存路径和文件名后缀初始化路径信息
     *
     * @param path
     *            保存路径
     * @param fileName
     *            文件名
     * @param suffix
     *            后缀
     * @param contentType
     *            文件类型
     * @param fileSize
     *            文件大小
     */
    public FileInfo(String path, String fileName, String suffix, String contentType, long fileSize) {
        setFileName(fileName, suffix);
        setPath(path);
        this.fileSize = fileSize;
        this.contentType = contentType;
    }

    /**
     * 通过路径信息和文件名后缀初始化路径信息
     *
     * @param pathInfo
     *            路径信息
     * @param suffix
     *            后缀
     * @param contentType
     *            文件类型
     */
    public FileInfo(FileInfo pathInfo, String suffix, String contentType) {
        setFileName(SnHelper.createSn(), suffix);
        setPath(pathInfo == null ? "" : pathInfo.getSavePath());
        fileSize = pathInfo == null ? 0 : pathInfo.fileSize;
        this.contentType = contentType;
    }

    /**
     * 通过路径信息和文件名后缀初始化路径信息
     *
     * @param pathInfo
     *            路径信息
     * @param fileName
     *            文件名
     * @param suffix
     *            后缀
     * @param contentType
     *            文件类型
     */
    public FileInfo(FileInfo pathInfo, String fileName, String suffix, String contentType) {
        setFileName(fileName, suffix);
        setPath(pathInfo == null ? "" : pathInfo.getSavePath());
        fileSize = pathInfo == null ? 0 : pathInfo.fileSize;
        this.contentType = contentType;
    }

    /**
     * 返回保存路径
     *
     * @return 保存路径
     */
    public String getSavePath() {
        return savePath;
    }

    /**
     * 返回保存文件名
     *
     * @return 保存文件名
     */
    public String getSaveFileName() {
        return saveFileName;
    }

    /**
     * 返回文件名
     *
     * @return 文件名
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 返回后缀名
     *
     * @return 后缀名
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * 返回文件类型
     *
     * @return 文件类型
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * 返回文件大小
     *
     * @return 文件大小
     */
    public long getFileSize() {
        return fileSize;
    }

    /**
     * 获取web路径
     *
     * @return web路径
     */
    public String getWebPath() {
        return PathInfoConfig.getSaveBasePath() + savePath + saveFileName;
    }

    /**
     * 返回保存目录
     *
     * @return 保存目录
     */
    public File getSaveDir() {
        return saveDir;
    }

    /**
     * 获取保存文件对象
     *
     * @return 保存文件对象
     */
    public File getSaveFile() {
        if (saveFile == null) {
            synchronized (this) {
                if (saveFile == null) {
                    saveFile = new File(saveDir, saveFileName);
                }
            }
        }

        return saveFile;
    }

    private void setPath(
            String path) {
        savePath = StringUtils.isBlank(path) ? "" : path + File.separatorChar;

        if (!IS_WINDOWS) {
            savePath = savePath.replaceAll("\\\\", "\\").replaceAll("//", "/");
        }

        saveDir = new File(PathInfoConfig.getSaveBaseDir(), savePath);

        if (!saveDir.isDirectory()) {
            saveDir.mkdirs();
        }
    }

    private void setFileName(
            String fileName,
            String suffix) {
        this.fileName = fileName == null ? SnHelper.createSn() : fileName;
        this.suffix = suffix;
        saveFileName = this.fileName + suffix;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PathInfo [path=");
        builder.append(getWebPath());
        builder.append("]");
        return builder.toString();
    }
}
