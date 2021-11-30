package cn.jj.amqp.message;

/**
 * @author by wanghui03
 * @Classname MessageMode
 * @Description 消息模式
 * @Date 2021/11/30 10:00
 */
public enum MessageMode {
    /**
     * 点对点模式
     */
    DIRECT,
    /**
     * 广播模式
     */
    FANOUT,
    /**
     * 主题模式
     */
    TOPIC
}
