package com.erp.chart.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.imagemap.*;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by wang_ on 2016-08-02.
 */
public class ImageJSONUtilities extends ImageMapUtilities {
    public ImageJSONUtilities() {
    }

    public static void writeImageJSON(PrintWriter writer, String name, ChartRenderingInfo info) throws IOException {
        writeImageJSON(writer, name, info, new StandardToolTipTagFragmentGenerator(), new StandardURLTagFragmentGenerator());
    }

    public static void writeImageJSON(PrintWriter writer, String name, ChartRenderingInfo info, boolean useOverLibForToolTips) throws IOException {
        Object toolTipTagFragmentGenerator;
        if(useOverLibForToolTips) {
            toolTipTagFragmentGenerator = new OverLIBToolTipTagFragmentGenerator();
        } else {
            toolTipTagFragmentGenerator = new StandardToolTipTagFragmentGenerator();
        }

        writeImageJSON(writer, name, info, (ToolTipTagFragmentGenerator)toolTipTagFragmentGenerator, new StandardURLTagFragmentGenerator());
    }

    public static void writeImageJSON(PrintWriter writer, String name, ChartRenderingInfo info, ToolTipTagFragmentGenerator toolTipTagFragmentGenerator, URLTagFragmentGenerator urlTagFragmentGenerator) throws IOException {
        writer.println(getImageJSON(name, info, toolTipTagFragmentGenerator, urlTagFragmentGenerator));
    }

    public static String getImageJSON(String name, ChartRenderingInfo info) {
        return getImageJSON(name, info, new StandardToolTipTagFragmentGenerator(), new StandardURLTagFragmentGenerator());
    }

    public static String getImageJSON(String name, ChartRenderingInfo info, ToolTipTagFragmentGenerator toolTipTagFragmentGenerator, URLTagFragmentGenerator urlTagFragmentGenerator) {
        JSONObject object = new JSONObject();
        object.put("id", htmlEscape(name));
        object.put("name", htmlEscape(name));
        object.put("html", getImageMap(name, info, toolTipTagFragmentGenerator, urlTagFragmentGenerator));
        EntityCollection entities = info.getEntityCollection();
        if(entities != null) {
            int count = entities.getEntityCount();
            JSONArray areas = new JSONArray();
            JSONObject area = null;
            for(int i = count - 1; i >= 0; --i) {
                ChartEntity entity = entities.getEntity(i);
                if(entity.getToolTipText() != null || entity.getURLText() != null) {
                    String areaTag = entity.getImageJSONAreaTag(toolTipTagFragmentGenerator, urlTagFragmentGenerator);
                    if(areaTag.length() > 0) {
                        area = new JSONObject();
                        area.put("area", areaTag);
                        areas.add(area);
                    }
                }
            }
            object.put("area", areas);
        }

        return object.toString();
    }

}
