package cn.jj.amqp.common.util;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.ApplicationContext;

/**
 * @author by wanghui03
 * @Classname AppContext
 * @Description 应用上下文
 * @Date 2021/11/30 16:55
 */
public class AppContext {

    private static ApplicationContext applicationContext;
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
    public static void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }
    /**
     * 通过类型获取上下文中的bean
     * @param beanType
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> beanType) {
        try {
            return applicationContext.getBean(beanType);
        } catch (NoUniqueBeanDefinitionException e) {
            throw new RuntimeException(e);
        } catch (NoSuchBeanDefinitionException e) {
            return null;
        }
    }
}
