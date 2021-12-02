package cn.jj.amqp.client.basedemo.send;

import cn.jj.amqp.common.util.AppContext;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class MqSendDemoTest {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    private MqSendDemo MqSendDemo;
    @Test
    void sendMessage() {
        AppContext.setApplicationContext(applicationContext);
        MqSendDemo.sendMessage();
    }
}