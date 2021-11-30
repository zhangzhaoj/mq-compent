package cn.jj.amqp.base;

import cn.jj.amqp.listener.AbstractListener;
import cn.jj.amqp.message.AbstractMessage;
import cn.jj.amqp.observer.ServerManager;

/**
 * @author by wanghui03
 * @Classname AmqpProxy
 * @Description Amqp代理类
 * @Date 2021/11/30 11:11
 */
public final class AmqpProxy {
    private AbstractMessage message;
    private AbstractListener.Condition condition;

    private AmqpProxy(){}

    public static void proxy(AbstractMessage message, AbstractListener.Condition condition) {
        AmqpProxy amqpProxy = new AmqpProxy(message, condition);
        amqpProxy.execute();
    }

    private AmqpProxy(AbstractMessage message, AbstractListener.Condition condition) {
        this();
        this.message = message;
        this.condition = condition;
    }

    private void execute() {
        ServerManager<AbstractMessage> manager = new ServerManager<>(message);
        manager.addServer(new CompensationObserver(condition));
        manager.addServer(new NotifierObserver(condition));
        manager.addServer(new RetryObserver(condition));
        manager.notifyObservers();
    }
}
