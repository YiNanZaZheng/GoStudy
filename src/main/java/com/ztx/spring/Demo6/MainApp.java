package com.ztx.spring.Demo6;

import com.ztx.spring.Demo6.event.CustomEventPublisher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        ConfigurableApplicationContext context=
                new ClassPathXmlApplicationContext("spring/Demo6/application.xml");
        /*context.stop();
        context.start();
        context.refresh();//重新加载bean
        HelloWorld bean = context.getBean(HelloWorld.class);
        bean.print();
        context.close();*/

        CustomEventPublisher bean = context.getBean(CustomEventPublisher.class);
        bean.publish();

    }
}
