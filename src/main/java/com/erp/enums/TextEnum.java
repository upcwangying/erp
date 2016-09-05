package com.erp.enums;

/**
 * Created by wang_ on 2016-08-04.
 */
public enum TextEnum {

    ADD("add", new String[]{"提交成功！", "提交失败："}),
    INIT("init", new String[]{"初始化成功！", "初始化失败："}),
    INSERT("insert", new String[]{"插入成功！", "插入失败："}),
    UPDATE("update", new String[]{"更新成功！", "更新失败："}),
    DELETE("delete", new String[]{"删除成功！", "删除失败："}),
    UP("up", new String[]{"商品上架成功！", "商品上架失败："}),
    DOWN("down", new String[]{"商品下架成功！", "商品下架失败："}),
    RESUME("resume", new String[]{"数据恢复成功！", "数据恢复失败："});

    private String key;
    private String[] value;

    TextEnum(String key, String[] value) {
        this.key = key;
        this.value = value;
    }

    public static TextEnum getEnumByName(String key) {
        for (TextEnum textEnum : values()) {
            if (textEnum.getKey().equals(key)) {
                return textEnum;
            }
        }
        return null;
    }

    public static String getText(String key, boolean flag) {
        String text = "";
        if (key != null) {
            key = key.toLowerCase();
            TextEnum textEnum = getEnumByName(key);
            if (textEnum != null) {
                String[] values = textEnum.getValue();
                if (flag)
                    text = values[0];
                else
                    text = values[1];
            }
        }
        return text;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String[] getValue() {
        return value;
    }

    public void setValue(String[] value) {
        this.value = value;
    }
}
