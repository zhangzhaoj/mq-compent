package cn.jj.amqp.base;


import cn.jj.amqp.message.MqMessage;

/**
 * @author by wanghui03
 * @Classname Amqp
 * @Description amqp接口
 * @Date 2021/11/30 10:27
 */
public interface Amqp<M extends MqMessage> {
    /**
     * 重试最大次数
     */
    int RETRY_MAX_COUNT = 5;
    /**
     * 持久化执行结果
     * @param message
     */
    default void save(M message) {

    }

    /**
     * 重试
     * @param message
     */
    default void retry(M message) {

    }

    /**
     * 通知
     * @param message
     */
    default void notifier(M message) {

    }
}
