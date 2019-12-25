package com.ztx.spring.Demo2.dao;

import com.ztx.spring.Demo2.entity.Student;
import com.ztx.spring.Demo2.mapper.StudentMarksMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class StudentJDBCTemplate implements StudentDao {

    private DataSource dataSource;
    private PlatformTransactionManager transactionManager;
    private JdbcTemplate jdbcTemplateObject;
    @Autowired
    public StudentJDBCTemplate(DataSource dataSource, PlatformTransactionManager transactionManager) {
        this.dataSource=dataSource;
        this.jdbcTemplateObject=new JdbcTemplate(this.dataSource);
        this.transactionManager = transactionManager;
    }

    @Override
    public void create(String name, Integer age, Integer marks, Integer year) {
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setIsolationLevel(3);
        TransactionStatus status = transactionManager.getTransaction(definition);

        try {
            String SQL1 = "insert into Student (name, age) values (?, ?)";
            jdbcTemplateObject.update(SQL1, name, age);
            // Get the latest student id to be used in Marks table
            String SQL2 = "select max(id) from Student";
            int sid = jdbcTemplateObject.queryForObject(SQL2,Integer.class);
            String SQL3 = "insert into Marks(sid, marks, year) " +
                    "values (?, ?, ?)";
            jdbcTemplateObject.update(SQL3, sid, marks, year);
            System.out.println("Created Name = " + name + ", Age = " + age);
            transactionManager.commit(status);
        } catch (DataAccessException e) {
            System.out.println("Error in creating record,rolling back");
            transactionManager.rollback(status);
            throw e;
        }
        return;
    }

    @Override
    public List<Student> listStudent() {
        String SQL4="select * from Student, Marks where Student.id=Marks.sid";
        List<Student> students = jdbcTemplateObject.query(SQL4, new StudentMarksMapper());
        return students;
    }
}
