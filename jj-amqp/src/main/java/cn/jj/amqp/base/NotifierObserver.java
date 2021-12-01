package cn.jj.amqp.base;


import cn.jj.amqp.common.util.PropertyReader;
import cn.jj.amqp.listener.AbstractListener;
import cn.jj.amqp.message.AbstractMessage;
import cn.jj.amqp.observer.AbstractObserver;
import org.springframework.util.StringUtils;

/**
 * @author by wanghui03
 * @Classname NotifierObserver
 * @Description 通知时间，当消息发生异常等情况时发送邮件提醒相关人员
 * @Date 2021/11/30 11:33
 */
public class NotifierObserver extends AbstractObserver<AbstractMessage> implements Amqp<AbstractMessage> {
    /**
     * 是否启用消费者预警
     */
    private final static boolean CONSUMER_WARN_ENABLE;
    /**
     * 预警临界值,超过该值触发预警
     */
    private final static long WARN_CRITICAL;
    /**
     * 邮件标题
     */
    private final static String WARN_TITLE = "消息队列预警";
    /**
     * 预警标识
     */
    private final static  String DEFAULT_WARN_KEY = "amqp_common";
    private AbstractListener.Condition condition;
    private AbstractMessage message;

    public NotifierObserver(AbstractListener.Condition condition) {
        this.condition = condition;
    }

    @Override
    public void init(AbstractMessage message) {
        this.message = message;
    }

    @Override
    public void execute() {
       this.notifier(message);
    }

    @Override
    public void notifier(AbstractMessage message) {
        try {
            //todo 通知
        } catch (Exception e) {

        }
    }
    @Override
    public boolean beforeCheck() {
        if (!CONSUMER_WARN_ENABLE) {
            return false;
        }
        long totalTime = condition.getTotalTime();
        return condition.getStatus() != 2 || totalTime >= WARN_CRITICAL;
    }

    static {
        CONSUMER_WARN_ENABLE = Boolean.parseBoolean(PropertyReader.getInstance().getProperty("spring.rabbitmq.consumer.warn.enable", "true"));
        WARN_CRITICAL = Long.parseLong(PropertyReader.getInstance().getProperty("spring.rabbitmq.warn.max.time", "5000"));
    }
}
