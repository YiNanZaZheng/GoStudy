package com.ztx.study.start;

import com.ztx.study.serviceimpl.ClculatorImpl;
import com.ztx.study.tools.ZkTools;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MainApp {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName()+"系统启动");
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"add服务启动");
            new ClculatorImpl().start();
        }).start();
        System.out.println("..............XXX服务拓展中....................");
    }
}
