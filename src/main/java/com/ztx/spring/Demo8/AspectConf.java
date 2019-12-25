package com.ztx.spring.Demo8;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AspectConf {
    @Pointcut("execution(* *..Demo8..set*(..))")
    private void selectHandler(){}

    @Before(value="selectHandler() && args(name,age)", argNames="name,age")
    public void beforeAdvice(String name,Integer age){
        System.out.println("方法前通知执行了"+name+":"+age);
    }

    @Around(value="selectHandler()")
    public Object AroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        StringBuilder builder = new StringBuilder("| ");
        for (int i = 0; i <args.length; i++) {
            builder.append(args[i]).append(" | ");
        }
        System.out.println("Around before///methed request【"+builder+"】");
        Object obj = joinPoint.proceed(joinPoint.getArgs());
        System.out.println("Around after ///methed responce【"+obj+"】");
        return obj;
    }
}
