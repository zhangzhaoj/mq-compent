package cn.jj.amqp.client.basedemo.message;

import cn.jj.amqp.message.AbstractMessage;
import cn.jj.amqp.message.MessageContext;
import cn.jj.amqp.message.MessageMode;
import cn.jj.amqp.message.MqMessage;
import cn.jj.amqp.client.basedemo.message.MessageBodyDemo;

/**
 * @author by wanghui03
 * @Classname MqMseeageDemo
 * @Description TODO
 * @Date 2021/11/30 14:27
 */
public class MqMessageDemo extends AbstractMessage {

    private static final long serialVersionUID = -4391210442085244654L;

    /**
     * 消息体
     */
    private MessageBodyDemo messageBody;
    /**
     * 消息头
     */
    private MessageContext messageHeader;
    /**
     * 消息类型
     */
    private MessageMode messageMode = MessageMode.TOPIC;

    /**
     * 消息唯一标识
     */
    private String messageId;
    /**
     * 消息标题
     */
    private String messageTitle;

    /**
     * 失败后是否重发
     */
    private boolean failedNeedTry;

    public MqMessageDemo(){

    }

    public MqMessageDemo(MessageBodyDemo messageBody, MessageContext messageHeader){
        this.messageBody = messageBody;
        this.messageHeader = messageHeader;
    }
    /**
     * 消息标识
     * @return
     */
    @Override
    public String getMessageKey() {
        return "demo";
    }

    /**
     * 事件标识
     * @return
     */
    @Override
    public String getEventKey() {
        if(this.messageBody == null) {
            return "";
        }
        StringBuilder sbr = new StringBuilder();
        //sbr.append(messageType.getQueueTypeKey());
        sbr.append(MqMessage.DEMILITER);
        //sbr.append(messageType.getName());
        return sbr.toString();
    }


    @Override
    public MessageMode getMessageMode() {
        return messageMode;
    }

    @Override
    public MessageContext getMessageHeader() {
        return this.messageHeader;
    }

    @Override
    public MessageBodyDemo getMessageBody() {
        return this.messageBody;
    }

    public void setMessageBody(MessageBodyDemo messageBody) {
        this.messageBody = messageBody;
    }

    public void setMessageHeader(MessageContext messageHeader) {
        this.messageHeader = messageHeader;
    }

    public void setMessageMode(MessageMode messageMode) {
        this.messageMode = messageMode;
    }
}
