package com.gridnt.pdms.util;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

public class XMLUtil {
    public static String convertToXml(Object obj){
        StringWriter stringWriter = new StringWriter();
        try{
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(obj, stringWriter);
        }catch (JAXBException e){
            e.printStackTrace();
        }
        return stringWriter.toString();
    }

    public static Object convertXmlStrToObject(Class clazz, String xmlStr){
        Object obj = null;
        try{
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader sr = new StringReader(xmlStr);
            obj = unmarshaller.unmarshal(sr);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return obj;
    }
}