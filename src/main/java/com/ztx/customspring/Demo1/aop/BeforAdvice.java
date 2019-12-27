package com.ztx.customspring.Demo1.aop;

import java.lang.reflect.Method;

public class BeforAdvice implements Advice {

    private Object bean;
    private MethedInvacation methodInvocation;

    public BeforAdvice(Object bean, MethedInvacation methodInvocation) {
        this.bean = bean;
        this.methodInvocation = methodInvocation;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        methodInvocation.invoke();
        return method.invoke(bean,args);
    }
}
