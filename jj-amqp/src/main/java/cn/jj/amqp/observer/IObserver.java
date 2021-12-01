package cn.jj.amqp.observer;


import cn.jj.amqp.event.IEvent;

/**
 * @author by wanghui03
 * @Classname IObserver
 * @Description 观察者接口
 * @Date 2021/11/30 11:29
 */
public interface IObserver<T> extends IEvent {

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
     */
    void execute();

    /**
     * 参数装配(最后执行)
     * @param data
     */
    void assemble(T data);
}
