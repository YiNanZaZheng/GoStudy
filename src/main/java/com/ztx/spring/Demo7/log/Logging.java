package com.ztx.spring.Demo7.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

@Component
public class Logging {
    public void beforeAdvice(){
        System.out.println("Going to setup student profile");
    }
    public void beforeAdvice1(String name,Integer age){
        System.out.println("Going to setup student profile;   name:"+name+"  ===== age:"+age);
    }
    public void afterAdvice(){
        System.out.println("Student profile has been setup");
    }

    public void afterReturningAdvice(JoinPoint joinpoint, Object result) {
        System.out.println("Returning：" +result);
    }

    public void afterThrowingAdvice(IllegalArgumentException e) {
        System.out.println("There has been an exception:"+e.toString());
    }

    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i <args.length; i++) {
            builder.append(args[i]);
        }
        System.out.println("Around before///methed request【"+builder+"】");
        Object obj = joinPoint.proceed(joinPoint.getArgs());
        System.out.println("Around after ///methed responce【"+obj+"】");
        return obj;
    }
}
