package com.erp.util;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by wang_ on 2016-09-02.
 */
public class ResourcePathUtil {
    private static Logger logger = Logger.getLogger(ResourcePathUtil.class);

    public static String getResourcePath() {
        return getResourcePath("");
    }

    public static String getResourcePath(String name) {
        if (name == null) {
            return null;
        }
        String resource_root = Thread.currentThread().getContextClassLoader().getResource(name).getFile();
        try {
            resource_root = URLDecoder.decode(resource_root, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            logger.error("decode the resource root failed:" + ex.getMessage(), ex);
            return null;
        }
        if (resource_root != null) {
            resource_root = new File(resource_root).getPath();
        } else {
            logger.error("get resource root error");
            return null;
        }

        return resource_root;
    }

}
