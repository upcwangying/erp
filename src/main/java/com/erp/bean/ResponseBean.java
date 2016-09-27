package com.erp.bean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.Serializable;

/**
 * Created by wang_ on 2016-08-08.
 */
public class ResponseBean implements Serializable {
    private boolean success;
    private String message;

    private JSONArray jsonArray;
    private JSONObject jsonObject;

    public ResponseBean() {
    }

    public ResponseBean(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ResponseBean(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public ResponseBean(JSONArray jsonArray, Object object, boolean queryAll) {
        this.jsonArray = jsonArray;
        if (queryAll) {
            this.jsonObject = JSONObject.fromObject(object);
            this.jsonArray.add(0, this.jsonObject);
        }
    }

    /**
     *
     * @return
     */
    public String getResponseText () {
        this.jsonObject = new JSONObject();
        jsonObject.put("success", success);
        jsonObject.put("msg", message);
        return jsonObject.toString();
    }

    /**
     *
     * @return
     */
    public String getResponseBool () {
        this.jsonObject = new JSONObject();
        jsonObject.put("success", success);
        return jsonObject.toString();
    }

    /**
     *
     * @param flag
     * @return
     */
    public String getResponseArray(boolean flag) {
        if (jsonArray != null) {
            if (flag) {
                this.jsonObject = new JSONObject();
                jsonObject.put("total", jsonArray.size());
                jsonObject.put("rows", jsonArray);
                return jsonObject.toString();
            } else {
                return jsonArray.toString();
            }
        }
        return  "";
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

}
