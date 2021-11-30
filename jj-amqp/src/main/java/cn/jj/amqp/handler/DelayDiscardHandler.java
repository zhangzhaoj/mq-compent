package cn.jj.amqp.handler;

import cn.jj.amqp.message.AbstractMessage;

/**
 * @author by wanghui03
 * @Classname DelayDiscardHandler
 * @Description 延迟消息处理器接口
 * @Date 2021/11/30 10:00
 */
public interface DelayDiscardHandler {

    /**
     * 初始化消息体
     * @param message
     */
    void initMessage(AbstractMessage message);

    /**
     * 获取消息体
     * @return
     */
    AbstractMessage getMessage();

    /**
     * 获取延时处理器,延时消息超时之后需要执行的handler
     * @return
     */
    DelayDiscardHandler getHandler();

    /**
     * 执行处理器
     */
    void invoke();
}
