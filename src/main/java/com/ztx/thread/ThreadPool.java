package com.ztx.thread;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadPool {
    private static ExecutorService pool;
    private static ExecutorService pool1;

    public static void main(String[] args) {
        pool = new ThreadPoolExecutor(6, 10, 1000,
                TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(10), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                System.out.println("线程pool1"+r.hashCode()+"创建完成");
                Thread th=new Thread(r,"threadPool1");
                return th;
            }
        },
                (a, b) -> System.out.println(a.toString() + "执行了拒绝策略啊"));


        pool1 = new ThreadPoolExecutor(6, 12, 1000,
                TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(10),new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                System.out.println("线程pool2"+r.hashCode()+"创建完成");
                Thread th=new Thread(r,"threadPool2");
                return th;
            }
        },new ThreadPoolExecutor.AbortPolicy()){
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                System.out.println("准备执行："+((ThreadTask1)r).getPriority());
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                System.out.println("执行完毕："+((ThreadTask1)r).getPriority());
            }

            @Override
            protected void terminated() {
                System.out.println("线程池退出");
            }
        };
        StartTestDemo1 testDemo1 = new StartTestDemo1();
        for (int i = 0; i < 1; i++) {
            pool.execute(new ThreadTask2(i,testDemo1));
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 2; i++) {
            pool1.execute(new ThreadTask1(i,testDemo1));
        }
        pool.shutdown();
        pool1.shutdown();
    }
}

class ThreadTask1 implements Runnable,Comparable<ThreadTask1>{
    private int priority;
    private StartTestDemo1 demo1;

    public StartTestDemo1 getDemo1() {
        return demo1;
    }

    public void setDemo1(StartTestDemo1 demo1) {
        this.demo1 = demo1;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public ThreadTask1() {

    }

    public ThreadTask1(int priority) {
        this.priority = priority;
    }

    public ThreadTask1(int priority, StartTestDemo1 demo1) {
        this.priority = priority;
        this.demo1 = demo1;
    }

    @Override
    public int compareTo(ThreadTask1 o) {
        return this.priority>o.priority?-1:1;
    }

    @Override
    public void run() {
        //StartTestDemo1.startB();
        //StartTestDemo1 demo1 = new StartTestDemo1();
        demo1.startD();
        //demo1.startA();
        //demo1.startC();
    }
}

class ThreadTask2 implements Runnable,Comparable<ThreadTask2>{
    private int priority;
    private StartTestDemo1 demo1;

    public StartTestDemo1 getDemo1() {
        return demo1;
    }

    public void setDemo1(StartTestDemo1 demo1) {
        this.demo1 = demo1;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {

        this.priority = priority;
    }

    public ThreadTask2() {

    }

    public ThreadTask2(int priority) {
        this.priority = priority;
    }

    public ThreadTask2(int priority, StartTestDemo1 demo1) {
        this.priority = priority;
        this.demo1 = demo1;
    }

    @Override
    public int compareTo(ThreadTask2 o) {
        return this.priority>o.priority?-1:1;
    }

    @Override
    public void run() {
        //StartTestDemo1.startB();
        //StartTestDemo1 demo1 = new StartTestDemo1();
        demo1.startA();
        //demo1.startC();
    }
}
class customRejectedExecutionHandler implements RejectedExecutionHandler{
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.println(r.toString()+"执行了拒绝策略");
    }
}

class StartTestDemo1{
    static long ms=10000;
    public synchronized void startA(){
        System.out.println();
        System.out.println("同步非静态方法1执行"+new Date().getTime()+Thread.currentThread().getName());
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("同步非静态方法1执行完成"+new Date().getTime()+Thread.currentThread().getName());
    }

    public synchronized void startD(){
        System.out.println("同步非静态方法2执行"+new Date().getTime()+Thread.currentThread().getName());
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("同步非静态方法2执行完成"+new Date().getTime()+Thread.currentThread().getName());
    }

    public static synchronized void startB() {
        System.out.println("同步静态方法执行"+new Date().getTime()+Thread.currentThread().getName());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("同步静态方法执行完成"+new Date().getTime()+Thread.currentThread().getName());
    }

    public void startC(){
        System.out.println("非同步静态方法执行"+new Date().getTime()+Thread.currentThread().getName());
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("非同步静态方法执行完成"+new Date().getTime()+Thread.currentThread().getName());
    }

}