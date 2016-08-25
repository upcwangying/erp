package com.erp.util;

import java.math.BigDecimal;
import java.util.*;

public class StringUtil {

    /**
     * ��ʽ��price��ֵ��������λС��
     * @param price
     * @return
     */
    public static String formatValue4(double price) {
        java.text.NumberFormat nf = java.text.NumberFormat.getInstance(Locale.CHINESE);
        java.text.DecimalFormat df = (java.text.DecimalFormat) nf;
        df.setMinimumFractionDigits(4);
        df.setMaximumFractionDigits(4);
        String pattern = "#0.0000";
        df.applyPattern(pattern);
        df.setDecimalSeparatorAlwaysShown(true);
        return df.format(price);
    }

    /**
     * ��ʽ�������ֵ
     * @param price
     * @return
     */
    public static String formatValue4(String price) {
        double d = 0.0;
        if (price == null || price.trim().equals("") || price.equals("null"))
            d = 0.0;
        else {
            try {
                d = Double.parseDouble(price);
            } catch (NumberFormatException ex) {
                d = 0.0;
            } catch (Exception ex) {
                d = 0.0;
            }
        }
        return formatValue4(d);
    }

    /**
     * ��ʽ��price��ֵ��������λС��
     * @param price
     * @return
     */
    public static String formatVlaue(double price) {
        java.text.NumberFormat nf = java.text.NumberFormat.getInstance(Locale.CHINESE);
        java.text.DecimalFormat df = (java.text.DecimalFormat) nf;
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);
        String pattern = "#0.00";
        df.applyPattern(pattern);
        df.setDecimalSeparatorAlwaysShown(true);
        return df.format(price);
    }

    /**
     * ��ʽ��price��ֵ��������λС��
     * @param price
     * @return ������Ϊ0.00 �򷵻�""
     */
    public static String formatValue(double price) {
        java.text.NumberFormat nf = java.text.NumberFormat.getInstance(Locale.CHINESE);
        java.text.DecimalFormat df = (java.text.DecimalFormat) nf;
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);
        String pattern = "#0.00";
        df.applyPattern(pattern);
        df.setDecimalSeparatorAlwaysShown(true);
        if ("0.00".equals(df.format(price))) return "";
        return df.format(price);
    }

    /**
     * ��ʽ�������ֵ
     * @param price
     * @return
     */
    public static String formatVlaue(String price) {
        double d = 0.0;
        if (price == null || price.trim().equals("") || price.equals("null"))
            d = 0.0;
        else {
            try {
                d = Double.parseDouble(price);
            } catch (NumberFormatException ex) {
                d = 0.0;
            } catch (Exception ex) {
                d = 0.0;
            }
        }
        return formatVlaue(d);
    }

    /**
     * ��ʽ��price��ֵ��������λС��
     * @param price
     * @return
     */
    public static int formatVlaue1(double price) {
        java.text.NumberFormat nf = java.text.NumberFormat.getInstance(Locale.CHINESE);
        java.text.DecimalFormat df = (java.text.DecimalFormat) nf;
        df.setMinimumFractionDigits(0);
        df.setMaximumFractionDigits(0);
        String pattern = "#0";
        df.applyPattern(pattern);
        return Integer.parseInt(df.format(price));
    }

    /**
     * ʵ�����ַ���ΪLONG
     * @param value
     * @return
     */
    public static Long procLong(String value) {
        if (value == null || value.trim().equals("") || value.trim().equals("null")) {
            return new Long("0");
        } else {
            return new Long(value.trim());
        }
    }

    /**
     * ʵ�����ַ���Ϊint
     * @param value
     * @return
     */
    public static int procInt(String value) {
        if (value == null || value.trim().equals("") || value.trim().equals("null")) {
            return 0;
        } else {
            return Integer.parseInt(value.trim());
        }
    }

    /**
     * ʵ�����ַ���Ϊint
     * @param value
     * @return
     */
    public static String procStrToInt(String value) {
        if (value == null || value.trim().equals("") || value.trim().equals("null")) {
            return "0";
        } else {
            int iNum = value.indexOf(".");
            if (iNum == -1) {
                return value;
            } else {
                return value.substring(0, iNum);
            }
        }
    }

    /**
     * ʵ�����ַ���ΪBIGDECIMAL
     * @param value
     * @return
     */
    public static BigDecimal procBigDecimal(Object value) {
        if (isEmpty(procString(value))) {
            return new BigDecimal("0");
        } else {
            return new BigDecimal(procString(value).trim());
        }
    }

    /**
     * ʵ�����ַ���ΪDOUBLE
     * @param value
     * @return
     */
    public static Double procDouble(String value) {
        if (value == null || value.trim().equals("") || value.trim().equals("null")) {
            return new Double("0");
        } else {
            return new Double(value);
        }
    }

    /**
     * ʵ�����ַ���Ϊ�ʹ�
     * @param value
     * @return
     */
    public static String procString(Object value) {
        if (value == null || value.toString().trim().equals("")) {
            return "";
        } else {
            return String.valueOf(value);
        }
    }

    /**
     * ���ָ����Ŷ����ַ���������
     * @param strlist ���зָ����ŵ��ַ���
     * @param ken     �ָ�����
     * @return �б�
     */
    public static final List parseStringToArrayList(String strlist, String ken) {
        StringTokenizer st = new StringTokenizer(strlist, ken);
        if (strlist == null || strlist.equals("") || st.countTokens() <= 0) {
            return new ArrayList();
        }
        int size = st.countTokens();
        List strv = new ArrayList();
        for (int i = 0; i < size; i++) {
            String nextstr = st.nextToken();
            if (!nextstr.equals("")) {
                strv.add(nextstr);
            }
        }
        return strv;
    }

    /**
     * �жϸ����ַ����Ƿ�Ϊ��
     * @param s
     * @return ���Ϊ null �� "" �� "null" ����true
     */
    public static boolean isEmpty(String s) {
        return null == s || 0 == s.trim().length() || s.trim().equals("null");
    }

    /**
     * �����ַ���Ϊ�յ����
     * @param s
     * @return ���Ϊ null �� "" �� "null" ����""
     */
    public static String isEmptyDo(String s) {
        return isEmpty(s) ? "" : s;
    }

    /**
     * �����ַ�������Ϊ�յ����
     * @param o
     * @return ���Ϊ null �� "" �� "null" ����""
     */
    public static String isEmptyDo(Object o) {
        return null == o ? "" : isEmptyDo(o.toString());
    }

    /**
     * ���������ַ�������,��������token�ϲ�Ϊ�ַ���
     * @param s
     * @param tok
     * @return
     */
    public static String join(String[] s, String tok) {
        if (null == s || 0 == s.length) {
            return "";
        }
        if (null == tok || tok.length() == 0) {
            tok = ",";
        }
        StringBuffer rval = new StringBuffer();
        for (int i = 0; i < s.length; ++i) {
            rval.append(s[i]);

            if (s.length - 1 != i) {
                rval.append(tok);
            }
        }
        return rval.toString();
    }


    /**
     * ��ֵ��Ϊ��ʱ�������ݿ�ת��Ϊ''
     * @param value
     * @return
     */
    public static String formatValue5(Object value) {
        if (value == null || value.toString().trim().equals("")) {
            return "''";
        } else {
            return String.valueOf(value);
        }
    }


    /**
     * ��ʽ��price��ֵ��������λС��
     * @param price
     * @return ������Ϊ0.000 �򷵻�""
     */
    public static String formatValue3(double price) {
        java.text.NumberFormat nf = java.text.NumberFormat.getInstance(Locale.CHINESE);
        java.text.DecimalFormat df = (java.text.DecimalFormat) nf;
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);
        String pattern = "#0.000";
        df.applyPattern(pattern);
        df.setDecimalSeparatorAlwaysShown(true);
        if ("0.00".equals(df.format(price))) return "";
        return df.format(price);
    }

    /**
     * �ж�һ���ַ������Ƿ��������һ���ַ���
     * @param str
     * @param searchStr
     * @return
     */
    public static boolean contain(String str, String searchStr) {
        String replace_str = searchStr.toUpperCase();
        if (str.length() != str.replaceAll(replace_str, "").length()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * ���紫��һ��123.4����ô����ֵΪ200.0
     * @param value
     * @return
     */
    public static double getMaxNum(double value) {
        double result = 0;
        if (value >= 0) {
            String str = String.valueOf(value);
            if (str.indexOf(".") < 0) {//˵��û��С��λ
                String p1 = str.substring(0, 1);//��ȡ��һλ�ַ�
                int length = str.length();
                result = makeResult(p1, length);
            } else {//��С��λ
                String p1 = str.substring(0, 1);
                int a = str.indexOf(".");//�õ�С�����λ��
                int length = str.substring(0, a).length();
                result = makeResult(p1, length);
            }
        }
        return result;
    }

    public static double makeResult(String p1, int length) {
        int value = Integer.parseInt(p1) + 1;
        StringBuffer sb = new StringBuffer();
        sb.append(value);
        for (int i = 0; i < length - 1; i++) {
            sb.append("0");
        }
        return Double.parseDouble(sb.toString());
    }


    public static Long getLongValue(String param) throws Exception {
        if(isEmpty(param)) {
            return null;
        }
        return Long.valueOf(param);
    }

    public static int getIntValue(String param, int def) throws Exception {
        if(isEmpty(param)) {
            return def;
        }
        return Integer.valueOf(param);
    }

    public static Long getLongValue(String[] param, int i) throws Exception {
        if(null == param || param.length == 0 || (param.length > 1 && param.length < i)) {
            return null;
        }
        if(param.length == 1) {
            return getLongValue(param[0]);
        }
        return getLongValue(param[i]);
    }

    public static Float getFloatValue(String param) throws Exception {
        if(isEmpty(param)) {
            return null;
        }
        return Float.valueOf(param);
    }

    public static double getDoubleValue(String param) throws Exception {
        if(isEmpty(param)) {
            return 0d;
        }
        return Double.valueOf(param);
    }

    public static Float getFloatValue(String[] param, int i) throws Exception {
        if(null == param || param.length == 0 || (param.length > 1 && param.length < i)) {
            return null;
        }
        if(param.length == 1) {
            return getFloatValue(param[0]);
        }
        return getFloatValue(param[i]);
    }

    public static Date getDateValue(String param) throws Exception {
        if(isEmpty(param)) {
            return null;
        }
        return DateUtil.getDateByType(param);
    }

    public static String getStringValue(String[] param, int i) throws Exception {
        if(null == param || param.length == 0 || (param.length > 1 && param.length < i)) {
            return null;
        }
        if(param.length == 1) {
            return isEmpty(param[0]) ? null : param[0];
        }
        return isEmpty(param[i]) ? null : param[i];
    }

    /**
     * �����������˫�����м����룩
     * @param number
     * @param length ����С��λ��
     * @return
     */
    public static Float roundOfBanker(Object number, int length) throws Exception {
        if (null == number) return null;
        float ratio = (float) Math.pow(10, length);
        float _num = Float.parseFloat(number.toString()) * ratio;
        float mod = _num % 1;
        float result = (float) Math.floor(_num);
        if (mod < 0.5 || (mod == 0.5 && result % 2 == 0)) {
            return result / ratio;
        }
        return (result + 1) / ratio;
    }

}