package com.qingyu.common.listener;

import com.qingyu.common.config.RedisStreamConfig;
import com.qingyu.common.service.MsgParseQueueService;
import com.qingyu.common.service.RedisStreamService;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

/**
 * @author jackie
 * @since 2.0
 */
@Slf4j
@Component
public class ListenerMsgParseStream implements StreamListener<String, MapRecord<String, String, String>> {

    @Resource
    private RedisStreamService<String> redisStreamService;

    @Resource
    private RedisStreamConfig redisStreamConfig;

    @Resource
    private MsgParseQueueService msgParseQueueService;


    @Override
    @SneakyThrows
    public void onMessage(MapRecord<String, String, String> message) {
        log.info("接受到来自redis的消息,message_id = {},stream = {},body = {}", message.getId(), message.getStream(), message.getValue());
        //解析数据,推送到消息数据队列
        Boolean parseStatus = msgParseQueueService.parseMsgData(message.getValue());
        if (parseStatus) {
            // 消费完成后手动确认消费ACK
            redisStreamService.ack(message.getStream(), redisStreamConfig.getParseGroupOne(), message.getId().getValue());
        }
    }
}
