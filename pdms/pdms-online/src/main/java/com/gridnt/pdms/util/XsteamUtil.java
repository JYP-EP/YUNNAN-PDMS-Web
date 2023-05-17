package com.gridnt.pdms.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;

public class XsteamUtil {

    public static Object toBean(Class<?> clazz, String xml) {
        Object xmlObject = null;
        XStream xstream = new XStream();
        xstream.addPermission(AnyTypePermission.ANY);
        xstream.processAnnotations(clazz);
        xstream.autodetectAnnotations(true);
        xstream.ignoreUnknownElements();
        xmlObject= xstream.fromXML(xml);
        return xmlObject;
    }
}
