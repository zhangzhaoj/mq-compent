package cn.jj.amqp.annotation;

import java.lang.annotation.*;

/**
 * @author by wanghui03
 * @Classname MqExtend
 * @Description amqp接口
 * @Date 2021/11/30 11:27
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MqExtend {

    /**
     * 扩展类别
     * @return
     */
    String value() default "rabbitmq";

    /**
     * 是否记录
     * @return
     */
    boolean isRecord() default true;

    /**
     * 队列集合，支持重试到其他队列，可自定义
     * @return
     */
    String[] queues() default {};

    /**
     * 队列描述
     * @return
     */
    String description();

    /**
     * 失败是否自动重试
     * @return
     */
    boolean autoRetry() default true;

    /**
     * 预警标识
     * @return
     */
    String warnKey() default "";
}
