package com.ztx.customspring.Demo2.aop;

import com.ztx.customspring.Demo2.ioc.BeanPostProcessor;
import com.ztx.customspring.Demo2.ioc.factory.BeanFactory;
import com.ztx.customspring.Demo2.ioc.factory.BeanFactoryAware;
import com.ztx.customspring.Demo2.ioc.xml.XMLBeanFactory;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.aspectj.AspectJPointcutAdvisor;

import java.util.List;

public class AspectJAwareAdvisorAutoProxyCreator implements BeanPostProcessor, BeanFactoryAware {
    private XMLBeanFactory xmlBeanFactory;


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws Exception {
        if (bean instanceof AspectJepressionPointcutAdvisor) {
            return bean;
        }

        if (bean instanceof MethodInterceptor) {
            return bean;
        }
        List<AspectJepressionPointcutAdvisor> advisors = xmlBeanFactory.getBeanForType(AspectJPointcutAdvisor.class);
        for (AspectJepressionPointcutAdvisor advisor : advisors) {
            //判断类是否符合切面表达式
            if (advisor.getPointcut().getClassFilter().matchers(bean.getClass())) {
                ProxyFactory advisedSupport = new ProxyFactory();
                advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
                advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
                TargetSource targetSource = new TargetSource(bean.getClass(), bean.getClass().getInterfaces(), bean);
                advisedSupport.setTargetSource(targetSource);
                return advisedSupport.getProxy();
            }
    }
        return bean;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws Exception {
        xmlBeanFactory=(XMLBeanFactory)beanFactory;
    }
}
