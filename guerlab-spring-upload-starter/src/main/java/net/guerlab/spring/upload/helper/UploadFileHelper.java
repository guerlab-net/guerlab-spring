package net.guerlab.spring.upload.helper;

import net.guerlab.commons.exception.ApplicationException;
import net.guerlab.spring.commons.util.SpringApplicationContextUtil;
import net.guerlab.spring.upload.entity.FileInfo;
import net.guerlab.spring.upload.entity.UploadFileInfo;
import net.guerlab.spring.upload.handler.UploadHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MimeType;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 上传处理
 *
 * @author guer
 *
 */
public class UploadFileHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadFileHelper.class);

    private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();

    private static final Executor HANDLER_POOL = new ThreadPoolExecutor(0, AVAILABLE_PROCESSORS, 60L, TimeUnit.SECONDS,
            new SynchronousQueue<>(), r -> {
        Thread thread = new Thread(r);
        thread.setDaemon(true);
        thread.setName("upload-handler");
        return thread;
    }, new ThreadPoolExecutor.CallerRunsPolicy());

    private UploadFileHelper() {
    }

    /**
     * 获取文件对象的后缀名
     *
     * @param fileItem
     *            文件对象
     * @return 后缀名
     */
    public static String getSuffix(final MultipartFile fileItem) {
        if (fileItem == null) {
            return null;
        }

        String suffix = getSuffixByOriginalFilename(fileItem);

        if (suffix == null) {
            suffix = getSuffixByContentType(fileItem);
        }

        return suffix;
    }

    private static String getSuffixByOriginalFilename(final MultipartFile fileItem) {
        String fileName = fileItem.getOriginalFilename();

        if (fileName == null) {
            fileName = "";
        }

        int index = fileName.lastIndexOf('.');

        if (index != -1) {
            return fileName.substring(index);
        }

        return null;
    }

    private static String getSuffixByContentType(final MultipartFile fileItem) {
        String contentType = fileItem.getContentType();

        if (contentType == null) {
            contentType = "";
        }

        int index = contentType.lastIndexOf('/');

        if (index == -1) {
            return contentType;
        }

        return "." + contentType.substring(index + 1);
    }

    /**
     * 判断文件格式是否为指定的格式
     *
     * @param fileItem
     *         文件对象
     * @param mimeType
     *         mime格式
     * @return 是否为指定的格式
     */
    public static boolean match(final MultipartFile fileItem, final MimeType mimeType) {
        if (fileItem == null || mimeType == null) {
            return false;
        }

        return mimeType.includes(MimeType.valueOf(Objects.requireNonNull(fileItem.getContentType())));
    }

    /**
     * 上传文件处理
     *
     * @param fileItem
     *            上传文件对象
     * @return 保存路径
     */
    public static FileInfo upload(final MultipartFile fileItem) {
        return upload0(fileItem, null, null);
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
    public static FileInfo upload(final MultipartFile fileItem, final String path) {
        return upload0(fileItem, path, null);
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
    public static FileInfo upload(final MultipartFile fileItem, final String path, final String fileName) {
        return upload0(fileItem, path, fileName);
    }

    /**
     * 获取批量上传的文件信息列表
     *
     * @param fileItemList
     *            上传文件列表
     * @return 文件信息列表
     */
    public static List<FileInfo> multiUpload(final List<MultipartFile> fileItemList) {
        return multiUpload0(fileItemList, UploadFileHelper::upload);
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
    public static List<FileInfo> multiUpload(final List<MultipartFile> fileItemList, final String savePath) {
        return multiUpload0(fileItemList, fileItem -> upload(fileItem, savePath));
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
    public static List<FileInfo> multiUpload(final List<MultipartFile> fileItemList, final String savePath,
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

    private static List<FileInfo> multiUpload0(final List<MultipartFile> fileItemList,
            Function<MultipartFile, FileInfo> mapper) {
        if (CollectionUtils.isEmpty(fileItemList)) {
            LOGGER.debug("fileItemList is null or is empty");
            return Collections.emptyList();
        }

        return fileItemList.stream().filter(fileItem -> fileItem != null && !fileItem.isEmpty()).map(mapper)
                .collect(Collectors.toList());
    }

    private static FileInfo upload0(final MultipartFile fileItem, final String path, final String fileName) {
        UploadFileInfo fileInfo = new UploadFileInfo(fileItem.getOriginalFilename(), path, fileName,
                getSuffix(fileItem), fileItem.getContentType(), fileItem.getSize());

        try {
            write(fileItem, fileInfo);

            handler0(fileInfo);

            return fileInfo.convertToFileInfo();
        } catch (Exception e) {
            LOGGER.debug(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    private static void handler0(UploadFileInfo fileInfo) {
        Map<String, UploadHandler> handlerMap = SpringApplicationContextUtil.getContext()
                .getBeansOfType(UploadHandler.class);

        if (handlerMap.isEmpty()) {
            return;
        }

        HANDLER_POOL.execute(() -> handlerMap.values().stream().filter(Objects::nonNull).filter(e -> e.accept(fileInfo))
                .sorted((o1, o2) -> o2.order() - o1.order()).forEach(e -> e.handler(fileInfo)));
    }

    private static void write(final MultipartFile fileItem, final UploadFileInfo fileInfo) throws IOException {
        try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(fileInfo.getSaveFile()))) {
            stream.write(fileItem.getBytes());
        }
    }

}
