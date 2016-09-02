package com.erp.chart.factory;

import com.erp.chart.bean.ChartBean;
import com.erp.enums.ResourceXmlEnum;
import com.erp.util.XmlUtil;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by wang_ on 2016-08-13.
 */
public class ChartBeanFactory {
    private static Logger logger = Logger.getLogger(ChartBeanFactory.class);
    private static String fileSource;
    private static ChartBeanFactory instance;
    private static Map<String, ChartBean> chartBeanMap = new HashMap<>();

    public ChartBeanFactory() {
        this.build();
    }

    public static ChartBeanFactory getInstance() {
        if (instance == null) {
            instance = new ChartBeanFactory();
        }
        return instance;
    }

    public static void init() {
        init(ResourceXmlEnum.path_chart.getValue());
    }

    public static void init(String file) {
        fileSource = file;
        getInstance();
    }

    public void build() {
        Document doc = null;
        try {
            logger.info("--------The chart bean begin init---------");
            doc = XmlUtil.getDocument(fileSource);
            Element root = doc.getRootElement();
            List tb_charts = root.elements("tb-chart");
            if (tb_charts != null) {
                for (Object o : tb_charts) {
                    Element element = (Element) o;
                    String module_lx = element.attributeValue("name");
                    List charts = element.elements("chart");
                    if (charts != null) {
                        loadChartProperties(module_lx, charts);
                    }
                }
            }
            logger.info("--------The chart bean init success---------");
        } catch (Exception e) {
            logger.error("--------The chart bean init failure:" +e.getMessage()+ "---------");
            e.printStackTrace();
        }

    }

    /**
     *
     * @param module_lx
     * @param charts
     */
    private void loadChartProperties(String module_lx, List charts) {
        for (Object o : charts) {
            Element element = (Element) o;
            String chart_lx = element.attributeValue("name");
            Properties properties = loadProperties(element);
            ChartBean chartBean = new ChartBean(properties);
            StringBuffer key = new StringBuffer();
            key.append(module_lx).append("_").append(chart_lx);
            logger.info(key + "=" + chartBean.toString());
            chartBeanMap.put(key.toString(), chartBean);
        }
    }

    /**
     *
     * @param root
     * @return
     */
    private Properties loadProperties(Element root) {
        Properties p = new Properties();
        List elms = root.elements("property");
        if (elms != null) {
            for (Object oo : elms) {
                Element pp = (Element) oo;
                p.setProperty(pp.attributeValue("name"), pp.getText().trim());
            }
        }
        return p;
    }

    /**
     *
     * @param key
     * @return
     */
    public static ChartBean getChartBean(String key) {
        return chartBeanMap.get(key);
    }

}
