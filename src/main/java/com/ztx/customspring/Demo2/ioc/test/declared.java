package com.ztx.customspring.Demo2.ioc.test;

import org.omg.PortableInterceptor.INACTIVE;
import sun.java2d.d3d.D3DDrawImage;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class declared {
    public static void main(String[] args) throws Exception{
        Class<?> Clazz = Class.forName("com.ztx.customspring.Demo2.ioc.test.User");
        Object user = Clazz.newInstance();
        Job job = new Job();
        //基础类型数据赋值
        Method declaredMethod = Clazz.getDeclaredMethod("setName", String.class);
        declaredMethod.setAccessible(true);
        Object[] a={"dsdf"};
        declaredMethod.invoke(user,a);
        Method declaredMethod1 = Clazz.getDeclaredMethod("setJob", Job.class);
        //引用类型赋值
        declaredMethod1.setAccessible(true);
        Object[] b={job};
        declaredMethod1.invoke(user,b);
        System.out.println(user);
    }
}

class User{
    private String name;
    private Integer age;
    private Job job;

    public void setJob(Job job) {
        this.job = job;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", job=" + job +
                '}';
    }
}
class Job{
    private String name;
    private String desc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "Job{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}