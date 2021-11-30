package cn.jj.amqp.common;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.util.Assert;
import java.io.Serializable;


/**
 * @author by wanghui03
 * @Classname KeyValue
 * @Description 键值对数据类型
 * @Date 2021/11/30 10:00
 */
public class KeyValue<K, V> implements Serializable {

	private static final long serialVersionUID = -8750711317323678169L;
	
	private K key;
	
	private V value;

	public KeyValue(){

	}

	public void setKey(K key){
		this.key = key;
	}

	public void setValue(V value) {
		this.value = value;
	}

	/**
	 * 生成键值对
	 * @param key 键
	 * @param value 值
	 */
	public KeyValue(K key, V value) {
		Assert.notNull(key, "Key must not be null!");
		this.key = key;
		this.value = value;
	}

	/**
	 * 获得键
	 * @return 键
	 */
	public K getKey() {
		return key;
	}

	/**
	 * 获得值
	 * @return 值
	 */
	public V getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31).append(key).append(value).toHashCode();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof KeyValue)) {
			return false;
		}
		KeyValue<K, V> that = (KeyValue<K, V>) other;
		return new EqualsBuilder().append(this.key, that.key)
				.append(this.value, that.value).isEquals();
	}

	@Override
	public String toString() {
		return "KeyValue [key=" + key + ", value=" + value + "]";
	}

}
