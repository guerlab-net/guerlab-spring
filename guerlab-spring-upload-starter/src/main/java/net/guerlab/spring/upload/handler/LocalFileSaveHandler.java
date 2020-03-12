package net.guerlab.spring.upload.handler;

import net.guerlab.commons.exception.ApplicationException;
import net.guerlab.spring.upload.entity.UploadFileInfo;
import net.guerlab.spring.upload.generator.SaveNameGenerator;
import net.guerlab.spring.upload.generator.SavePathGenerator;
import net.guerlab.spring.upload.helper.UploadFileHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

/**
 * 本地文件保存处理
 *
 * @author guer
 */
public class LocalFileSaveHandler implements SaveHandler {

    public static final LocalFileSaveHandler INSTANCE = new LocalFileSaveHandler();

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalFileSaveHandler.class);

    private LocalFileSaveHandler() {

    }

    @Override
    public boolean accept(MultipartFile fileItem, SavePathGenerator pathGenerator,
            SaveNameGenerator saveNameGenerator) {
        return true;
    }

    @Override
    public UploadFileInfo save(MultipartFile fileItem, SavePathGenerator pathGenerator,
            SaveNameGenerator saveNameGenerator) {
        String path = pathGenerator.generate(fileItem);
        String fileName = saveNameGenerator.generate(fileItem);
        UploadFileInfo fileInfo = new UploadFileInfo(fileItem.getOriginalFilename(), path, fileName,
                UploadFileHelper.getSuffix(fileItem), fileItem.getContentType(), fileItem.getSize());

        try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(fileInfo.getSaveFile()))) {
            stream.write(fileItem.getBytes());
            return fileInfo;
        } catch (Exception e) {
            LOGGER.debug(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
