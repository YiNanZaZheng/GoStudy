package com.ztx.spring.Demo6;

public class HelloWorld {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HelloWorld() {
        System.out.println("HelloWorld初始化");
    }

    public void print(){
        System.out.println("=================="+message+"==================");
    }
}
