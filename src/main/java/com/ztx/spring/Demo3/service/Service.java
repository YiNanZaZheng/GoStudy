package com.ztx.spring.Demo3.service;

import com.ztx.spring.Demo3.dao.StudentDao;
import com.ztx.spring.Demo3.dao.StudentJDBCTemplate;
import com.ztx.spring.Demo3.entity.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/*
*   声明式事务管理
 */
public class Service {
    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("spring/Demo3/application.xml");
        StudentDao studentJDBCTemplate =
                (StudentDao)context.getBean("studentJDBCTemplate");
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
