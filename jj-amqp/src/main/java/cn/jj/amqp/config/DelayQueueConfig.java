package cn.jj.amqp.config;

import cn.jj.amqp.message.MqMessage;
import org.springframework.amqp.core.*;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;

/**
 * @author by wanghui03
 * @Classname DelayQueueConfig
 * @Description TODO
 * @Date 2021/11/30 11:25
 */
@Configuration
@EnableConfigurationProperties(RabbitProperties.class)
public class DelayQueueConfig {
    /**
     * 消息默认延时时间(单位毫秒)
     *//*
    public static final long DEFAULT_DELAY_TIME = 2000;
    *//**
     * 队列延时时间(单位毫秒)
     *//*
    private final static int QUEUE_EXPIRATION = 2000;

    *//**
     * 延时消息方式,每个消息过期时间不同
     *//*
    public static final String DELAY_QUEUE_MESSAGE_TTL = MqMessage.PREFIX_QUEUE + "delay.queue.message.ttl";
    private static final String DELAY_EXCHANGE = MqMessage.PREFIX_EXCHANGE + "delay.exchange";
    public static final String DELAY_PROCESS_QUEUE = MqMessage.PREFIX_QUEUE + "delay.process.queue";

    *//**
     * 延时队列方式,即所有发到该队列的消息均有相同的过期时间
     *//*
    public static final String DELAY_QUEUE_PER_QUEUE_TTL_NAME = "delay.queue.per.queue_ttl";
    private static final String PER_QUEUE_TTL_EXCHANGE_NAME = "per.queue.ttl.exchange";


    *//**
     * 延迟消息缓存队列，按每个消息进行延迟
     *
     * @return
     *//*
    @Bean
    public Queue delayQueuePerMessageTTL() {
        return QueueBuilder.durable(DELAY_QUEUE_MESSAGE_TTL)
                .withArgument("x-dead-letter-exchange", DELAY_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DELAY_PROCESS_QUEUE)
                .build();
    }

    *//**
     * 延迟消息缓存队列,该队列所有消息都按此时间延迟
     *
     * @return
     *//*
    @Bean
    public Queue delayQueuePerQueueTTL() {
        return QueueBuilder.durable(DELAY_QUEUE_PER_QUEUE_TTL_NAME)
                .withArgument("x-dead-letter-exchange", DELAY_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DELAY_PROCESS_QUEUE)
                .withArgument("x-message-ttl", QUEUE_EXPIRATION)
                .build();
    }

    *//**
     * 延迟消息处理队列，即实际消费队列
     *
     * @return
     *//*
    @Bean
    public Queue delayProcessQueue() {
        return QueueBuilder.durable(DELAY_PROCESS_QUEUE).build();
    }

    *//**
     * 延迟消息处理交换机
     *
     * @return
     *//*
    @Bean
    public DirectExchange delayExchange() {
        return new DirectExchange(DELAY_EXCHANGE);
    }

    *//**
     * 延迟队列消息交换机
     *
     * @return
     *//*
    @Bean
    public DirectExchange perQueueTTLExchange() {
        return new DirectExchange(PER_QUEUE_TTL_EXCHANGE_NAME);
    }

    *//**
     * 将DLX绑定到实际消费队列
     *
     * @param delayProcessQueue
     * @param delayExchange
     * @return
     *//*
    @Bean
    public Binding dlxBinding(Queue delayProcessQueue, DirectExchange delayExchange) {
        return BindingBuilder.bind(delayProcessQueue)
                .to(delayExchange)
                .with(DELAY_PROCESS_QUEUE);
    }

    *//**
     * 将延时队列绑定在新的交换机(实际队列)
     * @param delayQueuePerQueueTTL
     * @param perQueueTTLExchange
     * @return
     *//*
    @Bean
    public Binding queueTTLBinding(Queue delayQueuePerQueueTTL, DirectExchange perQueueTTLExchange) {
        return BindingBuilder.bind(delayQueuePerQueueTTL)
                .to(perQueueTTLExchange)
                .with(DELAY_QUEUE_PER_QUEUE_TTL_NAME);
    }

    *//**
     * 计算消息延迟时间
     * @param timeUnit
     * @param delayTime
     * @return
     *//*
    public static long calculateDelayTime(TimeUnit timeUnit, long delayTime) {
        Assert.notNull(timeUnit, "timeUnit must not be null");
        Assert.isTrue(delayTime > 0, "delayTime 设置不合法");
        switch (timeUnit) {
            case DAYS:
                return delayTime * 24 * 60 * 60 * 1000;
            case HOURS:
                return delayTime * 60 * 60 * 1000;
            case MINUTES:
                return delayTime * 60 * 1000;
            case SECONDS:
                return delayTime * 1000;
            case MILLISECONDS:
                Assert.isTrue(delayTime > 1000, "请设置合法的延时时间");
                return delayTime;
            default:
                throw new RuntimeException("暂不支持的时间单位");
        }
    }*/
}
