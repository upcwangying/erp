package com.erp.chart.util;

import com.erp.chart.BaseChart;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.servlet.ServletUtilities;

import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by wang_ on 2016-06-29.
 */
public class ChartsUtil {

    //����ͼ
    public String parseChart(BaseChart baseChart, HttpSession session, int w, int h) {

        JFreeChart chart = baseChart.createChart();
        String filename = null;
        /**
         * ͼ�����, Ŀ¼�����ʾ��ǩ, ��ֵ�����ʾ��ǩ, ���ݼ�, ͼ����ˮƽ����ֱ
         * �Ƿ���ʾͼ��(���ڼ򵥵���״ͼ������false), �Ƿ����ɹ���, �Ƿ�����URL����
         */
        try {
            // �õ�chart�ı���·��
            ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            // ʹ��printWriter���ļ�д��
            filename = ServletUtilities.saveChartAsPNG(chart, w, h, info, session);
//            ChartUtilities.writeImageMap(pw, filename, info, true);
            filename = ChartsUtilities.getImageJSON(filename, info, true);
//            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filename;
    }

}
