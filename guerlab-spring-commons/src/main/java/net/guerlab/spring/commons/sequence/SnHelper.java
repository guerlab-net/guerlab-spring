/*
 * Copyright 2018-2021 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.guerlab.spring.commons.sequence;

import net.guerlab.commons.random.RandomUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
