package cn.jj.amqp.client.delayquery;

import cn.jj.amqp.annotation.MqExtend;
import cn.jj.amqp.listener.AbstractListener;
import cn.jj.amqp.message.AbstractMessage;
import cn.jj.amqp.message.MessageMode;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author by wanghui03
 * @Classname DelayListener
 * @Description 延迟队列监听
 * @Date 2021/11/30 11:11
 */
@Component
@MqExtend(description = "延迟队列监听", isRecord = false)
@RabbitListener(queues = DelayQueueConfig.DELAY_PROCESS_QUEUE)
public class DelayListenerDemo extends AbstractListener<AbstractMessage> {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    protected void processMessage(AbstractMessage message) {
        if (message.getMessageMode() == MessageMode.DIRECT) {
            rabbitTemplate.convertAndSend(message.getRoutingKey(), message);
        } else {
            rabbitTemplate.convertAndSend(message.getExchangeName(), message.getRoutingKey(), message);
        }
    }

    @Override
    protected void processException(AbstractMessage message, Exception ex) {

    }
}
