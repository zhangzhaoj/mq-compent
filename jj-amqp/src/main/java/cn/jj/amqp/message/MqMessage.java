package cn.jj.amqp.message;

import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * @author by wanghui03
 * @Classname MqMessage
 * @Description 消息接口
 * @Date 2021/11/30 10:00
 */
public interface MqMessage extends Serializable{
    /**
     * routekey前缀
     */
    String PREFIX_ROUTEKEY = "jj.routekey.";
    /**
     * exchange前缀
     */
    String PREFIX_EXCHANGE = "jj.exchange.";

    /**
     * 分割符
     */
    char DEMILITER = '.';

    /**
     * queue前缀
     */
    String PREFIX_QUEUE = "jj.queue.";

    /**
     * binding前缀
     */
    String PREFIX_BINDING = "jj.binding.";

    /**
     * 队列名分隔符
     */
    String QUEUE_SEPARATE = "&";

    /**
     * lock前缀
     */
    String PREFIX_LOCK = "jj.lock.";

    /**
     * 消息key,消息标识加对象，比如passport.user
     * @return
     */
    String getMessageKey();

    /**
     * 事件key
     * @return
     */
    String getEventKey();

    /**
     * 实例化对象
     * @param entityClass
     * @return
     */
    static <T extends MqMessage> T getInstance(Class<T> entityClass){
        try {
            return entityClass.newInstance();
        }catch (InstantiationException | IllegalAccessException e){
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 交换机名称
     * @return
     */
    default String getExchangeName(){
        Assert.hasText(getMessageKey(),"messageKey must be not null!");
        return PREFIX_EXCHANGE + getMessageKey();
    }

    /**
     * 路由
     * @return
     */
    default String getRoutingKey(){
        Assert.hasText(getEventKey(),"eventKey must be not null!");
        StringBuilder sbr = new StringBuilder();
        sbr.append(PREFIX_ROUTEKEY);
        sbr.append(getMessageKey());
        sbr.append(DEMILITER);
        sbr.append(getEventKey());
        return sbr.toString();
    }

    /**
     * 是否需要响应(RPC模式)
     * @return
     */
    default boolean isNeedResponse(){
        return false;
    }

    /**
     * 消息模式
     * @return
     */
    MessageMode getMessageMode();

    /**
     * 消息头
     * @return
     */
    MessageHeader getMessageHeader();

    /**
     * 消息体
     * @return
     */
    MessageBody getMessageBody();

    /**
     * 获取消息响应(RPC模式)
     * @return
     */
    default MessageResponse getMessageResponse(){
        return null;
    }
}
