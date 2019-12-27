package com.ztx.customspring.Demo1.ioc;

public class SimpleIOCTest {
    public static void main(String[] args) throws Exception{
        SimpleIOC ioc = new SimpleIOC("E:\\GoStudy\\src\\main\\resource\\customspring\\Demo1\\application.xml");
        Car car = (Car) ioc.getBean("car");
        System.out.println(car.getName());
    }
}
