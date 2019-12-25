package com.ztx.spring.Demo8;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/Demo8/application.xml");
        User bean = context.getBean(User.class);
        bean.setNameAge("张三",300);
        System.out.println(bean.getAge());
    }
}
