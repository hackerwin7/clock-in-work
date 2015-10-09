package hackerwin7.beijing.java.clock.in.work.utils.data;

/**
 * Created by hp on 6/23/15.
 */
public class TaskInfo {

    /*data*/
    private String usr = "";
    private String psd = "";
    private ClockTime cTime = null;
    public final int tTag;// 0 is for morning, 1 is for evening
    protected String loginUrl = "";
    protected String clockUrl = "";

    /**
     * constructor
     * @param _usr
     * @param _psd
     * @param _cTime
     * @throws Exception
     */
    public TaskInfo(String _usr, String _psd, ClockTime _cTime, int _tag) throws Exception {
        usr = _usr;
        psd = _psd;
        cTime = _cTime;
        tTag = _tag;
    }

    /**
     * constructor
     * @param _usr
     * @param _psd
     * @param startTime
     * @param endTime
     * @throws Exception
     */
    public TaskInfo(String _usr, String _psd, String startTime, String endTime, int _tag) throws Exception {
        usr = _usr;
        psd = _psd;
        cTime = new ClockTime(startTime, endTime);
        tTag = _tag;
    }

    /**
     * constructor
     * @param _usr
     * @param _psd
     * @param _startTime
     * @param _endTime
     * @param _tag
     * @param _loginUrl
     * @param _clockUrl
     * @throws Exception
     */
    public TaskInfo(String _usr, String _psd, String _startTime, String _endTime, int _tag, String _loginUrl, String _clockUrl) throws Exception {
        usr = _usr;
        psd = _psd;
        cTime = new ClockTime(_startTime, _endTime);
        tTag = _tag;
        loginUrl = _loginUrl;
        clockUrl = _clockUrl;
    }

    /**
     * task id = user + password + startTime + endTime
     * @return return the task id
     * @throws Exception
     */
    public String getTaskId() throws Exception {
        return usr + psd + cTime.getStartTime() + cTime.getEndTime();
    }

    /**
     * get the tag id
     * @return tag id
     * @throws Exception
     */
    public String getTaskTagId() throws Exception {
        return usr + psd + tTag;
    }

    /**
     * get start time
     * @return start time
     * @throws Exception
     */
    public String getStartTime() throws Exception {
        return cTime.getStartTime();
    }

    /**
     * get end time
     * @return end time
     * @throws Exception
     */
    public String getEndTime() throws Exception {
        return cTime.getEndTime();
    }

    /**
     * getter
     * @return clock url
     */
    public String getClockUrl() {
        return clockUrl;
    }

    /**
     * getter
     * @return login url
     */
    public String getLoginUrl() {
        return loginUrl;
    }

    /**
     * getter
     * @return username
     */
    public String getUsr() {
        return usr;
    }

    /**
     * getter
     * @return password
     */
    public String getPsd() {
        return psd;
    }
}
