package net.guerlab.spring.upload.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.guerlab.spring.upload.config.PathInfoConfig;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

/**
 * 上传文件信息
 *
 * @author guer
 */
public class UploadFileInfo implements IFileInfo {

    private static final String ROOT_PATH = "/";

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
    private final String contentType;

    /**
     * 文件大小
     */
    private final long fileSize;

    /**
     * 保存目录
     */
    @JsonIgnore
    private File saveDir;

    /**
     * 保存文件对象
     */
    @JsonIgnore
    private File saveFile;

    /**
     * 通过保存路径和文件名后缀初始化路径信息
     *
     * @param originalFilename
     *         原始文件名
     * @param path
     *         保存路径
     * @param fileName
     *         文件名
     * @param suffix
     *         后缀
     * @param contentType
     *         文件类型
     * @param fileSize
     *         文件大小
     */
    public UploadFileInfo(String originalFilename, String path, String fileName, String suffix, String contentType,
            long fileSize) {
        this.fileName = fileName;
        this.suffix = suffix;
        this.saveFileName = fileName + suffix;
        this.fileSize = fileSize;
        this.contentType = contentType;
        this.originalFilename = originalFilename;
        setPath(path);
    }

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
     * 返回保存文件名
     *
     * @return 保存文件名
     */
    @Override
    public String getSaveFileName() {
        return saveFileName;
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
     * 返回后缀名
     *
     * @return 后缀名
     */
    @Override
    public String getSuffix() {
        return suffix;
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
     * 返回文件大小
     *
     * @return 文件大小
     */
    @Override
    public long getFileSize() {
        return fileSize;
    }

    /**
     * 获取web路径
     *
     * @return web路径
     */
    @Override
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

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void setPath(String path) {
        savePath = StringUtils.isBlank(path) ? "" : path + File.separatorChar;

        savePath = savePath.replaceAll("\\\\", "/").replaceAll("//", "/");

        if (ROOT_PATH.equals(savePath)) {
            savePath = "";
        }

        saveDir = new File(PathInfoConfig.getSaveBaseDir(), savePath);

        if (!saveDir.isDirectory()) {
            saveDir.mkdirs();
        }
    }

    @Override
    public String toString() {
        return "PathInfo [path=" + getWebPath() + "]";
    }
}
