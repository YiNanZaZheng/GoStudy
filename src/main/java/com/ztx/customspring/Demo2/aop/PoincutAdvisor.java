package com.ztx.customspring.Demo2.aop;

public interface PoincutAdvisor extends Advisor {
    Pointcut getPointcut();
}
