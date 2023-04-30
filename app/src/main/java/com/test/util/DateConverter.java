package com.test.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
    public Date convert(String s) {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            // 把字符串转换为日期类型
            Date date=simpleDateFormat.parse(s);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 如果转换异常则返回空
        return null;
    }
}