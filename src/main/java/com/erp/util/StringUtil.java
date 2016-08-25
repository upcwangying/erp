package com.erp.util;

import java.math.BigDecimal;
import java.util.*;

public class StringUtil {

    /**
     * 格式化price的值，保留四位小数
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
     * 格式化传入的值
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
     * 格式化price的值，保留二位小数
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
     * 格式化price的值，保留二位小数
     * @param price
     * @return 如果结果为0.00 则返回""
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
     * 格式化传入的值
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
     * 格式化price的值，保留二位小数
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
     * 实例化字符串为LONG
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
     * 实例化字符串为int
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
     * 实例化字符串为int
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
     * 实例化字符串为BIGDECIMAL
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
     * 实例化字符串为DOUBLE
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
     * 实例化字符串为客串
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
     * 按分隔符号读出字符串的内容
     * @param strlist 含有分隔符号的字符串
     * @param ken     分隔符号
     * @return 列表
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
     * 判断给定字符串是否为空
     * @param s
     * @return 如果为 null 或 "" 或 "null" 返回true
     */
    public static boolean isEmpty(String s) {
        return null == s || 0 == s.trim().length() || s.trim().equals("null");
    }

    /**
     * 处理字符串为空的情况
     * @param s
     * @return 如果为 null 或 "" 或 "null" 返回""
     */
    public static String isEmptyDo(String s) {
        return isEmpty(s) ? "" : s;
    }

    /**
     * 处理字符串对象为空的情况
     * @param o
     * @return 如果为 null 或 "" 或 "null" 返回""
     */
    public static String isEmptyDo(Object o) {
        return null == o ? "" : isEmptyDo(o.toString());
    }

    /**
     * 将给定的字符串数组,按给定的token合并为字符串
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
     * 数值型为空时插入数据库转化为''
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
     * 格式化price的值，保留三位小数
     * @param price
     * @return 如果结果为0.000 则返回""
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
     * 判断一个字符串中是否包含另外一个字符串
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
     * 比如传入一个123.4，那么返回值为200.0
     * @param value
     * @return
     */
    public static double getMaxNum(double value) {
        double result = 0;
        if (value >= 0) {
            String str = String.valueOf(value);
            if (str.indexOf(".") < 0) {//说明没有小数位
                String p1 = str.substring(0, 1);//截取第一位字符
                int length = str.length();
                result = makeResult(p1, length);
            } else {//有小数位
                String p1 = str.substring(0, 1);
                int a = str.indexOf(".");//得到小数点的位置
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
     * 四舍六入五成双（银行家舍入）
     * @param number
     * @param length 保留小数位数
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