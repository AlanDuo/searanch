package com.yunyuan.searanch.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期格式工具类
 *
 * @author alan
 * @date 2020/5/4
 */
public class DateUtil {
    private static final Logger LOGGER= LoggerFactory.getLogger(DateUtil.class);
    private static final Integer TEN=10;
    private DateUtil(){}

    public static Date parseDate(Integer year){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy");
        try {
            return simpleDateFormat.parse(year.toString());
        }catch (Exception e) {
            LOGGER.info(e.getMessage());
            return null;
        }
    }

    /**
     * 根据年月转化为Date
     *
     * @param year
     * @param month
     * @return
     */
    public static Date parseDate(Integer year, Integer month){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM");
        String date=null;
        String month1=month.toString();
        if(month<TEN){
            month1="0"+month1;
        }
        date=year+"-"+month1;
        try {
            return simpleDateFormat.parse(date);
        }catch (Exception e) {
            LOGGER.info(e.getMessage());
            return null;
        }
    }
    /**
     * 根据年月日转化为Date
     *
     * @param year
     * @param month
     * @return
     */
    public static Date parseDate(Integer year, Integer month,Integer day){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String date=null;
        String month1=month.toString();
        String day1=day.toString();
        if(month<TEN){
            month1="0"+month1;
        }
        if(day<TEN){
            day1="0"+day1;
        }
        date=year+"-"+month1+"-"+day1;
        try {
            return simpleDateFormat.parse(date);
        }catch (Exception e) {
            LOGGER.info(e.getMessage());
            return null;
        }
    }
}
