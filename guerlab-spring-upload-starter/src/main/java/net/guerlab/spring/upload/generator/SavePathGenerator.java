package net.guerlab.spring.upload.generator;

import org.springframework.web.multipart.MultipartFile;

/**
 * 保存路径生成器
 *
 * @author guer
 */
@FunctionalInterface
public interface SavePathGenerator {

    /**
     * 生成保存路径
     *
     * @param file
     *         上传文件
     * @return 保存路径
     */
    String generate(MultipartFile file);
}
