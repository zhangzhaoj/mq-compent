package cn.jj.amqp.observer;

import cn.jj.amqp.event.IEvent;

/**
 * @author by wanghui03
 * @Classname IReceiveObserver
 * @Description TODO
 * @Date 2021/11/30 11:29
 */
public interface IReceiveObserver<T, R> extends IEvent {

    /**
     * 初始化
     * @param data
     */
    void init(T data);

    /**
     * 执行前校验
     * @return
     */
    boolean beforeCheck();

    /**
     * 执行
     * @return
     * @throws Exception
     */
    R executeAndReceive() throws Exception;
}