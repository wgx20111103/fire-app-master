package io.renren.common.utils;

public class SnowflakeIdGenerator {

    // 起始的时间戳 (2023-01-01 00:00:00 UTC)
    private final static long START_TIMESTAMP = 1672531200000L;

    // 每一部分占用的位数
    private final static long SEQUENCE_BIT = 12; // 序列号占用的位数
    private final static long MACHINE_BIT = 5;   // 机器标识占用的位数
    private final static long DATACENTER_BIT = 5; // 数据中心标识占用的位数

    // 每一部分的最大值
    private final static long MAX_DATACENTER_NUM = -1L ^ (-1L << DATACENTER_BIT);
    private final static long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);
    private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);

    // 每一部分向左的位移
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTAMP_LEFT = SEQUENCE_BIT + MACHINE_BIT + DATACENTER_BIT;

    private long datacenterId;  // 数据中心ID
    private long machineId;     // 机器ID
    private long sequence = 0L; // 序列号
    private long lastTimestamp = -1L; // 上次生成ID的时间戳

    // 私有构造函数，防止外部实例化
    private SnowflakeIdGenerator(long datacenterId, long machineId) {
        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
            throw new IllegalArgumentException("Datacenter ID can't be greater than " + MAX_DATACENTER_NUM + " or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("Machine ID can't be greater than " + MAX_MACHINE_NUM + " or less than 0");
        }
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    // 单例模式，确保只有一个实例
    private static class SingletonHolder {
        private static final SnowflakeIdGenerator INSTANCE = new SnowflakeIdGenerator(1, 1); // 默认数据中心ID为1，机器ID为1
    }

    public static SnowflakeIdGenerator getInstance() {
        return SingletonHolder.INSTANCE;
    }

    // 生成下一个ID
    public synchronized long nextId() {
        long currentTimestamp = System.currentTimeMillis();

        if (currentTimestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate id");
        }

        if (currentTimestamp == lastTimestamp) {
            // 当前毫秒内，则+1
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0) {
                // 当前毫秒内溢出，则等待下一毫秒
                currentTimestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            // 不同毫秒则重置序列号
            sequence = 0L;
        }

        lastTimestamp = currentTimestamp;

        return ((currentTimestamp - START_TIMESTAMP) << TIMESTAMP_LEFT)
                | (datacenterId << DATACENTER_LEFT)
                | (machineId << MACHINE_LEFT)
                | sequence;
    }

    // 等待下一毫秒
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

    // 提供一个静态方法直接获取ID
    public static long getId() {
        return getInstance().nextId();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            long id = SnowflakeIdGenerator.getId();
            System.out.println(id);
        }
    }
}
