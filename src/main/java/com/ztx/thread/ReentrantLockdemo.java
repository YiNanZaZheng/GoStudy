package com.ztx.thread;

import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockdemo {

    static ExecutorService pool=new ThreadPoolExecutor(50,50,1000,
            TimeUnit.SECONDS,new ArrayBlockingQueue<>(10), Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) {
        /*for (int i = 0; i < 2; i++) {
            pool.execute(new ThreadTask3());
        }*/
        pool.execute(new ThreadTask3());
        /*try {
            TimeUnit.MILLISECONDS.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        System.out.println("执行新任务");
        pool.execute(new ThreadTask4());
        pool.shutdown();
    }

}
class ThreadTask3 implements Runnable{
    @Override
    public void run() {
        new Test1().startB1();
        //new Test1().startB2();
    }
}
class ThreadTask4 implements Runnable{
    @Override
    public void run() {
        //new Test1().startB1();
        new Test1().startB2();
    }
}
class Test1{
    static Lock lock=new ReentrantLock(true);//通过构造方法实现公平锁
    static Lock lock3=new ReentrantLock();
    //验证可重入和公平锁
    public void startA(){
        try {
            for (int i = 0; i < 2; i++) {
                System.out.println(Thread.currentThread().getName()+"到达加锁前");
                lock.lock();
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName()+"doSomething");
                lock.unlock();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //lock.unlock();
        }
    }

    //验证Reentrantlock的相应中断,StartB1方法先获取Lock1->Lock2;StartB2方法先获取Lock2->Lock1
    static Lock lock1=new ReentrantLock();
    static Lock lock2=new ReentrantLock();

    public void startB1(){
        try {
            lock1.lock();
            System.out.println(Thread.currentThread().getName() + "已经取得Lock1,准备获取lock2");
            Thread.sleep(2000);
            lock2.lock();
            System.out.println(Thread.currentThread().getName()+"doSomething");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock2.unlock();
            lock1.unlock();
            System.out.println(Thread.currentThread().getName()+"任务执行完成，释放线程");
        }
    }
    public void startB2(){
        try {
            lock2.lock();
            System.out.println(Thread.currentThread().getName() + "已经取得Lock2,准备获取lock1");
            Thread.sleep(2000);
            if (!lock1.tryLock()){
                Thread thread = Thread.currentThread();
                thread.interrupt();
                System.out.println("产生死锁，中断当前线程完成");
            }else {
                lock1.lock();
                System.out.println(Thread.currentThread().getName()+"doSomething");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock2.unlock(); //中断线程后执行解锁语句
        }
    }

}
