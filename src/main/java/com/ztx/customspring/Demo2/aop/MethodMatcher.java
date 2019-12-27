package com.ztx.customspring.Demo2.aop;

import java.lang.reflect.Method;

public interface MethodMatcher {
    Boolean matchers(Method method, Class beanClass);
}
