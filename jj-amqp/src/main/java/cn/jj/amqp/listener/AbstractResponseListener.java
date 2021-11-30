package cn.jj.amqp.listener;

import cn.jj.amqp.common.CommonResponse;
import cn.jj.amqp.message.MessageHeader;
import cn.jj.amqp.message.MqMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;

/**
 * @author by wanghui03
 * @Classname AbstractResponseListener
 * @Description TODO
 * @Date 2021/11/30 11:11
 */
public abstract class AbstractResponseListener<M extends MqMessage> {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected MessageHeader messageHeader;

    /**
     * 监听消息
     * @param message
     */
    @RabbitHandler
    protected CommonResponse onMessage(M message) {
        long startTime = System.currentTimeMillis();
        logger.info("{} 执行开始", this.getClass());
        try {
            this.initContext(message);
            return this.processMessage(message);
        } catch (Exception e) {
            try {
                return processException(message, e);
            }catch (Exception ex){
                logger.error("{} 异常处理失败,原因:{},stack:{}",this.getClass(),ex.getMessage(),ex);
            }
        } finally {
            this.removeContext();
            logger.info("{} 执行结束, 耗时{}毫秒", this.getClass(), System.currentTimeMillis() - startTime);
        }
        return null;
    }

    /**
     * 初始化Context
     * @param mqMessage
     */
    protected void initContext(MqMessage mqMessage) {
        this.messageHeader = mqMessage.getMessageHeader();
        if (this.messageHeader == null) {
            logger.warn("messageHeader must not be null");
            return;
        }
    }

    /**
     * 释放ThreadLocal
     */
    protected void removeContext() {

    }

    /**
     * 处理消息
     * @param message
     * @return
     */
    protected abstract CommonResponse processMessage(M message);

    /**
     * 处理异常
     * @param message
     * @param ex
     * @return
     */
    protected abstract CommonResponse processException(M message,Exception ex);
}
