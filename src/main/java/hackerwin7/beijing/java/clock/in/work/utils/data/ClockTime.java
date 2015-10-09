package hackerwin7.beijing.java.clock.in.work.utils.data;

import org.apache.commons.lang3.StringUtils;

import java.sql.Time;
import java.util.Calendar;

/**
 * Created by hp on 6/19/15.
 */
public class ClockTime {

    /*external data*/
    private Time startTime = null;
    private Time endTime = null;

    /**
     * constructor
     * @param _start
     * @param _end
     * @throws Exception
     */
    public ClockTime(Time _start, Time _end) throws Exception {
        startTime = new Time(_start.getTime());
        endTime = new Time(_end.getTime());
    }

    /**
     * constructor
     * @param start
     * @param end
     * @throws Exception
     */
    public ClockTime(String start, String end) throws Exception {
        if(!StringUtils.isBlank(start)) {
            if (start.split(":").length != 3) {
                if (start.split(":").length == 2) {
                    start = start + ":00";
                } else if (start.split(":").length == 1) {
                    start = start + ":00:00";
                } else {
                    throw new Exception("start time format error : start = " + start.toString());
                }
            }
            startTime = Time.valueOf(start);
        } else {
            startTime = null;
        }
        if(!StringUtils.isBlank(end)) {
            if (end.split(":").length != 3) {
                if (end.split(":").length == 2) {
                    end = end + ":00";
                } else if (end.split(":").length == 1) {
                    end = end + ":00:00";
                } else {
                    throw new Exception("start time format error : start = " + end.toString());
                }
            }
            endTime = Time.valueOf(end);
        } else {
            endTime = null;
        }

        /*if start == null and end != nulll then start = end*/
        if(startTime == null && endTime != null) {
            startTime = Time.valueOf(endTime.toString());
        }
        if(startTime != null && endTime == null) {
            endTime = Time.valueOf(startTime.toString());
        }
    }

    /**
     * getter
     * @return start time
     * @throws Exception
     */
    public String getStartTime() throws Exception {
        if(isBlank()) {
            return null;
        } else {
            return startTime.toString();
        }
    }

    /**
     * getter
     * @return start time stamp
     * @throws Exception
     */
    public long getStartTimeStamp() throws Exception {
        if(isBlank()) {
            return 0;
        } else {
            String[] ss = getStartTime().split(":");
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(ss[0]));
            cal.set(Calendar.MINUTE, Integer.valueOf(ss[1]));
            cal.set(Calendar.SECOND, Integer.valueOf(ss[2]));
            return cal.getTimeInMillis();
        }
    }

    /**
     * getter
     * @return end time
     * @throws Exception
     */
    public String getEndTime() throws Exception {
        if(isBlank()) {
            return null;
        } else {
            return endTime.toString();
        }
    }

    /**
     * getter
     * @return end time stamp
     * @throws Exception
     */
    public long getEndTimeStamp() throws Exception {
        if(isBlank()) {
            return 0;
        } else {
            String[] ss = getEndTime().split(":");
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(ss[0]));
            cal.set(Calendar.MINUTE, Integer.valueOf(ss[1]));
            cal.set(Calendar.SECOND, Integer.valueOf(ss[2]));
            return cal.getTimeInMillis();
        }
    }

    /**
     * if start == null || end == null
     * then it is blank
     * @return isBlank
     * @throws Exception
     */
    public boolean isBlank() throws Exception {
        if(startTime == null && endTime == null) {
            return true;
        } else {
            return false;
        }
    }
}
