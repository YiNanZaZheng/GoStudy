package com.ztx.spring.Demo9;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {
    static Logger log = Logger.getLogger(MainApp.class.getName());
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("spring/Demo9/application.xml");
        HelloWorld bean = context.getBean(HelloWorld.class);
        log.info("Going to create HelloWorld Obj");
        bean.getMessage();
        log.info("Exiting the propram");
    }
}
