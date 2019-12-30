package net.guerlab.spring.upload.generator;

import org.springframework.web.multipart.MultipartFile;

/**
 * 保存名称生成器
 *
 * @author guer
 */
@FunctionalInterface
public interface SaveNameGenerator {

    /**
     * 生成保存名称
     *
     * @param file
     *         上传文件
     * @return 保存名称
     */
    String generate(MultipartFile file);
}
