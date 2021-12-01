package cn.jj.amqp.client.basedemo.consumer;

import cn.jj.amqp.message.MqMessage;
import cn.jj.amqp.client.message.MqMessageDemo;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author by wanghui03
 * @Classname MqConsumerDemo
 * @Description TODO
 * @Date 2021/11/30 14:22
 */
@Configuration
@EnableConfigurationProperties(RabbitProperties.class)
@ComponentScan(basePackages = "cn.jj.amqp.client.listener")
public class MqConsumerDemo {


    /**
     * 用于接受Queue,默认绑定自己的名称
     *
     * @return
     */
    @Bean(name = MqMessage.PREFIX_QUEUE + "demoQueue")
    public Queue demoQueue() {
        return new Queue(MqMessage.PREFIX_QUEUE + "demoQueue");
    }

    /**
     * 定义一个topExchange
     *
     * @return
     */
    @Bean
    public TopicExchange demoExchange() {
        MqMessageDemo mqdemoMessage = MqMessage.getInstance(MqMessageDemo.class);
        return new TopicExchange(mqdemoMessage.getExchangeName());
    }



    /**
     * demoQueueBinding
     *
     * @return
     */
    @Bean(name = MqMessage.PREFIX_BINDING + "demoQueueBinding")
    public Binding demoQueueBinding() {
        MqMessageDemo mqMessageDemo = MqMessage.getInstance(MqMessageDemo.class);

        StringBuilder sbr = new StringBuilder();
        sbr.append(MqMessage.PREFIX_ROUTEKEY);
        sbr.append(mqMessageDemo.getMessageKey());
        sbr.append(MqMessage.DEMILITER);
        sbr.append("#");

        return BindingBuilder.bind(demoQueue()).to(demoExchange()).with(sbr.toString());
    }
}
