package cn.jj.amqp.message;

import cn.jj.amqp.common.CommonResponse;
import cn.jj.amqp.config.DelayQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author by wanghui03
 * @Classname MessageSender
 * @Description TODO
 * @Date 2021/11/30 11:21
 */
@Component
@Slf4j
public class MessageSender<S extends AbstractMessage> implements MessageSend<S> {

    /**
     * 获取RabbitTemplate
     * @return
     */
    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 消息发送
     * @param mqMessage
     */
    @Override
    public void send(S mqMessage){
        this.before(mqMessage);
        //发送消息
        try {
            if (mqMessage.isHasDelay()) {
                //rabbitTemplate.convertAndSend(DelayQueueConfig.DELAY_QUEUE_PER_QUEUE_TTL_NAME, mqMessage);
            } else {
                rabbitTemplate.convertAndSend(mqMessage.getExchangeName(), mqMessage.getRoutingKey(), mqMessage);
            }
        } catch (AmqpException e) {
            log.error("消息发送失败, 原因:{}, 是否延时：{}, messageId:{}", e.getMessage(), mqMessage.isHasDelay(), mqMessage.getMessageId(), e);
            throw new RuntimeException("消息发送失败",e);
        }
        log.info("消息发送完毕, messageId:{}", mqMessage.getMessageId());
    }

    @Override
    @Deprecated
    public void send(S mqMessage, int delay) {
        this.before(mqMessage);
        //发送延迟消息
        try{
            rabbitTemplate.convertAndSend(mqMessage.getExchangeName(),mqMessage.getRoutingKey(),mqMessage,(Message message) -> {
                message.getMessageProperties().setDelay(delay);
                return message;
            });
        }catch (AmqpException e){
            log.error("消息发送失败, 原因:{}, messageId:{}", e.getMessage(), mqMessage.getMessageId(), e);
            throw new RuntimeException("消息发送失败",e);
        }
    }

    @Override
    public CommonResponse sendAndReceive(S mqMessage) {
        this.before(mqMessage);
        if(!MessageMode.DIRECT.equals(mqMessage.getMessageMode())){
            throw new RuntimeException("点对点模式下不支持返回数据!");
        }
        //发送消息
        try {
            return (CommonResponse)rabbitTemplate.convertSendAndReceive(mqMessage.getExchangeName(), mqMessage.getRoutingKey(), mqMessage);
        } catch (AmqpException e) {
            log.error("消息发送失败, 原因:{}, messageId:{}", e.getMessage(), mqMessage.getMessageId(), e);
            throw new RuntimeException("消息发送失败",e);
        }
    }

    @Override
    public void send(S message, String queueName) {
        this.before(message);
        Assert.hasText(queueName, "queueName must not be null");
        try{
            rabbitTemplate.convertAndSend(queueName, message);
        }catch (AmqpException e){
            log.error("消息发送失败, 原因:{}, messageId:{}", e.getMessage(), message.getMessageId(), e);
            throw new RuntimeException("消息发送失败",e);
        }
        log.info("消息发送完毕, messageId:{}", message.getMessageId());
    }

    @Override
    public void sendDelayMessage(S message, long delayTime) {
        this.sendDelayMessage(message, TimeUnit.MINUTES, delayTime);
    }

    @Override
    public void sendDelayMessage(S message, TimeUnit timeUnit, long delayTime) {
        /*this.before(message);
        Assert.isTrue(message.isHasDelay(), "请设置延时消息标记");
        try {
            message.setTimeUnit(timeUnit);
            message.setDelayTime(delayTime);
            long expiration = DelayQueueConfig.calculateDelayTime(timeUnit, delayTime);
            if (expiration < DelayQueueConfig.DEFAULT_DELAY_TIME) {
                rabbitTemplate.convertAndSend(message.getExchangeName(), message.getRoutingKey(), message);
            } else {
                rabbitTemplate.convertAndSend(DelayQueueConfig.DELAY_QUEUE_MESSAGE_TTL, message, param -> {
                    param.getMessageProperties().setExpiration(String.valueOf(expiration));
                    return param;
                });
            }
        } catch (Exception e) {
            log.error("延时消息发送失败, 原因:{}, messageId:{}", e.getMessage(), message.getMessageId(), e);
            throw new RuntimeException("延时消息发送失败", e);
        }
        log.info("发送延时消息完毕, messageId:{}", message.getMessageId());*/
    }
}
