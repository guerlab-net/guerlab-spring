package net.guerlab.spring.upload.helper;

import net.guerlab.spring.commons.util.SpringApplicationContextUtil;
import net.guerlab.spring.upload.entity.IFileInfo;
import net.guerlab.spring.upload.entity.UploadFileInfo;
import net.guerlab.spring.upload.generator.SaveNameGenerator;
import net.guerlab.spring.upload.generator.SavePathGenerator;
import net.guerlab.spring.upload.handler.AfterUploadHandler;
import net.guerlab.spring.upload.handler.LocalFileSaveHandler;
import net.guerlab.spring.upload.handler.SaveHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MimeType;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
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
     *         上传文件对象
     * @param pathGenerator
     *         保存路径生成器
     * @param saveNameGenerator
     *         保存名称生成器
     * @return 保存路径
     */
    public static IFileInfo upload(final MultipartFile fileItem, final SavePathGenerator pathGenerator,
            final SaveNameGenerator saveNameGenerator) {
        return upload(fileItem, pathGenerator, saveNameGenerator, null);
    }

    /**
     * 上传文件处理
     *
     * @param fileItem
     *         上传文件对象
     * @param pathGenerator
     *         保存路径生成器
     * @param saveNameGenerator
     *         保存名称生成器
     * @param saveHandler
     *         保存处理
     * @return 保存路径
     */
    public static IFileInfo upload(final MultipartFile fileItem, final SavePathGenerator pathGenerator,
            final SaveNameGenerator saveNameGenerator, SaveHandler saveHandler) {
        if (saveHandler == null) {
            saveHandler = LocalFileSaveHandler.INSTANCE;
        }

        UploadFileInfo fileInfo = saveHandler.save(fileItem, pathGenerator, saveNameGenerator);
        afterUpload(fileInfo);
        return fileInfo;
    }

    /**
     * 获取批量上传的文件信息列表
     *
     * @param fileItemList
     *         上传文件列表
     * @param pathGenerator
     *         保存路径生成器
     * @param saveNameGenerator
     *         保存名称生成器
     * @return 文件信息列表
     */
    public static List<IFileInfo> multiUpload(final List<MultipartFile> fileItemList,
            final SavePathGenerator pathGenerator, final SaveNameGenerator saveNameGenerator) {
        return multiUpload(fileItemList, pathGenerator, saveNameGenerator, null);
    }

    /**
     * 获取批量上传的文件信息列表
     *
     * @param fileItemList
     *         上传文件列表
     * @param pathGenerator
     *         保存路径生成器
     * @param saveNameGenerator
     *         保存名称生成器
     * @param saveHandler
     *         文件保存处理
     * @return 文件信息列表
     */
    public static List<IFileInfo> multiUpload(final List<MultipartFile> fileItemList,
            final SavePathGenerator pathGenerator, final SaveNameGenerator saveNameGenerator,
            final SaveHandler saveHandler) {
        if (CollectionUtils.isEmpty(fileItemList)) {
            LOGGER.debug("fileItemList is null or is empty");
            return Collections.emptyList();
        }

        return fileItemList.stream().filter((file) -> file != null && !file.isEmpty())
                .map((file) -> upload(file, pathGenerator, saveNameGenerator, saveHandler))
                .collect(Collectors.toList());
    }

    private static void afterUpload(UploadFileInfo fileInfo) {
        Map<String, AfterUploadHandler> handlerMap = SpringApplicationContextUtil.getContext()
                .getBeansOfType(AfterUploadHandler.class);

        if (handlerMap.isEmpty()) {
            return;
        }

        HANDLER_POOL.execute(() -> handlerMap.values().stream().filter(Objects::nonNull).filter(e -> e.accept(fileInfo))
                .sorted((o1, o2) -> o2.order() - o1.order()).forEach(e -> e.handler(fileInfo)));
    }

}
