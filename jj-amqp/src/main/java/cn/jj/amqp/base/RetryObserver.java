package cn.jj.amqp.base;

import cn.jj.amqp.common.util.AppContext;
import cn.jj.amqp.common.util.PropertyReader;
import cn.jj.amqp.listener.AbstractListener;
import cn.jj.amqp.message.AbstractMessage;
import cn.jj.amqp.message.MessageSend;
import cn.jj.amqp.observer.AbstractObserver;
import org.springframework.context.ApplicationContext;

/**
 * @author by wanghui03
 * @Classname RetryObserver
 * @Description TODO
 * @Date 2021/11/30 11:33
 */
public class RetryObserver extends AbstractObserver<AbstractMessage> implements Amqp<AbstractMessage> {
    /**
     * 失败是否自动重试
     */
    private final static boolean RETRY_ENABLE;
    /**
     * 重试次数
     */
    private final static int RETRY_COUNT;
    private MessageSend<AbstractMessage> sender;
    private AbstractListener.Condition condition;
    private AbstractMessage message;

    public RetryObserver(AbstractListener.Condition condition) {
        this.condition = condition;
    }

    @Override
    public void init(AbstractMessage message) {
        this.message = message;
        this.sender = AppContext.getBean(MessageSend.class);
    }

    @Override
    public void execute() {
        this.retry(message);
    }

    @Override
    public void retry(AbstractMessage message) {
        try {
            Integer retryCount = message.getRetryCount();
            message.setRetryCount(retryCount == null ? RETRY_COUNT : retryCount > RETRY_MAX_COUNT ? RETRY_MAX_COUNT : retryCount);
            if (message.getRetryCount() <= 0) {
                return;
            }
            message.setRetryCount(message.getRetryCount() - 1);
            sender.send(message, condition.getQueueName());
            logger.info("[{}]当前队列消息执行失败, 触发自动重试一次, messageId:{}", condition.getQueueName(), message.getMessageId());
        } catch (Exception e) {
            logger.error("队列:{}, 当前队列消息执行失败, 触发自动重试结果失败, 原因:{}, messageId:{}, stack:{}", this.condition.getQueueName(), e.getMessage(), message.getMessageId(), e);
        }
    }

    @Override
    public boolean beforeCheck() {
        if (!RETRY_ENABLE || !condition.isAutoRetry()) {
            return false;
        }
        return condition.getStatus() == (short)1;
    }

    static {
        RETRY_ENABLE = Boolean.parseBoolean(PropertyReader.getInstance().getProperty("spring.rabbitmq.retry.enable", "true"));
        RETRY_COUNT = Integer.parseInt(PropertyReader.getInstance().getProperty("spring.rabbitmq.retry.count", "1"));
    }
}
