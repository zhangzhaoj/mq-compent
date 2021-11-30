package cn.jj.amqp.observer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by wanghui03
 * @Classname ServerManager
 * @Description TODO
 * @Date 2021/11/30 11:29
 */
@Slf4j
public class ServerManager<T> {
    private List<IObserver<T>> observers;
    private T data;

    public static <T> ServerManager<T> builder(T data) {
        return new ServerManager<>(data);
    }

    public ServerManager(T data) {
        observers = new ArrayList<>(16);
        this.data = data;
    }

    public ServerManager<T> addServer(IObserver<T> observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
        return this;
    }

    public void notifyObservers() {
        if (CollectionUtils.isEmpty(observers)) {
            return;
        }
        for (IObserver<T> observer : observers) {
            try {
                observer.init(data);
                observer.beforeEvent();
                if (!observer.beforeCheck()) {
                    continue;
                }
                observer.execute();
                observer.assemble(data);
            } catch (Exception e) {
                log.error("{} Current observer failed to execute, message:{}, stack：{}", observer.getClass(), e.getMessage(), e);
                if (observer.interrupted()) {
                    throw e;
                }
            } finally {
                observer.afterEvent();
            }
        }
    }
}
