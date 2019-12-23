package com.ztx.spring.Demo3.dao;

import com.ztx.spring.Demo3.entity.Student;
import com.ztx.spring.Demo3.mapper.StudentMarksMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class StudentJDBCTemplate implements StudentDao {

    private JdbcTemplate jdbcTemplateObject;
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    @Override
    public void create(String name, Integer age, Integer marks, Integer year) {
        try {
            String SQL1 = "insert into Student (name, age) values (?, ?)";
            jdbcTemplateObject.update(SQL1, name, age);
            // Get the latest student id to be used in Marks table
            String SQL2 = "select max(id) from Student";
            int sid = jdbcTemplateObject.queryForObject(SQL2,Integer.class);
            String SQL3 = "insert into Marks(sid, marks, year) " +
                    "values(?, ?, ?)";
            jdbcTemplateObject.update(SQL3, sid, marks, year);
            System.out.println("Created Name = " + name + ", Age = " + age);
        } catch (DataAccessException e) {
            System.out.println("Error in creating record,rolling back");
            throw e;
        }
        return;
    }

    @Override
    public List<Student> listStudent() {
        String SQL4="select * from Student, Marks where Student.id=Marks.sid";
        //List<Student> students = jdbcTemplateObject.query(SQL4, new StudentMarksMapper());
        List<Student> students = jdbcTemplateObject.query(SQL4, new StudentMarksMapper());
        return students;
    }
}
