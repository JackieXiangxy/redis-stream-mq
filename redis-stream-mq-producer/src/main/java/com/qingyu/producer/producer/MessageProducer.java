package com.qingyu.producer.producer;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author jackie
 * @since 2.0
 */
@Component
@AllArgsConstructor
public class MessageProducer {

    private final RedisTemplate<String, Object> redisTemplate;


}
