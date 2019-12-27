package com.ztx.customspring.Demo1.aop;

public class SimpleAOPTest {

    public static void main(String[] args) {
        // 1.创建一个MethodInvocation实现类
        MethedInvacation logTask=new MethedInvacation() {
            @Override
            public void invoke() {
                System.out.println("log task start");
            }
        };
        HelloServiceImpl service = new HelloServiceImpl();

        // 2.创建一个Advice
        Advice beforAdvice = new BeforAdvice(service, logTask);

        // 3.为目标对象生成代理
        HelloService helloProxy = (HelloService) SimpleAOP.getProxy(service, beforAdvice);

        helloProxy.sayHelloWorld();
        helloProxy.sayHelloWorld();
    }
}
