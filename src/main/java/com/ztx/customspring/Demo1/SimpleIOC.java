package com.ztx.customspring.Demo1;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class SimpleIOC {
    private Map<String, Object> beanMap = new HashMap<>();

    public SimpleIOC(String location) throws Exception {
        loadBeans(location);
    }

    public Object getBean(String name) {
        Object obj = beanMap.get(name);
        if (obj == null) {
            throw  new IllegalArgumentException("there is not bean with name"+name);
        }
        return obj;
    }
    private void loadBeans(String loaction) throws Exception{
        //加载配置文件
        FileInputStream inputStream = new FileInputStream(loaction);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder doBuilder = factory.newDocumentBuilder();
        Document doc = doBuilder.parse(inputStream);
        Element root = doc.getDocumentElement();
        NodeList nodes = root.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node instanceof Element) {
                Element element = (Element) node;
                String id = element.getAttribute("id");
                String className = element.getAttribute("class");

                Class beanClass = null;
                try {
                    beanClass = beanClass.forName(className);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    return;
                }
                Object bean = beanClass.newInstance();

                NodeList propertyNodes = element.getElementsByTagName("property");
                for (int j = 0; j < propertyNodes.getLength(); j++) {
                    Node propertyNode = propertyNodes.item(j);
                    if (propertyNode instanceof Element) {
                        Element property = (Element) propertyNode;
                        String name = property.getAttribute("name");
                        String value = property.getAttribute("value");

                        Field declaredField = bean.getClass().getDeclaredField(name);
                        declaredField.setAccessible(true);
                        if (value != null && value.length() > 0) {
                            declaredField.set(bean, value);
                        } else {
                            String ref = property.getAttribute("ref");
                            if (ref == null || ref.length() == 0) {
                                throw new IllegalArgumentException("ref config error");
                            }
                            declaredField.set(bean,getBean(ref));
                        }
                        registerBean(name,bean);
                    }
                }
            }
        }
    }

    private void registerBean(String id, Object object) {
        beanMap.put(id,object);
    }

}
