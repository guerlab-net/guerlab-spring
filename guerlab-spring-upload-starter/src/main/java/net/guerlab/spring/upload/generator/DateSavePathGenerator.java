package net.guerlab.spring.upload.generator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

/**
 * 保存路径生成器
 *
 * @author guer
 */
public class DateSavePathGenerator implements SavePathGenerator {

    private static final String DEFAULT_PATH = "/";

    private String root;

    public DateSavePathGenerator(String root) {
        this.root = StringUtils.trimToNull(root);
    }

    @Override
    public String generate(MultipartFile file) {
        LocalDate localDate = LocalDate.now();

        if (root == null) {
            return localDate.getYear() + DEFAULT_PATH + localDate.getMonthValue();
        } else {
            return root + DEFAULT_PATH + localDate.getYear() + DEFAULT_PATH + localDate.getMonthValue();
        }
    }
}
