package com.ztx.customspring.Demo2.ioc.factory;

public interface BeanFactory {
    Object getBean(String beanId) throws Exception;
}
