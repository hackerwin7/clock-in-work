package hackerwin7.beijing.java.clock.in.work.utils.date;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by hp on 6/23/15.
 */
public class DateTimeUtils {
    private static Calendar cal = Calendar.getInstance();

    /**
     * get the current date
     * @return date
     * @throws Exception
     */
    public static Date getCurDate() throws Exception {
        return cal.getTime();
    }

    /**
     * get current time stamp
     * @return time stamp
     * @throws Exception
     */
    public static long getCurTimeStamp() throws Exception {
        return cal.getTimeInMillis();
    }

    /**
     * get current year
     * @return year
     * @throws Exception
     */
    public static int getCurYear() throws Exception {
        return cal.get(Calendar.YEAR);
    }

    /**
     * get current month
     * @return current month
     * @throws Exception
     */
    public static int getCurMonth() throws Exception {
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * get the current day
     * @return
     * @throws Exception
     */
    public static int getCurDay() throws Exception {
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * get the current day of week
     * @return day of week
     * @throws Exception
     */
    public static int getCurWeekDay() throws Exception {
        int[] week = {0, 7, 1, 2, 3, 4, 5, 6};
        int day = cal.get(Calendar.DAY_OF_WEEK);
        return week[day];
    }

    /**
     * get date string
     * @return date string
     * @throws Exception
     */
    public static String getCurDateString() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(cal.getTime());
    }

    /**
     * get time string
     * @return time string
     * @throws Exception
     */
    public static String getCurTimeString() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(cal.getTime());
    }

    /**
     * compare two time string
     * @param time1
     * @param time2
     * @return time string
     * @throws Exception
     */
    public static int compareTime(String time1, String time2) throws Exception {
        String[] st1 = time1.split(":");
        String[] st2 = time2.split(":");
        for(int i = 0; i <= 2; i++) {
            int it1 = Integer.valueOf(st1[i]);
            int it2 = Integer.valueOf(st2[i]);
            if(it1 > it2) {
                return 1;
            } else if(it1 < it2) {
                return -1;
            } else {
                /*no op*/
                /*continue compare next bit*/
            }
        }
        return 0;
    }

    /**
     * get the random time between t1 and t2
     * @param st1
     * @param st2
     * @return
     * @throws Exception
     */
    public static String getRandomTime(String st1, String st2) throws Exception {
        Time t1 = Time.valueOf(st1);
        Time t2 = Time.valueOf(st2);
        long l1 = t1.getTime();
        long l2 = t2.getTime();
        if(l1 > l2) {
            long tmp = l1;
            l1 = l2;
            l2 = tmp;
        }
        Random random = new Random();
        long delta = random.nextLong() % (l2 - l1 + 1);
        while (delta < 0) {
            delta = random.nextLong() % (l2 - l1 + 1);
        }
        long l3 = l1 + delta;
        Time t3 = new Time(l3);
        return t3.toString();
    }

    /**
     * get the specialized date
     * @return date
     */
    public static Date getDate(int year, int month, int day, String timeStr) throws Exception {
        String[] ts = timeStr.split(":");
        int hour = Integer.valueOf(ts[0]);
        int minute = Integer.valueOf(ts[1]);
        int second = Integer.valueOf(ts[2]);
        Calendar c = Calendar.getInstance();
        c.set(year, month, day, hour, minute, second);
        Date date = c.getTime();
        return date;
    }
}
