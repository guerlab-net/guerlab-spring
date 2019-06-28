package net.guerlab.spring.commons.sequence;

import net.guerlab.commons.exception.ApplicationException;

/**
 * 序列
 *
 * @author guer
 *
 */
public class Sequence {

    /** 起始时间戳，用于用当前时间戳减去这个时间戳，算出偏移量 **/
    private static final long START_TIME = 1519740777809L;

    /** workerId占用的位数5（表示只允许workId的范围为：0-1023） **/
    private static final long WORKER_ID_BITS = 5L;

    /** dataCenterId占用的位数：5 **/
    private static final long DATA_CENTER_ID_BITS = 5L;

    /** 序列号占用的位数：12（表示只允许workId的范围为：0-4095） **/
    private static final long SEQUENCE_BITS = 12L;

    /** workerId可以使用的最大数值：31 **/
    private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);

    /** dataCenterId可以使用的最大数值：31 **/
    private static final long MAX_DATA_CENTER_ID = ~(-1L << DATA_CENTER_ID_BITS);

    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;

    private static final long DATA_CENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;

    private static final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATA_CENTER_ID_BITS;

    /** 用mask防止溢出:位与运算保证计算的结果范围始终是 0-4095 **/
    private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);

    private long workerId;

    private long dataCenterId;

    private long nowSequence = 0L;

    private long lastTimestamp = -1L;

    private boolean isClock = false;

    /**
     * 基于Snowflake创建分布式ID生成器
     * <p>
     * 注：sequence
     *
     * @param workerId
     *            工作机器ID,数据范围为0~31
     * @param dataCenterId
     *            数据中心ID,数据范围为0~31
     */
    public Sequence(long workerId, long dataCenterId) {
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(
                    String.format("worker Id can't be greater than %d or less than 0", MAX_WORKER_ID));
        }
        if (dataCenterId > MAX_DATA_CENTER_ID || dataCenterId < 0) {
            throw new IllegalArgumentException(
                    String.format("dataCenter Id can't be greater than %d or less than 0", MAX_DATA_CENTER_ID));
        }

        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
    }

    /**
     * 设置是否使用时钟
     *
     * @param clock
     *            是否使用时钟
     */
    public void setClock(boolean clock) {
        isClock = clock;
    }

    /**
     * 获取ID
     *
     * @return ID
     */
    public synchronized Long nextId() {
        long timestamp = timeGen();

        // 闰秒：如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            long offset = lastTimestamp - timestamp;
            if (offset > 5) {
                throw new ApplicationException(
                        String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", offset));
            }

            try {
                this.wait(offset << 1);
                timestamp = timeGen();
                if (timestamp < lastTimestamp) {
                    throw new ApplicationException(String
                            .format("Clock moved backwards.  Refusing to generate id for %d milliseconds", offset));
                }
            } catch (Exception e) {
                throw new ApplicationException(e);
            }
        }

        // 解决跨毫秒生成ID序列号始终为偶数的缺陷:如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            // 通过位与运算保证计算的结果范围始终是 0-4095
            nowSequence = nowSequence + 1 & SEQUENCE_MASK;
            if (nowSequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            // 时间戳改变，毫秒内序列重置
            nowSequence = 0L;
        }

        lastTimestamp = timestamp;

        /*
         * 1.左移运算是为了将数值移动到对应的段(41、5、5，12那段因为本来就在最右，因此不用左移)
         * 2.然后对每个左移后的值(la、lb、lc、sequence)做位或运算，是为了把各个短的数据合并起来，合并成一个二进制数
         * 3.最后转换成10进制，就是最终生成的id
         */
        return timestamp - START_TIME << TIMESTAMP_LEFT_SHIFT | dataCenterId << DATA_CENTER_ID_SHIFT
                | workerId << WORKER_ID_SHIFT | nowSequence;
    }

    /**
     * 保证返回的毫秒数在参数之后(阻塞到下一个毫秒，直到获得新的时间戳)
     *
     * @param lastTimestamp
     *            lastTimestamp
     * @return timestamp
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }

        return timestamp;
    }

    /**
     * 获得系统当前毫秒数
     *
     * @return timestamp
     */
    private long timeGen() {
        if (isClock) {
            // 解决高并发下获取时间戳的性能问题
            return SystemClock.now();
        } else {
            return System.currentTimeMillis();
        }
    }
}
