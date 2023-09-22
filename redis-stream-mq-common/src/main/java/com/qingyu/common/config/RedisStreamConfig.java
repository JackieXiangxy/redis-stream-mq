package com.qingyu.common.config;

import lombok.Data;

/**
 * @author jackie
 * @since 2.0
 */
@Data
public class RedisStreamConfig {
    /**
     * 解析消息流
     */
    private String parseStream;
    private String parseGroupOne;
    private String parseConsumerOne;
    private String parseConsumerTwo;
    /**
     * 消息数据流
     */
    private String dataStream;
    private String dataGroupOne;
    private String dataConsumerOne;
    private String dataConsumerTwo;
    /**
     * 消息记录流
     */
    private String recordStream;
    private String recordGroupOne;
    private String recordConsumerOne;
    private String recordConsumerTwo;
}
