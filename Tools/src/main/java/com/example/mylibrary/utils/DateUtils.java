package com.example.mylibrary.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static String getDateFormat(String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    public static String getDateFormat() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        String date = getTime(str);
        return date;
    }

    /**
     * 获取当前的时间戳
     *
     * @return
     */
    public static long getTimeStame() {
        //获取当前的毫秒值
        long time = System.currentTimeMillis();
        return time;
    }

    public static String getDate(long timeStamp) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        String str = formatter.format(timeStamp);
        return str;
    }

    public static String getDateTime(long timeStamp) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        String str = formatter.format(timeStamp);
        return str;
    }

    public static String getMillisDateTime(long timeStamp) {
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
        String str = formatter.format(timeStamp);
        return str;
    }

    public static String getDateHour(long timeStamp) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = formatter.format(timeStamp);
        return str;
    }

    public static String getDateYear(long timeStamp) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String str = formatter.format(timeStamp);
        return str;
    }

    //字符串转时间戳
    public static String getTime(String timeString) {
        String timeStamp = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date d;
        try {
            d = sdf.parse(timeString);
            long l = d.getTime() / 1000;
            timeStamp = String.valueOf(l);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeStamp;
    }

    public static String getYMD(String timeString) {
        String timeStamp = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date d;
        String birth = "";
        try {
            d = sdf.parse(timeString);
            long l = d.getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            birth = formatter.format(l);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return birth;
    }

    public static String getYMD(long timeStamp) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String str = formatter.format(timeStamp);
        return str;
    }

    public static String getYM(int timeString) {
        String timeStamp = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        Date d;
        String date = "";
        try {
            d = sdf.parse(String.valueOf(timeString));
            long l = d.getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
            date = formatter.format(l);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getYM(long timeStamp) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
        String str = formatter.format(timeStamp);
        return str;
    }

    /**
     * 根据时间戳来判断消息是否是今天发的
     *
     * @param long_time
     * @return
     */
    public static String getMsgTime(long long_time) {
        long long_by_13 = 1000000000000L;
        long long_by_10 = 1000000000L;
        if (long_time / long_by_13 < 1) {
            if (Long.valueOf(long_time) / Long.valueOf(long_by_10) >= 1) {
                long_time = long_time * 1000;
            }
        }
        Timestamp time = new Timestamp(long_time);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat format_now = new SimpleDateFormat("yyyy-MM-dd");
        //System.out.println("传递过来的时间:"+format.format(time));
        //System.out.println("现在的时间:"+format.format(now));
        long day_conver = 1000 * 60 * 60 * 24;
        long hour_conver = 1000 * 60 * 60;
        long min_conver = 1000 * 60;
        long time_conver = now.getTime() - time.getTime();
        long temp_conver;

        String date = format.format(time);
        String nowDate = format_now.format(getTimeStame());
        String nowDateAddOne = format_now.format(getTimeStame() - (24 * 60 * 60 * 1000));
        String[] split = date.split(" ");
        if (split[0].equals(nowDate)) {
            return "今天 " + split[1];
        } else if (split[0].equals(nowDateAddOne)) {
            return "昨天 " + split[1];
        } else {
            return format.format(time);
        }
    }

    /**
     * 根据时间戳来判断当前的时间是几天前,几分钟,刚刚
     *
     * @param long_time
     * @return
     */
    public static String getTimeStateNew(String long_time) {
        String long_by_13 = "1000000000000";
        String long_by_10 = "1000000000";
        if (Long.valueOf(long_time) / Long.valueOf(long_by_13) < 1) {
            if (Long.valueOf(long_time) / Long.valueOf(long_by_10) >= 1) {
                long_time = long_time + "000";
            }
        }
        Timestamp time = new Timestamp(Long.valueOf(long_time));
        Timestamp now = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //System.out.println("传递过来的时间:"+format.format(time));
        //System.out.println("现在的时间:"+format.format(now));
        long day_conver = 1000 * 60 * 60 * 24;
        long hour_conver = 1000 * 60 * 60;
        long min_conver = 1000 * 60;
        long time_conver = now.getTime() - time.getTime();
        long temp_conver;
        //System.out.println("天数:"+time_conver/day_conver);
        if ((time_conver / day_conver) < 3) {
            temp_conver = time_conver / day_conver;
            if (temp_conver <= 2 && temp_conver >= 1) {
                return temp_conver + "天前";
            } else {
                temp_conver = (time_conver / hour_conver);
                if (temp_conver >= 1) {
                    return temp_conver + "小时前";
                } else {
                    temp_conver = (time_conver / min_conver);
                    if (temp_conver >= 1) {
                        return temp_conver + "分钟前";
                    } else {
                        return "刚刚";
                    }
                }
            }
        } else {
            return format.format(time);
        }
    }

    public static long getYear(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        String str = formatter.format(time);
        long year = Long.valueOf(str);
        return year;
    }

    public static long getMonth(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM");
        String str = formatter.format(time);
        long month = Long.valueOf(str);
        return month;
    }

    public static long getDay(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd");
        String str = formatter.format(time);
        return Long.parseLong(str);
    }

    public static String getYearStr(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
        String str = formatter.format(time);
        return str;
    }

    public static String getMonthStr(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月");
        String str = formatter.format(time);
        return str;
    }

    public static String getDayStr(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd");
        String str = formatter.format(time);
        return str;
    }

    /**
     * 根据当前日期获得是星期几
     * time=yyyy-MM-dd
     *
     * @return
     */
    public static String getWeek(long time) {
        String date = getDate(time);
        String Week = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int wek = c.get(Calendar.DAY_OF_WEEK);

        if (wek == 1) {
            Week += "星期日";
        }
        if (wek == 2) {
            Week += "星期一";
        }
        if (wek == 3) {
            Week += "星期二";
        }
        if (wek == 4) {
            Week += "星期三";
        }
        if (wek == 5) {
            Week += "星期四";
        }
        if (wek == 6) {
            Week += "星期五";
        }
        if (wek == 7) {
            Week += "星期六";
        }
        return Week;
    }

    //字符串转时间戳
    public static String getTimeLine(String timeString) {
        String timeStamp = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d;
        String times = "";
        try {
            d = sdf.parse(timeString);
            long l = d.getTime() / 1;
            SimpleDateFormat formatters = new SimpleDateFormat("yyyyMMdd");
            times = formatters.format(l);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return times;
    }

    public static String getLongDate(String time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
        Date date;
        String times = "";
        try {
            date = formatter.parse(time);
            long ltime = date.getTime();
            SimpleDateFormat formatters = new SimpleDateFormat("yyyyMMdd");
            times = formatters.format(ltime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }
}
