package cn.jj.amqp.observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author by wanghui03
 * @Classname AbstractObserver
 * @Description TODO
 * @Date 2021/11/30 11:29
 */
public abstract class AbstractObserver<T> implements IObserver<T> {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected T data;
    protected boolean interrupted = true;

    @Override
    public void beforeEvent() {

    }

    @Override
    public boolean beforeCheck() {
        return true;
    }

    @Override
    public boolean interrupted() {
        return interrupted;
    }

    @Override
    public void assemble(T data) {

    }

    @Override
    public void afterEvent() {

    }
}
