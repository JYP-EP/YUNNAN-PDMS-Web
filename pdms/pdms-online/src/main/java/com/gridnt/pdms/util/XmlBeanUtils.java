package com.gridnt.pdms.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

public class XmlBeanUtils {

    /**
     * Java Bean 转 Xml
     *
     * @param bean         - Java Bean
     * @param inheritClazz - Java Bean中嵌套的类，且有继承关系的Java Class
     * @return - xml
     */
    public static String beanToXml(Object bean, Class<?>... inheritClazz) {
        try {
            JAXBContext context = initContext(bean.getClass(), inheritClazz);
            Marshaller marshaller = context.createMarshaller();
            // 格式化xml
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            // 是否去掉xml头
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);
            StringWriter writer = new StringWriter();
            marshaller.marshal(bean, writer);
            return writer.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Xml 转 Java Bean
     *
     * @param xml          - xml
     * @param beanClazz    - Java Bean Class
     * @param inheritClazz - Java Bean中嵌套的类，且有继承关系的Java Class
     * @return - bean
     */
    public static Object xmlToBean(String xml, Class<?> beanClazz, Class<?>... inheritClazz) {
        try {
            JAXBContext context = initContext(beanClazz, inheritClazz);
            Unmarshaller um = context.createUnmarshaller();
            StringReader sr = new StringReader(xml);
            return um.unmarshal(sr);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 初始化JAXBContext
     *
     * @param mainClazz    - 序列化或反序列化Class
     * @param inheritClazz - Java Bean中嵌套的类，且有继承关系的Java Class
     * @return - JAXBContext
     */
    private static JAXBContext initContext(Class<?> mainClazz, Class<?>... inheritClazz) throws JAXBException {
        JAXBContext context;
        if (inheritClazz != null) {
            Class<?>[] clazzArr = new Class[inheritClazz.length + 1];
            clazzArr[0] = mainClazz;
            System.arraycopy(inheritClazz, 0, clazzArr, 1, clazzArr.length - 1);
            context = JAXBContext.newInstance(clazzArr);
        } else {
            context = JAXBContext.newInstance(mainClazz);
        }
        return context;
    }

}
