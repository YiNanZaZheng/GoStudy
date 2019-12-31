package com.ztx.customspring.Demo2.aop;

public class ProxyFactory extends AdvisedSupport implements AopProxy{

    @Override
    public Object getProxy() {
        return createProxy().getProxy();
    }

    public JdkDynamicAopProxy createProxy() {
        return new JdkDynamicAopProxy(this);
    }
}
