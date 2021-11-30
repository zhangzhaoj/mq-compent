package cn.jj.amqp.event;

/**
 * @author by wanghui03
 * @Classname IEvent
 * @Description TODO
 * @Date 2021/11/30 11:29
 */
public interface IEvent {
    /**
     * 执行前事件（方法执行前）
     */
    void beforeEvent();

    /**
     * 执行后事件（所有方法之后完成后执行，一定会执行）
     */
    void afterEvent();

    /**
     * 当执行遇到异常时，是否中断后续流程
     * @return
     */
    boolean interrupted();
}
