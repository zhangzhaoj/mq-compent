package cn.jj.amqp.common.util;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.io.IOException;
import java.util.Properties;

/**
 * @author by wanghui03
 * @Classname PropertyConfigurer
 * @Description TODO
 * @Date 2021/11/30 11:39
 */
public class PropertyConfigurer  extends PropertyPlaceholderConfigurer {

    private static Properties props;

    /**
     * 原方法是从文件中加载合并
     *
     * @return
     * @throws IOException
     */
    @Override
    protected Properties mergeProperties() throws IOException {
        props = super.mergeProperties();

        return props;
    }

    public Properties getProperties() {
        return props;
    }

}
