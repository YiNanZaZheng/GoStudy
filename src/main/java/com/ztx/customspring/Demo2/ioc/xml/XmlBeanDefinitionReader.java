package com.ztx.customspring.Demo2.ioc.xml;

import com.ztx.customspring.Demo2.ioc.BeanDefinition;
import com.ztx.customspring.Demo2.ioc.BeanReference;
import com.ztx.customspring.Demo2.ioc.PropertyValue;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class XmlBeanDefinitionReader {

    private Map<String,BeanDefinition> registry;

    public XmlBeanDefinitionReader() {
        registry =new HashMap<>();
    }

    //读取文件生成root节点，然后交给后续代码处理
    public void loadBeanDefinitions(String location) throws Exception{
        FileInputStream inputStream = new FileInputStream(location);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        Document doc = docBuilder.parse(inputStream);
        Element root = doc.getDocumentElement();
        parseBeanDefinitions(root);
    }

    //解析root节点,将root的子节点通过遍历的方式交给后续方法处理
    private void parseBeanDefinitions(Element root) throws Exception {
        NodeList childNodes = root.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);
            if (node instanceof Element) {
                Element element = (Element) node;
                parseBeanDefinition(element);
            }
        }
    }
    //逐个解析root下的子节点
    private void parseBeanDefinition(Element node) throws Exception {
        String id = node.getAttribute("id");
        String classPath = node.getAttribute("class");
        BeanDefinition beanDefinition = new BeanDefinition();
        beanDefinition.setBeanClassName(classPath);
        //modify 2019年12月27日15:28:52
        beanDefinition.setBeanClass(Class.forName(classPath));
        //modify --end
        processProperty(node,beanDefinition);
        registry.put(id,beanDefinition);
    }

    private void processProperty(Element ele,BeanDefinition beanDefinition) {
        NodeList propertyNodes = ele.getElementsByTagName("property");
        for (int i = 0; i < propertyNodes.getLength(); i++) {
            Node propertyNode = propertyNodes.item(i);
            if (propertyNode instanceof Element) {
                Element property = (Element) propertyNode;
                String name = property.getAttribute("name");
                String value = property.getAttribute("value");
                if (value != null && value.length() > 0) {
                    beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name,value));
                }else {
                    String ref = property.getAttribute("ref");
                    if (ref != null && ref.length() > 0) {
                        throw new IllegalArgumentException("ref config error");
                    }
                    BeanReference reference = new BeanReference(ref);
                    beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name,reference));
                }
            }
        }
    }

    public Map<String,BeanDefinition> getRegistry(){
        return registry;
    }
}
