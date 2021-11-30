package cn.jj.amqp.common;

import lombok.Data;

import java.io.Serializable;


/**
 * @author by wanghui03
 * @Classname MessageDto
 * @Description
 * @Date 2021/11/30 10:00
 */
@Data
public class MessageDto implements Serializable {
    private String message;
    private String clazz;
}
