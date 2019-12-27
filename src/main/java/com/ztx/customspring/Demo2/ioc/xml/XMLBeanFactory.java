package com.ztx.customspring.Demo2.ioc.xml;

import com.ztx.customspring.Demo2.ioc.BeanDefinition;
import com.ztx.customspring.Demo2.ioc.BeanPostProcessor;
import com.ztx.customspring.Demo2.ioc.BeanReference;
import com.ztx.customspring.Demo2.ioc.PropertyValue;
import com.ztx.customspring.Demo2.ioc.factory.BeanFactory;
import com.ztx.customspring.Demo2.ioc.factory.BeanFactoryAware;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class XMLBeanFactory implements BeanFactory {

    private XmlBeanDefinitionReader beanDefinitionReader;

    private Map<String,BeanDefinition> beanDefinitionMap=new HashMap<>();

    private List<String> beanDefinitionNames = new ArrayList<>();

    private List<BeanPostProcessor> beanPostProcessors=new ArrayList<>();

    public XMLBeanFactory(String location) throws Exception{
        beanDefinitionReader = new XmlBeanDefinitionReader();
        loadBeanDefinitions(location);
    }

    @Override
    public Object getBean(String beanId) throws Exception {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanId);
        if (beanDefinition == null) {
            throw new IllegalArgumentException("no this bean with name" + beanId);
        }
        Object bean = beanDefinition.getBean();
        if (bean == null) {
            bean = createBean(beanDefinition);
            bean = initializeBean(bean, beanId);
            beanDefinition.setBean(bean);
        }
        return bean;
    }

    private void loadBeanDefinitions(String location) throws Exception {
        //在此处将XML交给XmlBeanDefinitionReader处理
        beanDefinitionReader.loadBeanDefinitions(location);
        //将处理过的xml通过BeanDefinition放到beanDefinitionMap中。
        registryBeanDefinition();
        registerBeanPostProcessor();
    }

    //将解析完的xml放到benDefinitionMap中。
    private void registryBeanDefinition(){
        for (Map.Entry<String, BeanDefinition> entry :beanDefinitionReader.getRegistry().entrySet()) {
            String id = entry.getKey();
            BeanDefinition beanDefinition = entry.getValue();
            beanDefinitionMap.put(id, beanDefinition);
            beanDefinitionNames.add(id);
        }
    }

    private void registerBeanPostProcessor() throws Exception {
        List beans =getBeanForType(BeanPostProcessor.class);;
        for (Object bean : beans) {
            addBeanPostProcessor((BeanPostProcessor) bean);
        }
    }

    //此处getBean("id")方法还未补充
    public List getBeanForType(Class type) throws Exception{
        //存放所有实现了BeanPostProcessor接口的类的实例
        List beans = new ArrayList<>();
        for (String beanDefinitionName : beanDefinitionNames) {
            if (type.isAssignableFrom(beanDefinitionMap.get(beanDefinitionName).getBeanClass())){
                beans.add(getBean(beanDefinitionName));
            }
        }
        return beans;
    }

    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        beanPostProcessors.add(beanPostProcessor);
    }

    //创建Bean的实例，并且注入属性
    private Object createBean(BeanDefinition beanDefinition) throws Exception{
        Object bean=beanDefinition.getBeanClass().newInstance();
        applyPropertyValues(bean, beanDefinition);
        return bean;
    }

    private void applyPropertyValues(Object bean, BeanDefinition beanDefinition) throws Exception{

        if (bean instanceof BeanFactoryAware) {
            ((BeanFactoryAware) bean).setBeanFactory(this);
        }

        List<PropertyValue> propertyValues = beanDefinition.getPropertyValues().getPropertyValues();
        for (PropertyValue propertyValue : propertyValues) {
            String name = propertyValue.getName();
            Object value = propertyValue.getValue();
            if (value instanceof BeanReference) {
                BeanReference reference = (BeanReference) value;
                value = getBean(reference.getName());
            }
            try {
                /*
                 *   改天写代码测试测试一下！
                 */
                Method declaredMethod = bean.getClass().getDeclaredMethod("set" + name.substring(0, 1).toUpperCase()
                        + name.substring(1), value.getClass());
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(bean,value);
            }catch (NoSuchMethodException e){
                Field declaredField = bean.getClass().getDeclaredField(name);
                declaredField.setAccessible(true);
                declaredField.set(bean,value);
            }
        }
    }
     /*
      *      此方法的具体细节需要后续研究
      */
    private Object initializeBean(Object bean, String name) throws Exception {
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            bean = beanPostProcessor.postProcessBeforeInitialization(bean, name);
        }
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            bean = beanPostProcessor.postProcessAfterInitialization(bean, name);
        }
        return bean;
    }

}
