package com.crscd.passengerservice.display.format.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Created by cuishiqing on 2017/8/30.
 */
public class JaxbUtil {

    /**
     * 'Bean转XML'
     *
     * @param obj
     * @param load
     * @return
     * @throws JAXBException
     */
    public static String toXml(Object obj, Class<?> load) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(load);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); //是否格式化生成的XML串
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "gb2312");  //编码方式
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);//是否省略XML头信息
        StringWriter writer = new StringWriter();
        marshaller.marshal(obj, writer);
        return writer.toString().replace(" standalone=\"yes\"", "");//去掉生成XML中的 standalone设置项
    }

    /**
     * 'XML转Bean'
     *
     * @param xml
     * @param c
     * @param <T>
     * @return
     */
    public static <T> T converyToJavaBean(String xml, Class<T> c) {
        T t = null;
        try {
            JAXBContext context = JAXBContext.newInstance(c);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            t = (T) unmarshaller.unmarshal(new StringReader(xml));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

}
