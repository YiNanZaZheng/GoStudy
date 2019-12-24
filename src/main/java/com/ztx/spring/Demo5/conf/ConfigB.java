package com.ztx.spring.Demo5.conf;

import com.ztx.spring.Demo5.entity.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

@Configuration
@Import(ConfigA.class)
public class ConfigB {
    @Bean(initMethod = "",destroyMethod = "")
    @Scope("prototype")
    public Student student(){
        return new Student();
    }
}
