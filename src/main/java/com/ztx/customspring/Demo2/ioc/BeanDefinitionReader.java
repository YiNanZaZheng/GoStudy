package com.ztx.customspring.Demo2.ioc;

import java.io.FileNotFoundException;

public interface BeanDefinitionReader {
    void loadBeanDefinitions(String location) throws Exception, FileNotFoundException;
}
