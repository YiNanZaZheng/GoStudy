package com.ztx.spring.Demo5.mapper;

import com.ztx.spring.Demo5.entity.Student;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentMarksMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        Student studentMarks=new Student();
        studentMarks.setName(rs.getString("name"));
        studentMarks.setAge(rs.getInt("age"));
        studentMarks.setId(rs.getInt("id"));
        studentMarks.setMarks(rs.getInt("marks"));
        studentMarks.setSid(rs.getInt("sid"));
        studentMarks.setYears(rs.getInt("year"));
        return studentMarks;
    }
}
