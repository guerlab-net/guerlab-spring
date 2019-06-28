package net.guerlab.spring.commons.sequence;

import java.sql.Timestamp;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 系统时钟
 *
 * @author guer
 *
 */
public class SystemClock {

    private final AtomicLong now;

    private SystemClock() {
        now = new AtomicLong(System.currentTimeMillis());
        scheduleClockUpdating();
    }

    private static class InstanceHolder {

        public static final SystemClock INSTANCE = new SystemClock();

        private InstanceHolder() {
        }
    }

    private static SystemClock instance() {
        return InstanceHolder.INSTANCE;
    }

    private void scheduleClockUpdating() {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(runnable -> {
            Thread thread = new Thread(runnable, "System Clock");
            thread.setDaemon(true);
            return thread;
        });
        scheduler.scheduleAtFixedRate(() -> now.set(System.currentTimeMillis()), 1L, 1L, TimeUnit.MILLISECONDS);
    }

    private long currentTimeMillis() {
        return now.get();
    }

    /**
     * 获取当前时钟
     *
     * @return 当前时钟
     */
    public static long now() {
        return instance().currentTimeMillis();
    }

    /**
     * 获取当前日期字符串
     *
     * @return 当前日期字符串
     */
    public static String nowDate() {
        return new Timestamp(instance().currentTimeMillis()).toString();
    }
}
