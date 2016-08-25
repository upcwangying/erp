package com.erp.util;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import java.io.*;

public class XmlUtil {
    public static Logger logger = Logger.getLogger(XmlUtil.class);

    public static Document getDocument(String filename) throws Exception {
        return getDocument(new File(filename));
    }

    public static Document getDocument(File file) throws Exception {
        SAXReader reader = new SAXReader();
        return reader.read(file);
    }

}
