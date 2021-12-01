package cn.jj.amqp.base;

import cn.jj.amqp.listener.AbstractListener;
import cn.jj.amqp.message.AbstractMessage;
import cn.jj.amqp.observer.AbstractObserver;
import com.sun.deploy.appcontext.AppContext;

/**
 * @author by wanghui03
 * @Classname CompensationObserver
 * @Description 记录需要补偿的消息
 * @Date 2021/11/30 11:33
 */
public class CompensationObserver extends AbstractObserver<AbstractMessage> implements Amqp<AbstractMessage> {

    private AbstractListener.Condition condition;
    private AbstractMessage message;

    public CompensationObserver(AbstractListener.Condition condition) {
        this.condition = condition;
    }

    @Override
    public void init(AbstractMessage message) {
        this.message = message;
    }

    @Override
    public void execute() {
        this.save(message);
    }

    @Override
    public void save(AbstractMessage message) {
        try {

            logger.info("队列:{},记录消息:{}",this.condition.getQueueName(), message);
            //todo 持久化
        } catch (Exception ex) {
            logger.error("队列:{}, 日志记录失败, 原因:{}, messageId:{}, stack:{}", this.condition.getQueueName(), ex.getMessage(), message.getMessageId(), ex);
        }
    }


    @Override
    public boolean beforeCheck() {
        if (!message.isHasRecord() || !this.condition.isRecord()) {
            return false;
        }
        return super.beforeCheck();
    }
}
