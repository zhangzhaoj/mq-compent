package cn.jj.amqp.client.basedemo.send;

import cn.jj.amqp.client.MqClientApplication;
import cn.jj.amqp.common.util.AppContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class MqSendDemoTest {

    @BeforeAll
    public static void before(){
        try{
           //todo 初始化上下文
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Autowired
    private MqSendDemo MqSendDemo;
    @Test
    void sendMessage() {
        MqSendDemo.sendMessage();
    }
}