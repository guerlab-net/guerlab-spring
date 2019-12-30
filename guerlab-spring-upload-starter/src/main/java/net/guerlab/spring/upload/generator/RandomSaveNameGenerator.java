package net.guerlab.spring.upload.generator;

import net.guerlab.spring.commons.sequence.SnHelper;
import org.springframework.web.multipart.MultipartFile;

/**
 * 随机保存名称生成器
 *
 * @author guer
 */
public class RandomSaveNameGenerator implements SaveNameGenerator {

    /**
     * 随机保存名称生成器实例
     */
    public static final SaveNameGenerator INSTANCE = new RandomSaveNameGenerator();

    private RandomSaveNameGenerator() {

    }

    @Override
    public String generate(MultipartFile file) {
        return SnHelper.createSn();
    }
}
