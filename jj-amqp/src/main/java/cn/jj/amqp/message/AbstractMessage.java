package cn.jj.amqp.message;

import cn.jj.amqp.common.KeyValue;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author by wanghui03
 * @Classname MqMessage
 * @Description 消息接口
 * @Date 2021/11/30 10:00
 */
@Data
public abstract class AbstractMessage implements MqMessage {
    /**
     * 消息id，唯一标识
     */
    private String messageId;
    /**
     * 是否延时消息
     */
    private boolean hasDelay;
    /**
     * 延时消息超时是否丢弃
     */
    private boolean delayDiscard;
    /**
     * 延时时间
     */
    private long delayTime;
    /**
     * 延时时间单位
     */
    private TimeUnit timeUnit;
    /**
     * 失败后重试记录
     */
    private Integer retryCount;
    /**
     * 是否记录(生产者(发送时)/消费者(执行过程),都可以控制)
     */
    private boolean hasRecord = true;
    /**
     * 消息上下文
     */
    private MessageHeader messageHeader;

    /**
     * 自定义参数
     */
    private List<KeyValue<String,Object>> data;

    @JsonIgnore
    public <T> T getValue (String key, T defaultValue) {
        if (CollectionUtils.isEmpty(data)) {
            setValue(key,defaultValue);
            return defaultValue;
        }
        Map<String, Object> keyValueMap = data.parallelStream().collect(Collectors.toMap(KeyValue::getKey,KeyValue::getValue, (item1, item2) -> item2));
        return keyValueMap.containsKey(key) ? (T)keyValueMap.get(key) : defaultValue;
    }
    @JsonIgnore
    public void setValue (String key,Object value) {
        Assert.notNull(key,"key is not null");
        Assert.notNull(value,"value is not null");
        if (CollectionUtils.isEmpty(data)) {
            data = new ArrayList<>();
        }
        boolean isUpdate = false;
        for (KeyValue<String, Object> keyValue : data) {
            if (key.equals(keyValue.getKey())) {
                keyValue.setValue(value);
                isUpdate=true;
                break;
            }
        }
        if(!isUpdate){
            data.add(new KeyValue<>(key,value));
        }
    }
}
