package cn.jj.amqp.common;

import cn.jj.amqp.message.*;
import lombok.Data;

import java.util.List;


/**
 * @author by wanghui03
 * @Classname MqCachePreheatMessage
 * @Description
 * @Date 2021/11/30 10:00
 */
@Data
public class MqCachePreheatMessage extends AbstractMessage {
    /**
     * 消息体
     */
    private CachePreheatMessage messageBody;
    /**
     * 消息头
     */
    private MessageContext messageHeader;
    /**
     * 消息类型
     */
    private MessageMode messageMode = MessageMode.DIRECT;

    public MqCachePreheatMessage(){}
    public MqCachePreheatMessage(CachePreheatMessage messageBody, MessageContext messageHeader) {
        this.messageBody = messageBody;
        this.messageHeader = messageHeader;
    }

    @Override
    public String getMessageKey() {
        return "cache";
    }

    @Override
    public String getEventKey() {
        return "preheat";
    }

    @Override
    public MessageMode getMessageMode() {
        return this.messageMode;
    }

    @Override
    public MessageHeader getMessageHeader() {
        return this.messageHeader;
    }

    @Override
    public CachePreheatMessage getMessageBody() {
        return this.messageBody;
    }

    @Data
    public static class CachePreheatMessage implements MessageBody {
        private List<Long> corpIds;
        public CachePreheatMessage(){}
        public CachePreheatMessage(List<Long> corpIds) {
            this.corpIds = corpIds;
        }
    }
}
