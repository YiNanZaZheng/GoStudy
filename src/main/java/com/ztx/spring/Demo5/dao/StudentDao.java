package com.ztx.spring.Demo5.dao;

import com.ztx.spring.Demo5.entity.Student;

import java.util.List;

public interface StudentDao {
    void create(String name, Integer age, Integer marks, Integer year);
    List<Student> listStudents();
}
