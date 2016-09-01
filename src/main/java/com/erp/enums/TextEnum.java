package com.erp.enums;

/**
 * Created by wang_ on 2016-08-04.
 */
public enum TextEnum {

    ADD("add", new String[]{"�ύ�ɹ���", "�ύʧ�ܣ�"}),
    INIT("init", new String[]{"��ʼ���ɹ���", "��ʼ��ʧ�ܣ�"}),
    INSERT("insert", new String[]{"����ɹ���", "����ʧ�ܣ�"}),
    UPDATE("update", new String[]{"���³ɹ���", "����ʧ�ܣ�"}),
    DELETE("delete", new String[]{"ɾ���ɹ���", "ɾ��ʧ�ܣ�"}),
    RESUME("resume", new String[]{"���ݻָ��ɹ���", "���ݻָ�ʧ�ܣ�"});

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
