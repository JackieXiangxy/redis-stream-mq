package com.qingyu.common.config;

import com.qingyu.common.listener.ListenerMsgParseStream;
import com.qingyu.common.service.RedisStreamService;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.Subscription;

import java.time.Duration;

/**
 * 将消费者监听类绑定到相应的stream流上
 * 生产者绑定 msg_parse_stream流--未解析的消息
 *
 * @author jackie
 * @since 2.0
 */
@Configuration
public class ProducerParseConfig {
    @Resource
    private RedisStreamConfig redisStreamConfig;

    @Resource
    private RedisStreamService<String> redisStreamService;

    @Resource
    private ListenerMsgParseStream listenerMsgParseStream;
//    @Resource
//    private ListenerMsgParseStream2 listenerMsgParseStream2;


    /**
     * 描述: 构建流读取请求
     *
     * @param
     * @return org.springframework.data.redis.stream.Subscription
     * @author wangke
     * @date 2022/4/15 22:27
     */
    private StreamMessageListenerContainer.ConsumerStreamReadRequest<String> Construct(String key, String group, String consumerName) {
        //初始化stream和group
        redisStreamService.initStream(key, group);
        //指定消费最新消息
        StreamOffset<String> offset = StreamOffset.create(key, ReadOffset.lastConsumed());
        //创建消费者
        Consumer consumer = Consumer.from(group, consumerName);

        return StreamMessageListenerContainer.StreamReadRequest
                .builder(offset)
                .errorHandler((error) -> {
                })
                .cancelOnError(e -> false)
                .consumer(consumer)
                .autoAcknowledge(false) //不自动ACK确认
                .build();
    }

    /**
     * 描述: 解析消息队列 的订阅者1
     *
     * @param
     * @return org.springframework.data.redis.stream.Subscription
     * @author wangke
     * @date 2022/4/15 22:27
     */
    @Bean
    public Subscription subscriptionWithParseMsg(RedisConnectionFactory factory) {

        //创建容器
        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, MapRecord<String, String, String>> options = StreamMessageListenerContainer
                .StreamMessageListenerContainerOptions
                .builder()
                .pollTimeout(Duration.ofSeconds(5))
                .build();

        StreamMessageListenerContainer<String, MapRecord<String, String, String>> listenerContainer = StreamMessageListenerContainer.create(factory, options);
        //构建流读取请求
        StreamMessageListenerContainer.ConsumerStreamReadRequest<String> build = this.Construct(redisStreamConfig.getParseStream(), redisStreamConfig.getParseGroupOne(), redisStreamConfig.getParseConsumerOne());
        //将监听类绑定到相应的stream流上
        Subscription subscription = listenerContainer.register(build, listenerMsgParseStream);
        //启动监听
        listenerContainer.start();

        return subscription;
    }


    /**
     * 描述: 解析消息队列 的订阅者2
     *
     * @param
     * @return org.springframework.data.redis.stream.Subscription
     * @author wangke
     * @date 2022/4/15 22:27
     */
/*    @Bean
    public Subscription subscriptionWithParseMsg2(RedisConnectionFactory factory) {
        //创建容器
        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, MapRecord<String, String, String>> options = StreamMessageListenerContainer
                .StreamMessageListenerContainerOptions
                .builder()
                .pollTimeout(Duration.ofSeconds(1))
                .build();

        StreamMessageListenerContainer<String, MapRecord<String, String, String>> listenerContainer = StreamMessageListenerContainer.create(factory, options);
        //构建流读取请求
        StreamMessageListenerContainer.ConsumerStreamReadRequest<String> build = this.Construct(redisStreamConfig.getParseStream(), redisStreamConfig.getParseGroupOne(), redisStreamConfig.getParseConsumerTwo());
        //将监听类绑定到相应的stream流上
        Subscription subscription = listenerContainer.register(build, listenerMsgParseStream2);
        //启动监听
        listenerContainer.start();

        return subscription;
    }*/

}
