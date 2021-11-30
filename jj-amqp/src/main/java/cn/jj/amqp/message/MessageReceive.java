package cn.jj.amqp.message;

import java.io.Serializable;

/**
 * @author by wanghui03
 * @Classname MessageReceive
 * @Description 消息处理扩展接口
 * @Date 2021/11/30 10:00
 */
public interface MessageReceive<S extends MqMessage> extends Serializable {
    /**
     * 处理消息
     * @param message
     * @throws Exception
     */
    void handleMessage(S message) throws Exception;
}
