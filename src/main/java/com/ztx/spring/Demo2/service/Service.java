package com.ztx.spring.Demo2.service;

import com.ztx.spring.Demo2.dao.StudentJDBCTemplate;
import com.ztx.spring.Demo2.entity.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/*
*   编程式事务管理
 */
public class Service {
    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("spring/Demo2/application.xml");
        StudentJDBCTemplate studentJDBCTemplate =
                (StudentJDBCTemplate)context.getBean("studentJDBCTemplate");
        System.out.println("------Records creation--------" );
        studentJDBCTemplate.create("Zara", 11, 99, 2010);
        studentJDBCTemplate.create("Nuha", 20, 97, 2010);
        studentJDBCTemplate.create("Ayan", 25, 100, 2011);
        System.out.println("------Listing all the records--------" );
        List<Student> studentMarks = studentJDBCTemplate.listStudent();
        for (Student record : studentMarks) {
            System.out.print("ID : " + record.getId() );
            System.out.print(", Name : " + record.getName() );
            System.out.print(", Marks : " + record.getMarks());
            System.out.print(", Year : " + record.getYears());
            System.out.println(", Age : " + record.getAge());
        }
    }
}
