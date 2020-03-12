package net.guerlab.spring.upload.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Base64;
import java.util.UUID;

/**
 * 文件base64工具类
 *
 * @author guer
 */
public class FileBase64Utils {

    private static final String FORMAT_PREFIX = "data:";

    private static final String FORMAT_SUFFIX = ";base64";

    private static final String SEPARATOR = "/";

    private static final int SUB_CONTENT_TYPE_LENGTH = 2;

    private FileBase64Utils() {

    }

    /**
     * 解析文件base64为MultipartFile对象，当解析失败的时候返回null
     *
     * @param base64String
     *         文件base64
     * @return MultipartFile对象 / null
     */
    public static MultipartFile parse(String base64String) {
        String str = StringUtils.trimToEmpty(base64String);
        String[] values = str.split(",");
        if (values.length < 2) {
            return null;
        }

        String prefix = values[0];
        String base64Value = values[1];

        if (!prefix.startsWith(FORMAT_PREFIX) || !prefix.endsWith(FORMAT_SUFFIX)) {
            return null;
        }

        String contentType = prefix.substring(FORMAT_PREFIX.length());
        contentType = contentType.substring(0, contentType.length() - FORMAT_SUFFIX.length());
        String suffix = contentType;

        if (contentType.length() <= 0) {
            return null;
        }

        if (contentType.contains(SEPARATOR)) {
            String[] subContentTypes = contentType.split(SEPARATOR);
            if (subContentTypes.length != SUB_CONTENT_TYPE_LENGTH) {
                return null;
            }
            suffix = subContentTypes[1];
        }

        byte[] bytes;
        try {
            bytes = Base64.getDecoder().decode(base64Value);
        } catch (Exception e) {
            return null;
        }

        String name = UUID.randomUUID().toString() + "." + suffix;

        return new CustomerMultipartFile(name, name, contentType, bytes);
    }

    private static class CustomerMultipartFile implements MultipartFile {

        private String name;

        private String originalFilename;

        private String contentType;

        private byte[] bytes;

        public CustomerMultipartFile(String name, String originalFilename, String contentType, byte[] bytes) {
            this.name = name;
            this.originalFilename = originalFilename;
            this.contentType = contentType;
            this.bytes = bytes;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getOriginalFilename() {
            return originalFilename;
        }

        @Override
        public String getContentType() {
            return contentType;
        }

        @Override
        public boolean isEmpty() {
            return bytes.length <= 0;
        }

        @Override
        public long getSize() {
            return bytes.length;
        }

        @Override
        public byte[] getBytes() {
            return bytes;
        }

        @Override
        public InputStream getInputStream() {
            return new ByteArrayInputStream(bytes);
        }

        @Override
        public void transferTo(File file) throws IOException, IllegalStateException {
            try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file))) {
                stream.write(bytes);
            }
        }

        @Override
        public String toString() {
            return "CustomerMultipartFile{" + "name='" + name + '\'' + ", originalFilename='" + originalFilename + '\''
                    + ", contentType='" + contentType + '\'' + ", bytes.length=" + bytes.length + '}';
        }
    }

}
