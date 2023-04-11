package com.pt.vx.utils;

import cn.hutool.core.date.ChineseDate;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static java.time.DayOfWeek.MONDAY;

public class DateUtil {




    /**
     * 获取今天离目标的距离天数
     * @param year 目标年
     * @param month  目标月
     * @param day 目标天
     * @return 天数
     */
    public static String passDay(int year,int month,int day){
        LocalDate start = LocalDate.of(year, month, day);
        LocalDate now = LocalDate.now();
        return passDay(now,start);
    }

    /**
     *
     * @param chineseYear 农历年
     * @param chineseMonth 农历月
     * @param chineseDay 农历日
     * @return 距离今天的天数
     */
    public static String passDayChinese(int chineseYear,int chineseMonth,int chineseDay){
        ChineseDate chineseDate = new ChineseDate(chineseYear,chineseMonth,chineseDay);
        int gregorianYear = chineseDate.getGregorianYear();
        int gregorianMonthBase1 = chineseDate.getGregorianMonthBase1();
        int gregorianDay = chineseDate.getGregorianDay();
        LocalDate start = LocalDate.of(gregorianYear, gregorianMonthBase1, gregorianDay);
        LocalDate now = LocalDate.now();
        return passDay(now,start);
    }
    
    public static String passDayOfNow(LocalDate source){
        int year = source.getYear();
        int month = source.getMonthValue();
        int day = source.getDayOfMonth();
        LocalDate start = LocalDate.of(year, month, day);
        LocalDate now = LocalDate.now();
        return passDay(now,start);
    }

    /**
     *  获取资源离目标的距离天数
     * @param source 资源日期
     * @param target 目标容器
     * @return 天数
     */
    public static String passDay(LocalDate source,LocalDate target){
        return source.toEpochDay() - target.toEpochDay() + "";
    }


    /**
     *
     * 获取距离阳历生日的天数
     * @param month 阳历生日的月份
     * @param day 阳历生日的日期
     * @return 天数
     */
    public static String getNextBirthDay(int month,int day){
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int monthValue = now.getMonthValue();
        int dayOfMonth = now.getDayOfMonth();

        //生日已经过去
        if(monthValue > month || (monthValue == month && dayOfMonth > day)){
            year++;
        }
        return passDay(LocalDate.of(year, month, day),now);
    }

    public static String getNextBirthDay(LocalDate birthday){
        int month = birthday.getMonthValue();
        int day = birthday.getDayOfMonth();
        return getNextBirthDay(month,day);
    }

    public static String getNextChineseBirthDay(LocalDate birthday){
        int month = birthday.getMonthValue();
        int day = birthday.getDayOfMonth();
        return getNextChineseBirthDay(month,day);
    }

    /**
     * 获取距离农历生日的天数
     * @param chineseMonth 农历生日的月份
     * @param chineseDay 农历生日的日期
     * @return 天数
     */
    public static String getNextChineseBirthDay(int chineseMonth,int chineseDay){

        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int monthValue = now.getMonthValue();
        int dayOfMonth = now.getDayOfMonth();

        ChineseDate birthDay = new ChineseDate(year,chineseMonth,chineseDay,false);
        int greMonth = birthDay.getGregorianMonthBase1();
        int greDay = birthDay.getGregorianDay();

        //非润月生日已经过去
        if(monthValue > greMonth || (monthValue == greMonth && dayOfMonth > greDay)){
            ChineseDate birthDay2 = new ChineseDate(year,chineseMonth,chineseDay);
            int greMonth2= birthDay2.getGregorianMonthBase1();
            int greDay2 = birthDay2.getGregorianDay();

            //闰月生日已经过去，则返回下一个非闰月生日
            if(monthValue > greMonth2 || (monthValue == greMonth2 && dayOfMonth > greDay2)){
                ChineseDate birthDay3 = new ChineseDate(++year,chineseMonth,chineseDay,false);
                int greMonth3 = birthDay3.getGregorianMonthBase1();
                int greDay3 = birthDay3.getGregorianDay();
                return passDay(LocalDate.of(year,greMonth3,greDay3),now);
            }

            //返回闰月生日
            return passDay(LocalDate.of(year,greMonth2,greDay2),now);

        }

        return passDay(LocalDate.of(year,greMonth,greDay),now);
    }

    /**
     *
     * @param date 日期
     * @param isChinese 中文还是数字
     * @return 星期
     */
    public static String getWeek(LocalDate date,boolean isChinese){
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if(isChinese){
            String week = "";
            switch (dayOfWeek) {
                case MONDAY:
                    week = "一";
                    break;
                case TUESDAY:
                    week = "二";
                    break;
                case WEDNESDAY:
                    week = "三";
                    break;
                case THURSDAY:
                    week = "四";
                    break;
                case FRIDAY:
                    week = "五";
                    break;
                case SATURDAY:
                    week = "六";
                    break;
                case SUNDAY:
                    week = "日";
                    break;
                default:
                    week = "未知";
            }

            return week;
        }else {
            return ""+dayOfWeek.getValue();
        }



    }


}
