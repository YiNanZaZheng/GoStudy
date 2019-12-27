package com.ztx.customspring.Demo2.ioc;

public interface BeanPostProcessor {
    Object postProcessBeforeInitialization(Object bean,String beanName) throws Exception;
    Object postProcessAfterInitialization(Object bean,String beanName) throws Exception;
}
