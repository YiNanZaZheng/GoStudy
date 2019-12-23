package com.ztx.spring.Demo5.dao;

import com.ztx.spring.Demo5.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;


public class StudentJDBCTemplate implements StudentDao {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;
    private PlatformTransactionManager transactionManager;
    @Autowired
    private Student student;

    public StudentJDBCTemplate(Student student) {
        this.student=student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource=dataSource;
        jdbcTemplateObject = new JdbcTemplate(this.dataSource);
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public void create(String name, Integer age, Integer marks, Integer year) {
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(definition);
        try {
            System.out.println("create 方法执行11");
        } catch (Exception e) {
            new RuntimeException(e);
        }
    }

    @Override
    public List<Student> listStudents() {
        Student student = new Student();
        List<Student> list = new ArrayList<Student>();
        list.add(student);
        return list;
    }

}
