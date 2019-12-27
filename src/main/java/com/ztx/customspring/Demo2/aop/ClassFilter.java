package com.ztx.customspring.Demo2.aop;

public interface ClassFilter {
    Boolean matchers(Class beanClass) throws Exception;
}
