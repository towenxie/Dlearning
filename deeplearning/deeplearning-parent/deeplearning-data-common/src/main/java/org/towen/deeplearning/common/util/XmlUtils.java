package org.towen.deeplearning.common.util;

import java.io.StringReader;

import javax.xml.transform.stream.StreamSource;

import org.springframework.oxm.XmlMappingException;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import java.io.File;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public class XmlUtils {

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String xmlName, Class<T> targetBeanType) {
        String xml;
        try {
            xml = XmlToString(xmlName);
        } catch (DocumentException e1) {
            throw new RuntimeException("XML: " + xmlName, e1);
        }
        Jaxb2Marshaller unmarshaller = new Jaxb2Marshaller();
        unmarshaller.setClassesToBeBound(targetBeanType);
        Object targetBean = null;

        try {
            targetBean = unmarshaller.unmarshal(new StreamSource(new StringReader(xml.trim())));
        } catch (XmlMappingException e) {
            throw new RuntimeException("target class type not match XML: " + xml, e);
        }

        if (targetBeanType.isInstance(targetBean)) {
            return (T) targetBean;
        }

        throw new RuntimeException("target class type not match XML: " + xml);
    }

    private static String XmlToString(String xmlName) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File(xmlName));
        String documentStr = document.asXML();
        return documentStr;
    }
}
