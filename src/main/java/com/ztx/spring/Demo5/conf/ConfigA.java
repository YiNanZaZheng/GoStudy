package com.ztx.spring.Demo5.conf;

import com.ztx.spring.Demo5.dao.StudentJDBCTemplate;
import com.ztx.spring.Demo5.entity.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigA {
    @Bean
    public StudentJDBCTemplate studentJDBCTemplate(){
        return new StudentJDBCTemplate();
    }
}
