package com.ztx.customspring.Demo2.aop;

public abstract class AbstractAopProxy implements AopProxy {
    protected AdvisedSupport advised;

    public AbstractAopProxy(AdvisedSupport advisedSupport) {
        this.advised = advisedSupport;
    }
}
