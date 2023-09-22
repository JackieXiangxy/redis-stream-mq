package com.qingyu.common.service;

import java.util.Map;

/**
 * 消息解析队列服务类--待解析的消息
 *
 * @author jackie
 * @since 2.0
 */
public interface MsgParseQueueService {
    /**
     * 描述: 添加消息到parse_stream
     *
     * @param configKey config的key
     * @return void
     * @author wangke
     * @date 2022/4/21 16:19
     */
    void saveMsgData(String configKey);

    /**
     * 描述: 添加消息到parse_stream
     *
     * @param value
     * @return void
     * @author wangke
     * @date 2022/4/21 16:19
     */
    void saveMsgData(Map<String, Object> value);

    /**
     * 描述: 解析数据，根据模板生成消息
     *
     * @param value
     * @return 解析成功返回true
     * @throws Exception 反射获取方法失败
     * @author wangke
     * @date 2022/4/21 13:53
     */
    Boolean parseMsgData(Map<String, String> value) throws Exception;
}
