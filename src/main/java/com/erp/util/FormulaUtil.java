package com.erp.util;

import java.util.Vector;

/**
 * ������ʽ������
 * Created by wang_ on 2016-08-25.
 */
public class FormulaUtil {
    private static int leftBracket = 0;//�����Ÿ���
    private static int rightBracket = 0;//�����Ÿ���
    private static int startL = 0;//�����ŵ�λ��
    private static int startR = 0;//�����ŵ�λ��
    private static double answer = 0;
    private static String leftNumber = "0";
    private static String rightNumber = "0";
    public static String Msg = "";
    private static String formula = "";
    private static int[] sym = new int[4];
    private static Vector<String> list = new Vector<String>();//������Ŵ��ַ��������������ַ�
    static Vector<Integer> paras = new Vector<Integer>();//������ű�������

    public FormulaUtil() {
    }

    /**
     * ���ø�ʽ
     * @param calRule
     */
    private static void setFormula(String calRule) {
        formula = replaceSubtration(calRule.trim());
        formula = "(" + formula + ")";
    }

    /**
     * Ϊ��ʹ��ʽ��֧�ָ�����ʹ�á�`����ʾ���ţ�ʹ�á�-����ʾ���ţ������м��Ż��ɡ�����
     * @param vstr
     * @return
     */
    private static String replaceSubtration(String vstr) {
        String tmp = "";
        String result = "";
        int startS = vstr.indexOf("-");
        if (startS != -1) {
            if (startS > 0) {
                tmp = vstr.substring(startS - 1, startS);
                if (!"+".equals(tmp) && !"-".equals(tmp) && !"*".equals(tmp) && !"/".equals(tmp) &&
                        !"(".equals(tmp)) {
                    result = result + vstr.substring(0, startS) + "`";
                } else
                    result = result + vstr.substring(0, startS + 1);
            } else
                result = result + vstr.substring(0, startS + 1);
            vstr = vstr.substring(startS + 1);
        }
        while (startS != -1) {
            startS = vstr.indexOf("-");
            if (startS > 0) {
                tmp = vstr.substring(startS - 1, startS);
                if (!"+".equals(tmp) && !"-".equals(tmp) && !"*".equals(tmp) && !"/".equals(tmp) &&
                        !"(".equals(tmp))
                    result = result + vstr.substring(0, startS) + "`";
                else
                    result = result + vstr.substring(0, startS + 1);
            } else
                result = result + vstr.substring(0, startS + 1);
            vstr = vstr.substring(startS + 1);
        }
        result += vstr;
        return result;
    }

    public static String getFormula() {
        return formula.replace('`', '-').substring(1, formula.length() - 1);
    }

    /*
     * �����������
     */
    private static int getLeftBracket(String calRule) {
        leftBracket = 0;
        startL = calRule.indexOf("(");
        if (startL != -1) {
            calRule = calRule.substring(startL + 1, calRule.length());
        }
        while (startL != -1) {
            leftBracket++;
            startL = calRule.indexOf("(");
            calRule = calRule.substring(startL + 1, calRule.length());
        }
        return leftBracket;
    }

    /*
     * �����������
     */
    private static int getRightBracket(String calRule) {
        rightBracket = 0;
        startR = calRule.indexOf(")");
        if (startR != -1) {
            calRule = calRule.substring(startR + 1, calRule.length());
        }
        while (startR != -1) {
            rightBracket++;
            startR = calRule.indexOf(")");
            calRule = calRule.substring(startR + 1, calRule.length());
        }
        return rightBracket;
    }

    /*
    /*�Ա��������Ÿ���
    */
    private static boolean compareToLR() {
        int lb = getLeftBracket(formula);
        int rb = getRightBracket(formula);
        boolean CTLR = false;
        if (lb == rb) {
            Msg = "";
            CTLR = true;
        } else if (lb > rb) {
            Msg = "�������ĸ������������������飡";
            CTLR = false;
        } else {
            Msg = "�������ĸ������������������飡";
            CTLR = false;
        }
        return CTLR;
    }

    /*
    /*��鹫ʽ���Ƿ���ڷǷ��ַ���(+��-)��
    */
    private static boolean checkFormula() {
        boolean isOk = true;
        String[] bracket = new String[2];
        String[] sign = new String[4];
        bracket[0] = "(";
        bracket[1] = ")";
        sign[0] = "+";
        sign[1] = "`";
        sign[2] = "*";
        sign[3] = "/";
        String vstr = "";
        for (int i = 0; i < bracket.length; i++) {
            for (int j = 0; j < sign.length; j++) {
                if (i == 0)
                    vstr = bracket[i] + sign[j];
                else
                    vstr = sign[j] + bracket[i];
                if (formula.indexOf(vstr) > 0) {
                    Msg = "��ʽ�д��ڷǷ��ַ�" + vstr;
                    isOk = false;
                    return isOk;
                }
            }
        }
        for (int i = 0; i < sign.length; i++) {
            for (int j = 0; j < sign.length; j++) {
                vstr = sign[i] + sign[j];
                if (formula.indexOf(vstr) > 0) {
                    Msg = "��ʽ�д��ڷǷ��ַ�" + vstr;
                    isOk = false;
                    return isOk;
                }
            }
        }
        if (formula.indexOf("()") > 0) {
            Msg = "��ʽ�д��ڷǷ��ַ�()";
            isOk = false;
        }
        return isOk;
    }

    /*
     *�ж�������ַ����Ƿ�Ϸ�
     */
    public static boolean checkValid() {
        if ((formula == null) || (formula.trim().length() <= 0)) {
            Msg = "����������calRule!";
            return false;
        }
        return (compareToLR() && checkFormula());
    }

    /**
     * ���ع�ʽִ�н��
     * @return
     */
    private static double getResult() {
        String formulaStr = "", calRule = "";
        double value = 0.0;
        calRule = formula;
        if (checkValid()) {
            for (int i = 0; i < leftBracket; i++) {
                int iStart = calRule.lastIndexOf("(") + 1;
                //�������������������
                formulaStr = calRule.substring(iStart, iStart + calRule.substring(iStart).indexOf(")")).trim();
                symbolParse(formulaStr);
                value = parseString();
                iStart = calRule.lastIndexOf("(");
                int iEnd = calRule.substring(iStart).indexOf(")") + 1;
                calRule = calRule.substring(0, iStart).trim() +
                        value +
                        calRule.substring(iStart + iEnd, calRule.length()).trim();
            }
        }
        double tmp = Math.pow(10, 10);
        value = Math.round(value * tmp) / tmp;
        return value;
    }

    /**
     *
     * @param calRule
     * @return
     */
    public static double getDoubleResult(String calRule) {
        setFormula(calRule);
        return getResult();
    }

    /**
     *
     * @param calRule
     * @return
     */
    public static long getLongResult(String calRule) {
        setFormula(calRule);
        double result = getResult();
        return Long.valueOf(formatValue(result, 0));
    }

    private static String formatValue(double price, int fix) {
        java.text.NumberFormat nf = java.text.NumberFormat.getInstance(java.util.Locale.CHINESE);
        java.text.DecimalFormat df = (java.text.DecimalFormat) nf;
        df.setMinimumFractionDigits(fix);
        df.setMaximumFractionDigits(fix);
        StringBuffer pattern = new StringBuffer("0");
        for (int i = 0; i < fix; i++) {
            if (i == 0) {
                pattern.append(".0");
            } else {
                pattern.append("0");
            }
        }
        df.applyPattern(pattern.toString());
        df.setDecimalSeparatorAlwaysShown(false);
        return df.format(price);
    }

    public void FormulaStr(String calRule) {
        String formulaStr = "";
        if (checkValid()) {
            for (int i = 0; i < leftBracket; i++) {
                formulaStr = calRule.substring(calRule.lastIndexOf("(") + 1, calRule.indexOf(")")).trim();
                symbolParse(formulaStr);
                double value = parseString();
                String.valueOf(value);
                System.out.println("formulaStr=" + formulaStr);
                //formulaVal = Double.parseDouble(formulaStr);
                System.out.println("formulaVal=" + value);
                calRule = calRule.substring(0, calRule.lastIndexOf("(")).trim() + value + calRule.substring(calRule.indexOf(")") + 1, calRule.length()).trim();
                System.out.println("calRule=" + calRule);
            }
        }
    }

    /**
     * ��ȡ�������������ݵ�List
     * @param str
     */
    private static void symbolParse(String str) {
        list.clear();
        int count = 0;
        for (int i = 0; i < 4; i++) {
            compareMin(str);
            while (sym[i] != -1) {
                String insStr = str.substring(0, sym[i]).trim();
                //�ж�insStr�Ƿ�����ĸ
                if (containsLetter(insStr)) {
                    insStr = Integer.toString(parseLetter(insStr, paras.get(count)));
                }
                list.add(insStr);
                insStr = str.substring(sym[i], sym[i] + 1).trim();
                list.add(insStr);

                str = str.substring(sym[i] + 1, str.length()).trim();
                compareMin(str);
                count++;
            }
        }
        if (sym[0] == -1 && sym[1] == -1 && sym[2] == -1 & sym[3] == -1) {
            if (containsLetter(str)) {
                str = Integer.toString(parseLetter(str, paras.get(count)));
            }
            list.add(str);
        }
    }

    /**
     *
     * @param insStr
     * @return
     */
    public static boolean containsLetter(String insStr) {
        boolean isLetter = false;
        for (int i = 0; i < insStr.length(); i++) {
            char ch = insStr.charAt(i);
            if (Character.isLetter(ch)) {
                isLetter = true;
            }
        }
        return isLetter;
    }

    /**
     *
     * @param insStr
     * @param para
     * @return
     */
    public static int parseLetter(String insStr, int para) {
        if (insStr.charAt(0) == '-') {
            para = 0 - para;
        }
        return para;
    }

    /**
     * ѭ���Ƚϸ�SubString��ʼֵ
     * @param str
     */
    private static void compareMin(String str) {
        int sps = str.indexOf("`");//����subtration
        sym[0] = sps;
        int spa = str.indexOf("+");//�ӷ�addition
        sym[1] = spa;
        int spd = str.indexOf("/");//����division
        sym[2] = spd;
        int spm = str.indexOf("*");//�˷�multiplication
        sym[3] = spm;
        for (int i = 1; i < sym.length; i++) {
            for (int j = 0; j < sym.length - i; j++)
                if (sym[j] > sym[j + 1]) {
                    int temp = sym[j];
                    sym[j] = sym[j + 1];
                    sym[j + 1] = temp;
                }
        }
    }

    private static double parseString()
            throws NumberFormatException, StringIndexOutOfBoundsException {
        try {
            calculate();
            return answer;
        } catch (Exception e) {
            Msg = "����" + e.getMessage();
            return 0.0;
        }
    }

    /**
     * ����
     */
    private static void calculate() {
        // �������
        int spd = list.indexOf("/");
        while (spd != -1) {
            leftNumber = list.get(spd - 1).toString();
            rightNumber = list.get(spd + 1).toString();
            list.remove(spd - 1);
            list.remove(spd - 1);
            list.remove(spd - 1);
            double ln = Double.valueOf(leftNumber).doubleValue();
            double rn = Double.valueOf(rightNumber).doubleValue();
            double answer = ln / rn;
            list.add(spd - 1, String.valueOf(answer));
            spd = list.indexOf("/");
        }

        // ����˷�
        int spm = list.indexOf("*");
        while (spm != -1) {
            leftNumber = list.get(spm - 1).toString();
            rightNumber = list.get(spm + 1).toString();
            list.remove(spm - 1);
            list.remove(spm - 1);
            list.remove(spm - 1);
            double ln = Double.valueOf(leftNumber).doubleValue();
            double rn = Double.valueOf(rightNumber).doubleValue();
            double answer = ln * rn;
            list.add(spm - 1, String.valueOf(answer));
            spm = list.indexOf("*");
        }

        // �������
        int sps = list.indexOf("`");
        while (sps != -1) {
            leftNumber = list.get(sps - 1).toString();
            rightNumber = list.get(sps + 1).toString();
            list.remove(sps - 1);
            list.remove(sps - 1);
            list.remove(sps - 1);
            double ln = Double.valueOf(leftNumber).doubleValue();
            double rn = Double.valueOf(rightNumber).doubleValue();
            double answer = ln - rn;
            list.add(sps - 1, String.valueOf(answer));
            sps = list.indexOf("`");
        }

        // ����ӷ�
        int spa = list.indexOf("+");
        while (spa != -1) {
            leftNumber = list.get(spa - 1).toString();
            rightNumber = list.get(spa + 1).toString();
            list.remove(spa - 1);
            list.remove(spa - 1);
            list.remove(spa - 1);
            double ln = Double.valueOf(leftNumber).doubleValue();
            double rn = Double.valueOf(rightNumber).doubleValue();
            double answer = ln + rn;
            list.add(spa - 1, String.valueOf(answer));
            spa = list.indexOf("+");
        }
        if (list.size() != 0) {
            String result = list.get(0).toString();
            if (result == null || result.length() == 0) result = "0";
            answer = Double.parseDouble(list.get(0).toString());
        }
    }

//    public static void main(String[] args) {
//        System.out.println(getLongResult("1 + 2 * (3 + (2*3)) -5"));
//    }

}
