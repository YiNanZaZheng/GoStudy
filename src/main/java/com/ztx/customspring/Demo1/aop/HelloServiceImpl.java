package com.ztx.customspring.Demo1.aop;


public class HelloServiceImpl implements HelloService {
    @Override
    public void sayHelloWorld() {
        System.out.println("Hello World!");
    }
}
