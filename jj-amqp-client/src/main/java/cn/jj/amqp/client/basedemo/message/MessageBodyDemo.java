package cn.jj.amqp.client.basedemo.message;

import cn.jj.amqp.message.MessageBody;
import lombok.Data;

import java.util.List;

/**
 * @author by wanghui03
 * @Classname MessageBodyDemo
 * @Description TODO
 * @Date 2021/11/30 14:32
 */
@Data
public class MessageBodyDemo implements MessageBody {

    private List<Integer> data;
}
