package com.qingyu.common.service;

import com.qingyu.common.vo.ContentVO;

import java.util.Map;

/**
 * 消息数据队列服务类--解析后待发送的消息
 *
 * @author jackie
 * @since 2.0
 */
public interface MsgDataQueueService {
    /**
     * 描述: 添加消息到date_stream
     *
     * @param value
     * @return void
     * @author wangke
     * @date 2022/4/21 16:19
     */
    void sendMsg(Map<String, String> value);

    /**
     * 描述: 添加消息到date_stream
     *
     * @param model msgConfigKey,msgContent,msgCreateUser,msgSendUser,sendDingDing
     * @return void
     * @throws Exception 创建消息参数缺失,未配置消息模板
     * @author wangke
     * @date 2022/4/21 16:19
     */
    void sendMsg(ContentVO model);
}
