package net.guerlab.spring.upload.generator;

import net.guerlab.commons.exception.ApplicationException;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.*;

/**
 * 异步生成器
 *
 * @author guer
 */
public abstract class AbstractAsyncGenerator<T> implements Runnable {

    private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();

    private static final Executor HANDLER_POOL = new ThreadPoolExecutor(0, AVAILABLE_PROCESSORS, 60L, TimeUnit.SECONDS,
            new SynchronousQueue<>(), r -> {
        Thread thread = new Thread(r);
        thread.setDaemon(true);
        thread.setName("async-generator");
        return thread;
    }, new ThreadPoolExecutor.CallerRunsPolicy());

    protected final CountDownLatch countDownLatch = new CountDownLatch(1);

    protected MultipartFile currentFile;

    protected String currentName;

    /**
     * 通知
     *
     * @param entity
     *         通知对象
     */
    public abstract void notify(T entity);

    public String generate(MultipartFile file) {
        currentFile = file;

        HANDLER_POOL.execute(this);

        try {
            countDownLatch.await();
            return currentName;
        } catch (Exception e) {
            throw new ApplicationException(e.getLocalizedMessage(), e);
        } finally {
            currentFile = null;
            currentName = null;
        }
    }
}
