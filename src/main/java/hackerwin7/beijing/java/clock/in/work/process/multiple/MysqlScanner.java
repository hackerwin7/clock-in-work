package hackerwin7.beijing.java.clock.in.work.process.multiple;

import hackerwin7.beijing.java.clock.in.work.utils.data.MysqlInfo;
import hackerwin7.beijing.java.clock.in.work.utils.data.TaskInfo;
import hackerwin7.beijing.java.clock.in.work.utils.data.TaskInfoList;
import hackerwin7.beijing.java.clock.in.work.utils.date.DateTimeUtils;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by hp on 6/23/15.
 */
public class MysqlScanner {
    private Logger logger= Logger.getLogger(MysqlScanner.class);

    /*mysql data*/
    private MysqlInfo myInfo = null;

    /*mysql driver*/
    private Connection conn = null;
    private Statement stmt = null;

    /**
     * constructor
     * @param host
     * @param usr
     * @param psd
     * @param port
     * @param db
     * @param tb
     * @throws Exception
     */
    public MysqlScanner(String host, String usr, String psd, int port, String db, String tb) throws Exception {
        myInfo = new MysqlInfo(host, usr, psd, port, db, tb);
        Class.forName(myInfo.getDriverName());
        conn = DriverManager.getConnection(myInfo.getUrl(), myInfo.getUsername(), myInfo.getPassword());
        stmt = conn.createStatement();
    }

    /**
     * scan the mysql and construct to the task list (not pending list)
     * weekday 0 is everyday ,  -1 is working day(week 1 ~ 5) , 1 ~ 7 (week 1 ~ 7)
     * @return task list
     * @throws Exception
     */
    public TaskInfoList scan() throws Exception {
        TaskInfoList list = new TaskInfoList();
        String sql = "select * from " + myInfo.getDbname() + "." + myInfo.getTbname();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            TaskInfo info = null;
            String username = rs.getString("username");
            String password = rs.getString("password");
            String morningStart = rs.getString("morning_start");
            String morningEnd = rs.getString("morning_end");
            String eveningStart = rs.getString("evening_start");
            String eveingEnd = rs.getString("evening_end");
            String loginUrl = rs.getString("login_url");
            String clockUrl = rs.getString("clock_url");
            int weekDay = rs.getInt("week_day");
            info = getInfoData(username, password, morningStart, morningEnd, weekDay, 0, loginUrl, clockUrl);
            list.add(info);
            info = getInfoData(username, password, eveningStart, eveingEnd, weekDay, 1, loginUrl, clockUrl);
            list.add(info);
        }
        return list;
    }

    /**
     * get the a single task info
     * check it, if the time < current time
     * then return null task info
     * week for 0 is every day , -1 is working day
     * @param usr
     * @param psd
     * @param start
     * @param end
     * @param week
     * @param tag
     * @param loginUrl
     * @param clockUrl
     * @return
     * @throws Exception
     */
    private TaskInfo getInfoData(String usr, String psd, String start, String end, int week, int tag, String loginUrl, String clockUrl) throws Exception {
        if(week == 0) {
            /*no op*/
        } else if(week == -1) {
            int weekDay = DateTimeUtils.getCurWeekDay();
            if(weekDay == 6 || weekDay == 7) {
                return null;
            }
        } else {
            if(week != DateTimeUtils.getCurWeekDay()) {
                return null;
            }
        }
        if(DateTimeUtils.compareTime(DateTimeUtils.getCurTimeString(), end) > 0) {// if current day the clock time < current time, it can't clock punch in time
            return null;
        }
        TaskInfo info = new TaskInfo(usr, psd, start, end, tag, loginUrl, clockUrl);
        return info;
    }

    /**
     * close the connection
     * @throws Exception
     */
    public void close() throws Exception {
        stmt.close();
        conn.close();
    }
}
