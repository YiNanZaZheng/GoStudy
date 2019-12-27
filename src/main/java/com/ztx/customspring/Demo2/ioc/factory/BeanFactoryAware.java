package com.ztx.customspring.Demo2.ioc.factory;

public interface BeanFactoryAware {
    void setBeanFactory(BeanFactory beanFactory) throws Exception;
}
