package hackerwin7.beijing.java.clock.in.work.process.single;

import hackerwin7.beijing.java.clock.in.work.utils.clock.ClocksIn;
import hackerwin7.beijing.java.clock.in.work.utils.data.ClockConstants;
import hackerwin7.beijing.java.clock.in.work.utils.data.ClockTime;
import hackerwin7.beijing.java.magpie.client.migration.utils.file.FileUtils;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

/**
 * single user 's clock program
 * Created by hp on 6/19/15.
 */
public class ClocksIn7DaysSingle {
    private Logger logger = Logger.getLogger(ClocksIn7DaysSingle.class);

    /*external data*/
    private ClockTime[] clockMorningWeek = null;
    private ClockTime[] clockEveningWeek = null;
    private String name = "";
    private String password = "";
    private String logUrl = "";
    private String clockUrl = "";

    /*driver*/
    ClocksIn clock = null;

    /**
     * constructor
     * @throws Exception
     */
    public ClocksIn7DaysSingle() throws Exception {
        clockMorningWeek = new ClockTime[7];
        clockEveningWeek = new ClockTime[7];
        clock = new ClocksIn();
    }

    /**
     * load the config and initialize the driver
     * @throws Exception
     */
    public void init() throws Exception {
        logger.info("initializing......");
        InputStream is = FileUtils.file2in(ClockConstants.CONF_FILE, ClockConstants.CONFI_SHELL);
        Properties pro = new Properties();
        pro.load(is);
        logUrl = pro.getProperty("page.login.url");
        name = pro.getProperty("page.login.username");
        password = pro.getProperty("page.login.password");
        clockUrl = pro.getProperty("page.kaoqin.url");
        clockMorningWeek[0] = new ClockTime(pro.getProperty("monday.morning.start"), pro.getProperty("monday.morning.end"));
        clockEveningWeek[0] = new ClockTime(pro.getProperty("monday.evening.start"), pro.getProperty("monday.evening.end"));
        clockMorningWeek[1] = new ClockTime(pro.getProperty("tuesday.morning.start"), pro.getProperty("tuesday.morning.end"));
        clockEveningWeek[1] = new ClockTime(pro.getProperty("tuesday.evening.start"), pro.getProperty("tuesday.evening.end"));
        clockMorningWeek[2] = new ClockTime(pro.getProperty("wednesday.morning.start"), pro.getProperty("wednesday.morning.end"));
        clockEveningWeek[2] = new ClockTime(pro.getProperty("wednesday.evening.start"), pro.getProperty("wednesday.evening.end"));
        clockMorningWeek[3] = new ClockTime(pro.getProperty("thursday.morning.start"), pro.getProperty("thursday.morning.end"));
        clockEveningWeek[3] = new ClockTime(pro.getProperty("thursday.evening.start"), pro.getProperty("thursday.evening.end"));
        clockMorningWeek[4] = new ClockTime(pro.getProperty("friday.morning.start"), pro.getProperty("friday.morning.end"));
        clockEveningWeek[4] = new ClockTime(pro.getProperty("friday.evening.start"), pro.getProperty("friday.evening.end"));
        clockMorningWeek[5] = new ClockTime(pro.getProperty("saturday.morning.start"), pro.getProperty("saturday.morning.end"));
        clockEveningWeek[5] = new ClockTime(pro.getProperty("saturday.evening.start"), pro.getProperty("saturday.evening.end"));
        clockMorningWeek[6] = new ClockTime(pro.getProperty("sunday.morning.start"), pro.getProperty("sunday.morning.end"));
        clockEveningWeek[6] = new ClockTime(pro.getProperty("sunday.evening.start"), pro.getProperty("sunday.evening.end"));
        is.close();
    }

    /**
     * get the day for week
     * @param cur
     * @return day of week
     * @throws Exception
     */
    private int getDayOfWeek(long cur) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(cur);
        int days = cal.get(Calendar.DAY_OF_WEEK);
        int ret = days - 2;//in java sunday is the first day
        if(ret < 0) {
            ret = 6;
        }
        return ret;
    }

    /**
     * get the execute time
     * @param cur
     * @return
     * @throws Exception
     */
    private long getExecuteTime(long cur, ClockTime day) throws Exception {
        long len = day.getEndTimeStamp() - cur;
        if(len == 0) {
            len = 1;
        }
        if(len < 0) {
            len = 0 - len;
        }
        Random random = new Random();
        long delta = random.nextLong() % len;
        while (delta < 0) {
            delta = random.nextLong() % len;
        }
        return cur + delta;
    }

    /**
     * scan the memory , if condition is true for time then clock
     * @throws Exception
     */
    public void run() throws Exception {
        logger.info("running...... user = " + name);
        long cur = System.currentTimeMillis();
        int dayIndex = getDayOfWeek(cur);
        Time curTime = new Time(cur);
        long curs = curTime.getTime();
        logger.info("time : cur = " + new Date(cur) + ", curs = " + curTime + ", morning start = " + clockMorningWeek[dayIndex].getStartTime() + ", morning end = " + clockMorningWeek[dayIndex].getEndTime());
        logger.info("timestamp : cur = " + cur + ", curs = " + curs + ", morning start = " + clockMorningWeek[dayIndex].getStartTimeStamp() + ", morning end = " + clockMorningWeek[dayIndex].getEndTimeStamp());
        logger.info("time : cur = " + new Date(cur) + ", curs = " + curTime + ", evening start = " + clockEveningWeek[dayIndex].getStartTime() + ", evening end = " + clockEveningWeek[dayIndex].getEndTime());
        logger.info("timestamp : cur = " + cur + ", curs = " + curs + ", evening start = " + clockEveningWeek[dayIndex].getStartTimeStamp() + ", evening end = " + clockEveningWeek[dayIndex].getEndTimeStamp());
        //morning clock
        if(cur >= clockMorningWeek[dayIndex].getStartTimeStamp() && cur <= clockMorningWeek[dayIndex].getEndTimeStamp()) {
            long exeTime = getExecuteTime(cur, clockMorningWeek[dayIndex]);
            Thread.sleep(exeTime - cur);
            clock.asyncClocks(logUrl, name, password, clockUrl);
        }
        //evening clock
        if(cur >= clockEveningWeek[dayIndex].getStartTimeStamp() && cur <= clockEveningWeek[dayIndex].getEndTimeStamp()) {
            long exeTime = getExecuteTime(cur, clockEveningWeek[dayIndex]);
            Thread.sleep(exeTime - cur);
            clock.asyncClocks(logUrl, name, password, clockUrl);
        }
    }

    /**
     * setter
     * @param clockMorningWeek
     */
    public void setClockMorningWeek(ClockTime[] clockMorningWeek) {
        this.clockMorningWeek = clockMorningWeek;
    }

    /**
     * setter
     * @param clockEveningWeek
     */
    public void setClockEveningWeek(ClockTime[] clockEveningWeek) {
        this.clockEveningWeek = clockEveningWeek;
    }

    /**
     * setter
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * setter
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * setter
     * @param logUrl
     */
    public void setLogUrl(String logUrl) {
        this.logUrl = logUrl;
    }

    /**
     * setter
     * @param clockUrl
     */
    public void setClockUrl(String clockUrl) {
        this.clockUrl = clockUrl;
    }

    /**
     * setter
     * @param clock
     */
    public void setClock(ClocksIn clock) {
        this.clock = clock;
    }
}
