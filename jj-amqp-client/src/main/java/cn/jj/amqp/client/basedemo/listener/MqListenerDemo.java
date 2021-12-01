package cn.jj.amqp.client.basedemo.listener;

import cn.jj.amqp.annotation.MqExtend;
import cn.jj.amqp.listener.AbstractListener;
import cn.jj.amqp.message.MqMessage;
import cn.jj.amqp.client.basedemo.message.MqMessageDemo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author by wanghui03
 * @Classname MqListenerDemo
 * @Description TODO
 * @Date 2021/11/30 14:24
 */
@Component
@MqExtend(description = "消息队列监听demo")
@RabbitListener(queues = MqMessage.PREFIX_QUEUE + "demoQueue")
@Slf4j
public class MqListenerDemo extends AbstractListener<MqMessageDemo> {


    @Override
    protected void processMessage(MqMessageDemo message) {
        List<Integer> data = message.getMessageBody().getData();
        log.info("监听数据处理:{}", data);
    }

    @Override
    protected void processException(MqMessageDemo message, Exception ex) {
        log.error("mq process exception",ex);
    }
}
