package cn.jj.amqp.client.send;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class MqSendDemoTest {

    @Autowired
    private MqSendDemo MqSendDemo;
    @Test
    void sendMessage() {
        MqSendDemo.sendMessage();
    }
}