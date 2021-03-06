package com.ztx.spring.Demo2.dao;

import com.ztx.spring.Demo2.entity.Student;

import javax.sql.DataSource;
import java.util.List;

public interface StudentDao {

    //创建学生信息
    void create(String name, Integer age, Integer marks, Integer year);
    //查询所有学生信息
    List<Student> listStudent();
}
