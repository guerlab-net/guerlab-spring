package net.guerlab.spring.upload.config;

import java.io.File;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * 上传文件目录配置
 *
 * @author guer
 *
 */
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "upload")
public class PathInfoConfig {

    /**
     * 目录信息配置实例
     */
    private static PathInfoConfig instance;

    /**
     * 基本保存路径
     */
    private String path = "/";

    /**
     * 基本保存目录
     */
    private String dir = "/uploads";

    /**
     * 基本保存目录
     */
    private File saveBaseDir;

    /**
     * 构造目录信息配置
     */
    public PathInfoConfig() {
        PathInfoConfig.instance = this;
    }

    /**
     * 获取基本保存路径<br>
     * 相对于web的跟路径<br>
     * eq:<br>
     * 网站目录:http://domain.com<br>
     * path:/images<br>
     * 文件:test.txt<br>
     * web路径:http://domain.com/images/test.txt
     *
     * @return 基本保存路径
     */
    public static String getSaveBasePath() {
        return instance.path;
    }

    /**
     * 获取基本保存目录<br>
     * 文件保存目录路径<br>
     * eq:<br>
     * windows: C:/uploads<br>
     * linux: /uploads
     *
     * @return 基本保存目录
     */
    public static File getSaveBaseDir() {
        if (instance.saveBaseDir == null) {
            instance.saveBaseDir = new File(instance.dir);
        }
        return instance.saveBaseDir;
    }

    /**
     * 获取基本保存路径<br>
     * 相对于web的跟路径<br>
     * eq:<br>
     * 网站目录:http://domain.com<br>
     * path:/images<br>
     * 文件:test.txt<br>
     * web路径:http://domain.com/images/test.txt
     *
     * @return 基本保存路径
     */
    public String getPath() {
        return path;
    }

    /**
     * 设置基本保存路径<br>
     * 相对于web的跟路径<br>
     * eq:<br>
     * 网站目录:http://domain.com<br>
     * path:/images<br>
     * 文件:test.txt<br>
     * web路径:http://domain.com/images/test.txt
     *
     * @param path
     *            基本保存路径
     */
    public void setPath(
            String path) {
        this.path = path;
    }

    /**
     * 获取基本保存目录<br>
     * 文件保存目录路径<br>
     * eq:<br>
     * windows: C:/uploads<br>
     * linux: /uploads
     *
     * @return 基本保存目录
     */
    public String getDir() {
        return dir;
    }

    /**
     * 设置基本保存目录<br>
     * 文件保存目录路径<br>
     * eq:<br>
     * windows: C:/uploads<br>
     * linux: /uploads
     *
     * @param dir
     *            基本保存目录
     */
    public void setDir(
            String dir) {
        this.dir = dir;
    }

}
