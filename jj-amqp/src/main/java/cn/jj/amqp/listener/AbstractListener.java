package cn.jj.amqp.listener;

import cn.jj.amqp.annotation.MqExtend;
import cn.jj.amqp.base.AmqpProxy;
import cn.jj.amqp.common.util.PropertyReader;
import cn.jj.amqp.message.AbstractMessage;
import cn.jj.amqp.message.MessageHeader;
import cn.jj.amqp.message.MqMessage;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.util.Assert;

/**
 * @author by wanghui03
 * @Classname AbstractListener
 * @Description 消费者监听基类
 * @Date 2021/11/30 11:11
 */
@Slf4j
public abstract class AbstractListener<M extends AbstractMessage> {
    /**
     * 日志长度
     */
    private final static int LOGGER_LENGTH = Integer.parseInt(PropertyReader.getInstance().getProperty("spring.rabbitmq.logger.length", "1000"));;
    /**
     * 超过此毫秒数则记录性能日志
     */
    private final static int PERFORMANCE_VALUE = 500;
    /**
     * 上下文
     */
    protected MessageHeader messageHeader;
    /**
     * 重试条件
     */
    private Condition condition;
    /**
     * 存储响应信息
     */
    protected volatile StringBuilder responseMessage;
    /**
     * 消息唯一标识
     */
    protected String messageId;


    /**
     * 监听消息
     *
     * @param message
     */
    @RabbitHandler
    protected void onMessage(M message) {
        long startTime = System.currentTimeMillis();
        try {
            this.init(message);
            if (this.beforeEvent(message)) {
                this.processMessage(message);
            }
        } catch (Exception e) {
            this.handlerException(message, e);
        } finally {
            try {
                this.afterEvent(message);
            } catch (Exception e) {
                log.error("{} 消息执行后事件处理失败, 原因:{}, stack:{}, messageId:{}", this.getClass(), e.getMessage(), e, message.getMessageId());
            }
            long totalTime = System.currentTimeMillis() - startTime;
            this.condition.setTotalTime(totalTime);
            this.condition.setResponseMessage(this.buildMessage(responseMessage));
            AmqpProxy.proxy(message, this.condition);
            this.destroy();
            if(totalTime>PERFORMANCE_VALUE) {
                log.info("{} 执行结束, 耗时{}毫秒, messageId:{}", this.getClass(), totalTime, message.getMessageId());
            }
        }
    }

    private void handlerException(M message, Exception e) {
        StringBuilder exceptionMessage = new StringBuilder("消息执行失败, 原因:");
        try {
            exceptionMessage.append(e.getMessage());
            log.error("{} 消息执行失败, 原因:{}, stack:{}, messageId:{}", this.getClass(), e.getMessage(), e, message.getMessageId());
            processException(message, e);
        } catch (Exception ex) {
            log.error("{} 消息执行失败后处理异常失败, 原因:{}, stack:{}, messageId:{}", this.getClass(), ex.getMessage(), ex, message.getMessageId());
            exceptionMessage.append("|")
                    .append(ex.getMessage());
        }
        this.condition.setStatus((short)0);
        this.condition.setExceptionMessage(this.buildMessage(exceptionMessage));
    }

    private String buildMessage(StringBuilder message) {
        String newMessage = message.toString();
        if (newMessage.length() > LOGGER_LENGTH) {
            return newMessage.substring(0, LOGGER_LENGTH);
        }
        return newMessage;
    }

    /**
     * 子类初始化重试入口，默认重试
     *
     * @param queueName
     * @param messageTitle
     */
    @Deprecated
    protected void tryRecord(String queueName, String messageTitle) {
        if (this.condition == null) {
            this.condition = new Condition(queueName, messageTitle, (short)0);
        }
    }

    private void init(M mqMessage) {
        this.messageId = mqMessage.getMessageId();
        this.messageHeader = mqMessage.getMessageHeader();
        this.responseMessage = new StringBuilder();
        if (this.messageHeader != null) {
        }
        this.initExtend(mqMessage);
    }

    /**
     * 释放资源
     */
    private void destroy() {
        this.responseMessage = null;
        this.condition = null;
        this.messageId = null;
    }

    /**
     * 执行前初始化
     * @param message
     */
    private void initExtend(M message) {
        try {
            MqExtend mqExtend = this.getClass().getAnnotation(MqExtend.class);
            String description = this.getClass().getSimpleName();
            String[] queues = null;
            String warnKey = null;
            if (mqExtend != null) {
                queues = mqExtend.queues();
                description = mqExtend.description();
                warnKey = mqExtend.warnKey();
            }
            if (queues == null || queues.length == 0) {
                RabbitListener rabbitListener = this.getClass().getAnnotation(RabbitListener.class);
                queues = rabbitListener.queues();
            }
            String queueName = StringUtils.join(queues, MqMessage.QUEUE_SEPARATE);
            this.condition = new Condition(queueName, description);
            this.condition.setRecord(mqExtend == null || mqExtend.isRecord());
            this.condition.setAutoRetry(mqExtend == null || mqExtend.autoRetry());
            this.condition.setWarnKey(warnKey);
        } catch (Exception e) {
            log.error("{} 消息执行前初始化扩展信息失败, 原因:{}, stack:{}, messageId:{}", this.getClass(), e.getMessage(), e, message.getMessageId());
        }
    }

    /**
     * 处理消息
     *
     * @param message
     * @return
     */
    protected abstract void processMessage(M message);

    /**
     * 处理异常
     *
     * @param message
     * @param ex
     * @return
     */
    protected abstract void processException(M message, Exception ex);

    /**
     * 消息执行前事件
     * @param message
     * @return
     */
    protected boolean beforeEvent(M message) {
        return true;
    }

    /**
     * 消息执行后事件
     * @param message
     * @return
     */
    protected boolean afterEvent(M message) {
        return true;
    }

    @Data
    public static class Condition {
        /**
         * 补偿队列名
         */
        private String queueName;
        /**
         * 补偿消息标题
         */
        private String messageTitle;
        /**
         * 消息执行状态 ： 0：失败，1：关闭；2：成功
         */
        private short status = 2;
        /**
         * 是否记录消息
         */
        private boolean isRecord = true;
        /**
         * 消息内容
         */
        private String content;
        /**
         * 响应信息
         */
        private String responseMessage;
        /**
         *  异常信息
         */
        private String exceptionMessage;
        /**
         * 失败是否自动重试
         */
        private boolean autoRetry = true;
        /**
         * 预警标识
         */
        private String warnKey;
        /**
         * 总耗时
         */
        private long totalTime;

        private Condition(String queueName, String messageTitle) {
            this.checkCondition(queueName, messageTitle);
            this.queueName = queueName;
            this.messageTitle = messageTitle;
        }

        private Condition(String queueName, String messageTitle, short status) {
            this.checkCondition(queueName, messageTitle);
            this.queueName = queueName;
            this.messageTitle = messageTitle;
            this.status = status;
        }
        private void checkCondition(String queueName, String messageTitle) {
            Assert.hasText(queueName, "queueName must not be null");
            Assert.hasText(messageTitle, "messageTitle must not be null");
        }
    }
}
