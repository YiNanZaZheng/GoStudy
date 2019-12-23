package spring.Demo1;

import com.ztx.spring.Demo1.service.UserService;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServiceTest {
    @Test
    public void UserServiceTest(){
        ClassPathXmlApplicationContext apContext =
                new ClassPathXmlApplicationContext("spring.Demo1/application.xml");
        UserService userService = apContext.getBean(UserService.class, "userService");
        userService.loginUser();
    }
}
