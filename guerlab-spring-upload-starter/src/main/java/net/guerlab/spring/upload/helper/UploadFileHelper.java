package net.guerlab.spring.upload.helper;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MimeType;
import org.springframework.web.multipart.MultipartFile;

import net.guerlab.commons.exception.ApplicationException;
import net.guerlab.spring.upload.entity.FileInfo;

/**
 * 上传处理
 *
 * @author guer
 *
 */
public class UploadFileHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadFileHelper.class);

    private UploadFileHelper() {
        throw new IllegalAccessError();
    }

    /**
     * 获取文件对象的后缀名
     *
     * @param fileItem
     *            文件对象
     * @return 后缀名
     */
    public static String getSuffix(
            final MultipartFile fileItem) {
        if (fileItem == null) {
            return null;
        }

        String fileName = fileItem.getOriginalFilename();

        int index = fileName.lastIndexOf('.');

        if (index != -1) {
            return fileName.substring(index);
        }

        String contentType = fileItem.getContentType();

        int contentTypeIndex = contentType.lastIndexOf('/');

        if (contentTypeIndex == -1) {
            return contentType;
        }

        return "." + contentType.substring(contentTypeIndex + 1);
    }

    /**
     * 判断文件格式是否为指定的格式
     *
     * @param fileItem
     *            文件对象
     * @param mimeType
     *            mime格式
     * @return 是否为指定的格式
     */
    public static boolean includes(
            final MultipartFile fileItem,
            final MimeType mimeType) {
        if (fileItem == null || mimeType == null) {
            return false;
        }

        return mimeType.includes(MimeType.valueOf(fileItem.getContentType()));
    }

    /**
     * 上传文件处理
     *
     * @param fileItem
     *            上传文件对象
     * @return 保存路径
     */
    public static FileInfo upload(
            final MultipartFile fileItem) {
        return toUpload(fileItem, null);
    }

    /**
     * 上传文件处理
     *
     * @param fileItem
     *            上传文件对象
     * @param path
     *            保存路径
     * @return 保存路径
     */
    public static FileInfo upload(
            final MultipartFile fileItem,
            final String path) {
        return toUpload(fileItem, path);
    }

    /**
     * 上传文件处理
     *
     * @param fileItem
     *            上传文件对象
     * @param path
     *            保存路径
     * @param fileName
     *            保存文件名
     * @return 保存路径
     */
    public static FileInfo upload(
            final MultipartFile fileItem,
            final String path,
            final String fileName) {
        return toUpload(fileItem, path, fileName, null);
    }

    /**
     * 获取批量上传的文件信息列表
     *
     * @param fileItemList
     *            上传文件列表
     * @return 文件信息列表
     */
    public static List<FileInfo> multiUpload(
            final List<MultipartFile> fileItemList) {
        return toMultiUpload(fileItemList, UploadFileHelper::upload);
    }

    /**
     * 获取批量上传的文件信息列表
     *
     * @param fileItemList
     *            上传文件列表
     * @param savePath
     *            保存路径
     * @return 文件信息列表
     */
    public static List<FileInfo> multiUpload(
            final List<MultipartFile> fileItemList,
            final String savePath) {
        return toMultiUpload(fileItemList, fileItem -> upload(fileItem, savePath));
    }

    /**
     * 获取批量上传的文件信息列表
     *
     * @param fileItemList
     *            上传文件列表
     * @param savePath
     *            保存路径
     * @param fileName
     *            保存文件名
     * @return 文件信息列表
     */
    public static List<FileInfo> multiUpload(
            final List<MultipartFile> fileItemList,
            final String savePath,
            final String fileName) {
        if (CollectionUtils.isEmpty(fileItemList)) {
            LOGGER.debug("fileItemList is null or is empty");
            return Collections.emptyList();
        }

        int index = 1;

        List<FileInfo> list = new ArrayList<>(fileItemList.size());

        for (MultipartFile file : fileItemList) {
            if (file == null || file.isEmpty()) {
                continue;
            }

            list.add(upload(file, savePath, fileName + '_' + index++));
        }

        return list;
    }

    private static List<FileInfo> toMultiUpload(
            final List<MultipartFile> fileItemList,
            Function<MultipartFile, FileInfo> mapper) {
        if (CollectionUtils.isEmpty(fileItemList)) {
            LOGGER.debug("fileItemList is null or is empty");
            return Collections.emptyList();
        }

        return fileItemList.stream().filter(fileItem -> fileItem != null && !fileItem.isEmpty()).map(mapper)
                .collect(Collectors.toList());
    }

    private static FileInfo toUpload(
            final MultipartFile fileItem,
            final String path) {
        return toUpload(fileItem, path, null, null);
    }

    private static FileInfo toUpload(
            final MultipartFile fileItem,
            final String path,
            final String fileName,
            final String suffix) {
        FileInfo pathInfo = new FileInfo(path, fileName, getSuffix(suffix, fileItem), fileItem.getContentType(),
                fileItem.getSize());

        try {
            write(fileItem, pathInfo);
            return pathInfo;
        } catch (Exception e) {
            LOGGER.debug(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    private static String getSuffix(
            final String suffix,
            final MultipartFile fileItem) {
        String value = suffix;

        if (value == null) {
            value = getSuffix(fileItem);
        }

        return StringUtils.isBlank(value) ? "" : value;
    }

    private static void write(
            final MultipartFile fileItem,
            final FileInfo pathInfo) throws IOException {
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(pathInfo.getSaveFile()));
        stream.write(fileItem.getBytes());
        stream.close();
    }

}
