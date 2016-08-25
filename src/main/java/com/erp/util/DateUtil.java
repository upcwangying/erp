package com.erp.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * ������
 * @author zhanghj 2006-12-18
 */
public class DateUtil {
	/**
	 * ���������жϸ����ڵĸ�ʽ
	 * @param DateString
	 */
	public static String getFormat(String DateString) {
		String formatType = "";
		if (DateString.matches("\\d{2,4}-((1[0-2])|(0\\d)|\\d)-((\\d)|(0[1-9])|([1-2]\\d)|3[0-1])")) {
			formatType = "yyyy-MM-dd";
		} else if (DateString.matches("\\d{2,4}/((1[0-2])|(0\\d)|\\d)/((\\d)|(0[1-9])|([1-2]\\d)|3[0-1])")) {
			formatType = "yyyy/MM/dd";
		} else if (DateString.matches("\\d{2,4}-((1[0-2])|(0\\d)|\\d)-((\\d)|(0[1-9])|([1-2]\\d)|3[0-1]) ((2[0123])|(1\\d)|\\d|(0\\d)):(([0-5]\\d)|\\d):(([0-5]\\d)|\\d)")) {
			formatType = "yyyy-MM-dd HH:mm:ss";
		} else if (DateString.matches("\\d{2,4}-((1[0-2])|(0\\d)|\\d)-((\\d)|(0[1-9])|([1-2]\\d)|3[0-1]) ((2[0123])|(1\\d)|\\d|(0\\d)):(([0-5]\\d)|\\d)")) {
			formatType = "yyyy-MM-dd HH:mm";
		} else if (DateString.matches("\\d{2,4}/((1[0-2])|(0\\d)|\\d)/((\\d)|(0[1-9])|([1-2]\\d)|3[0-1]) ((2[0123])|(1\\d)|\\d|(0\\d)):(([0-5]\\d)|\\d):(([0-5]\\d)|\\d)")) {
			formatType = "yyyy/MM/dd HH:mm:ss";
		} else if (DateString.matches("\\d{2,4}/((1[0-2])|(0\\d)|\\d)/((\\d)|(0[1-9])|([1-2]\\d)|3[0-1]) ((2[0123])|(1\\d)|\\d|(0\\d)):(([0-5]\\d)|\\d)")) {
			formatType = "yyyy/MM/dd HH:mm";
		} else if (DateString.matches("\\d{2,4}-((1[0-2])|(0\\d)|\\d)-((\\d)|(0[1-9])|([1-2]\\d)|3[0-1]) ((2[0123])|(1\\d)|\\d|(0\\d)):(([0-5]\\d)|\\d):(([0-5]\\d)|\\d).\\d{1,3}")) {
			formatType = "yyyy-MM-dd HH:mm:ss.SSS";
		} else if (DateString.matches("\\d{2,4}/((1[0-2])|(0\\d)|\\d)/((\\d)|(0[1-9])|([1-2]\\d)|3[0-1]) ((2[0123])|(1\\d)|\\d|(0\\d)):(([0-5]\\d)|\\d):(([0-5]\\d)|\\d).\\d{1,3}")) {
			formatType = "yyyy/MM/dd HH:mm:ss.SSS";
		}
		return formatType;
	}

	/**
	 * �����ַ����ڣ��������ڸ�ʽ
	 * 
	 * @param dateString
	 * @return
	 */
	public static Date getDateByType(String dateString) {
		String formatType = getFormat(dateString);
		SimpleDateFormat sdf = new SimpleDateFormat(formatType);
		Date date = new Date();
		try {
			date = sdf.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * @Title: addDay
	 * @Description: ���ӻ��������
	 * @param @param date
	 * @param @param num
	 * @param @return
	 * @return Date
	 * @throws
	 */
	public static Date addDay2Date(Date date, int num) {
		Calendar startDT = Calendar.getInstance();
		startDT.setTime(date);
		startDT.add(Calendar.DAY_OF_MONTH, num);
		return startDT.getTime();
	}
	
	/**
     * ���ڸ�ʽ���������ַ���
     * @param date   Date  Ҫ��ʽ��������
     * @param format String ��ʽ���ַ���
     * @return String
     */
    public static String getDateValue(Date date, String format) {
        if (date == null) {           
            return "";
        }
        DateFormat formated = new SimpleDateFormat(format);
        return formated.format(date);
    }
    
    /**
     * �õ�ϵͳ��ǰ����(yyyy-MM-dd)
     * @return
     */
    public static String getSysFormatDate(){
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
        String s = simpledateformat.format(Calendar.getInstance().getTime());
        return s;
    }
    
    /**
     * ���ڸ�ʽ��
     */
    public static String getFormatDate(Date date, String format) {
    	SimpleDateFormat simpledateformat = new SimpleDateFormat(format);
    	return simpledateformat.format(date);
    }
    
    /**
     * �õ�+i�Ժ�����ڣ�i�����Ǹ���
     * @param s
     * @param s(��ʽ)
     * @param i
     * @return
     */
    public static String getNextDateByNum(String s, int i,String format) {
        SimpleDateFormat simpledateformat = new SimpleDateFormat(format);
        Date date = simpledateformat.parse(s, new ParsePosition(0));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(5, i);
        date = calendar.getTime();
        s = simpledateformat.format(date);
        return s;
    }
    
    /**
     * @Title: getMinDateValue 
     * @Description: �Ƚ��������ڣ����ؽ����һ��
     * @param @param dt1
     * @param @param dt2
     * @return void
     * @throws
     */
    public static Date getMinDateValue(Date dt1, Date dt2) {
    	return dt1 == null ? dt2 : (dt2 == null ? dt1 : (dt1.compareTo(dt2) < 0 ? dt1 : dt2));
    }
    
    /**
     * ��YYYY-MM-DD������ʽ����YYYYMMDD
     * @param date
     * @return
     */
    public static String formatDate(String date){
        if(date == null || date.trim().equals("")) return "";
        return date.replaceAll("-", "");
    }
    
    /**
     * ��������������������
     * @param startDate ��ʽ��yyyyMMdd
     * @param endDate ��ʽ��yyyyMMdd
     * @return ������������������
     */
    public static int dateMargin(String startDate, String endDate) {
        String d1=formatDate(startDate);
        String d2=formatDate(endDate);
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar= Calendar.getInstance();
        calendar.setTime(df.parse(d1, new ParsePosition(0)));
        Date end=df.parse(d2, new ParsePosition(0));
        int margin=0;
        int step=startDate.compareTo(endDate)>0? -1:1;
        while(calendar.getTime().compareTo(end)!=0) {
            calendar.add(Calendar.DATE,step);
            margin+=step;
        }
        return margin;
    }

}
