package cn.jj.amqp.message;


import cn.jj.amqp.common.CommonResponse;
import cn.jj.amqp.common.util.IDUtil;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author by wanghui03
 * @Classname MessageSend
 * @Description
 * @Date 2021/11/30 10:00
 */
public interface MessageSend<S extends AbstractMessage> {
    /**
     * 发送消息到队列
     * @param message 消息内容
     */
    void send(S message);

    /**
     * 延迟发送消息到队列
     * @param message  消息内容
     * @param times 延迟时间 单位毫秒
     */
    @Deprecated
    void send(S message, int times);

    /**
     * 发送并等待响应
     * @param message
     * @return
     */
    CommonResponse sendAndReceive(S message);

    /**
     * 发送消息，适用于点对点模式
     * @param message
     * @param queueName
     */
    void send(S message, String queueName);

    /**
     * 消息执行前处理
     * @param message
     */
    default void before(S message) {
        Assert.notNull(message, "message must not be null");
        if (!StringUtils.hasText(message.getMessageId())){
            message.setMessageId(IDUtil.uuid());
        }
        if (MessageMode.DIRECT.equals(message.getMessageMode())) {
            Assert.hasText(message.getRoutingKey(), "routingKey must not be null");
        }
        if (MessageMode.FANOUT.equals(message.getMessageMode()) || MessageMode.TOPIC.equals(message.getMessageMode())) {
            Assert.hasText(message.getExchangeName(), "exchangeName must not be null");
        }
        //Assert.notNull(message.getMessageHeader(),"messageHeader must not be null!");
        Assert.notNull(message.getMessageBody(), "messageBody must not be null");
    }

    /**
     * 发送延时消息
     * @param message
     * @param delayTime (默认按分钟执行)
     */
    void sendDelayMessage(S message, long delayTime);

    /**
     * 发送延时消息
     * @param message
     * @param timeUnit
     * @param delayTime
     */
    void sendDelayMessage(S message, TimeUnit timeUnit, long delayTime);
}
