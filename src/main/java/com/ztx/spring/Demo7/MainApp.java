package com.ztx.spring.Demo7;

import com.ztx.spring.Demo7.entity.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        ApplicationContext context=new ClassPathXmlApplicationContext("spring/Demo7/application.xml");
        Student student = context.getBean(Student.class);
        student.setNameAge("张三",200);
        System.out.println(student.getAge());
        //student.printThrowException();
    }
}
