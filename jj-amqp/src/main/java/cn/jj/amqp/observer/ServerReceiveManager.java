package cn.jj.amqp.observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaowq
 * @since 2021/3/29 13:06
 */
public class ServerReceiveManager<T, R> {
    private final Logger logger = LoggerFactory.getLogger(ServerManager.class);
    private List<IReceiveObserver<T, R>> observers;
    private List<R> receives;
    private T data;

    public ServerReceiveManager(T data) {
        this.observers = new ArrayList<>(16);
        this.receives = new ArrayList<>(16);
        this.data = data;
    }

    public ServerReceiveManager<T, R> addServer(IReceiveObserver<T, R> observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
        return this;
    }

    public List<R> notifyReceiveObservers() throws Exception {
        if (CollectionUtils.isEmpty(observers)) {
            return receives;
        }
        R receive;
        for (IReceiveObserver<T, R> observer : observers) {
            try {
                observer.init(data);
                observer.beforeEvent();
                if (!observer.beforeCheck()) {
                    continue;
                }
                receive = observer.executeAndReceive();
                if (receive == null) {
                    continue;
                }
                receives.add(receive);
            } catch (Exception e) {
                logger.error("{} Current observer failed to execute, message:{}, stack：{}", observer.getClass(), e.getMessage(), e);
                if (observer.interrupted()) {
                    throw e;
                }
            } finally {
                observer.afterEvent();
            }
        }
        return receives;
    }
}