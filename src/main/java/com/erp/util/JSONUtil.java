package com.erp.util;

import com.erp.entity.Module;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.Set;

/**
 * JSON工具类
 * Created by wang_ on 2016-07-21.
 */
public class JSONUtil {

    /**
     * 将Module-Tree转化为JSON格式
     * @param modules
     * @return
     */
    public static JSONArray parseToJson(Set<Module> modules) {
        return parseToJsonByModules(modules);
    }

    /**
     *
     * @param modules
     */
    private static JSONArray parseToJsonByModules(Set<Module> modules) {
        JSONArray array = new JSONArray();
        for (Module module : modules) {
            array.add(parseToJsonByModule(module));
        }
        return array;
    }

    /**
     *
     * @param module
     */
    private static JSONObject parseToJsonByModule(Module module) {
        JSONObject object = new JSONObject();
        object.put("id", module.getModuleId());
        object.put("text", module.getModuleName());
        object.put("order", module.getDisOrder());
        object.put("display", module.getDisplay());
        object.put("leaf", module.getIcon());
        object.put("url", module.getHref());
        object.put("parentId", module.getParentId());

        JSONObject attribute = new JSONObject();
        attribute.put("text", module.getModuleName());

        if (module.hasChildren()) {
            Set<Module> children = module.getChildren();
            object.put("state", "closed");
            object.put("children", parseToJsonByModules(children));
        }

        object.put("attributes", attribute);

        return object;
    }

}
