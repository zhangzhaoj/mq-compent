package cn.jj.amqp.client;

import cn.jj.amqp.common.util.AppContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author by wanghui03
 * @Classname AppControllerAdvice
 * @Description MQ demo项目启动类
 * @Date 2021/11/30 09:50
 */
@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"cn.jj.amqp"})
public class MqClientApplication {

	public static void main(String[] args) {

		SpringApplication app = new SpringApplication(MqClientApplication.class);
		ConfigurableApplicationContext ctx = app.run(args);
		AppContext.setApplicationContext(ctx);
	}

}
