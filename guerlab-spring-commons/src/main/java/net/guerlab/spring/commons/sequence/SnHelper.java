package net.guerlab.spring.commons.sequence;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import net.guerlab.commons.random.RandomUtil;

/**
 * 序列号助手
 *
 * @author guer
 *
 */
public class SnHelper {

    private static final DateTimeFormatter SIMPLE_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private SnHelper() {
    }

    /**
     * 创建序列号
     *
     * @return 序列号
     */
    public static String createSn() {
        return LocalDateTime.now().format(SIMPLE_DATE_FORMAT) + (RandomUtil.nextInt(900) + 100);
    }

}
