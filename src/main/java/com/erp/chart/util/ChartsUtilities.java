package com.erp.chart.util;

import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.imagemap.*;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by wang_ on 2016-08-02.
 */
public class ChartsUtilities extends ChartUtilities {

    public ChartsUtilities() {
        super();
    }

    public static void writeImageJSON(PrintWriter writer, String name, ChartRenderingInfo info, boolean useOverLibForToolTips) throws IOException {
        Object toolTipTagFragmentGenerator;
        if(useOverLibForToolTips) {
            toolTipTagFragmentGenerator = new OverLIBToolTipTagFragmentGenerator();
        } else {
            toolTipTagFragmentGenerator = new StandardToolTipTagFragmentGenerator();
        }

        ImageJSONUtilities.writeImageJSON(writer, name, info, (ToolTipTagFragmentGenerator)toolTipTagFragmentGenerator, new StandardURLTagFragmentGenerator());
    }

    public static void writeImageJSON(PrintWriter writer, String name, ChartRenderingInfo info, ToolTipTagFragmentGenerator toolTipTagFragmentGenerator, URLTagFragmentGenerator urlTagFragmentGenerator) throws IOException {
        writer.println(ImageJSONUtilities.getImageJSON(name, info, toolTipTagFragmentGenerator, urlTagFragmentGenerator));
    }

    public static String getImageJSON(String name, ChartRenderingInfo info, boolean useOverLibForToolTips) throws IOException {
        Object toolTipTagFragmentGenerator;
        if(useOverLibForToolTips) {
            toolTipTagFragmentGenerator = new OverLIBToolTipTagFragmentGenerator();
        } else {
            toolTipTagFragmentGenerator = new StandardToolTipTagFragmentGenerator();
        }

        return ImageJSONUtilities.getImageJSON(name, info, (ToolTipTagFragmentGenerator)toolTipTagFragmentGenerator, new StandardURLTagFragmentGenerator());
    }

    public static String getImageJSON(String name, ChartRenderingInfo info) {
        return ImageJSONUtilities.getImageJSON(name, info, new StandardToolTipTagFragmentGenerator(), new StandardURLTagFragmentGenerator());
    }

    public static String getImageJSON(String name, ChartRenderingInfo info, ToolTipTagFragmentGenerator toolTipTagFragmentGenerator, URLTagFragmentGenerator urlTagFragmentGenerator) {
        return ImageJSONUtilities.getImageJSON(name, info, toolTipTagFragmentGenerator, urlTagFragmentGenerator);
    }
}
