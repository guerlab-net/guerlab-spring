package net.guerlab.spring.upload.handler;

import net.guerlab.spring.upload.entity.UploadFileInfo;
import net.guerlab.spring.upload.generator.SaveNameGenerator;
import net.guerlab.spring.upload.generator.SavePathGenerator;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件保存处理
 *
 * @author guer
 */
@FunctionalInterface
public interface SaveHandler {

    /**
     * 判断是否处理
     *
     * @param fileItem
     *         文件上传对象
     * @param pathGenerator
     *         保存路径生成器
     * @param saveNameGenerator
     *         保存名称生成器
     * @return 是否处理
     */
    default boolean accept(final MultipartFile fileItem, final SavePathGenerator pathGenerator,
            final SaveNameGenerator saveNameGenerator) {
        return false;
    }

    /**
     * 保存文件
     *
     * @param fileItem
     *         文件上传对象
     * @param pathGenerator
     *         保存路径生成器
     * @param saveNameGenerator
     *         保存名称生成器
     * @return 文件信息
     */
    UploadFileInfo save(final MultipartFile fileItem, final SavePathGenerator pathGenerator,
            final SaveNameGenerator saveNameGenerator);
}
