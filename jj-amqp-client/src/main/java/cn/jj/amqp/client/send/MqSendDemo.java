package cn.jj.amqp.client.send;

import cn.jj.amqp.client.message.MessageBodyDemo;
import cn.jj.amqp.client.message.MqMessageDemo;
import cn.jj.amqp.message.AbstractMessage;
import cn.jj.amqp.message.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author by wanghui03
 * @Classname MqSend
 * @Description TODO
 * @Date 2021/11/30 11:07
 */
@Service
public class MqSendDemo {

    @Autowired
    MessageSender<AbstractMessage> messageSender;


    public void sendMessage(){
        List<Integer> data = Arrays.asList(new Integer[]{1,2,3});
        MessageBodyDemo bodyDemo = new MessageBodyDemo();
        bodyDemo.setData(data);
        MqMessageDemo mqMessageDemo = new MqMessageDemo();
        mqMessageDemo.setMessageBody(bodyDemo);

        messageSender.send(mqMessageDemo);
    }

}
