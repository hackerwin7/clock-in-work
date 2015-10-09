package hackerwin7.beijing.java.clock.in.work.process.multiple;

import hackerwin7.beijing.java.clock.in.work.utils.clock.ClocksIn;
import hackerwin7.beijing.java.clock.in.work.utils.data.TaskInfo;
import org.apache.log4j.Logger;

import java.util.TimerTask;

/**
 * Created by hp on 6/24/15.
 */
public class ClockTimerTask extends TimerTask {
    private Logger logger = Logger.getLogger(ClockTimerTask.class);

    /*info*/
    private TaskInfo info = null;

    /**
     * constructor
     * @param _info
     * @throws Exception
     */
    public ClockTimerTask(TaskInfo _info) throws Exception {
        info = _info;
    }

    /**
     * clock the punch
     */
    @Override
    public void run() {
        try {
            ClocksIn clocksIn = new ClocksIn();
            clocksIn.asyncClocks(info.getLoginUrl(), info.getUsr(), info.getPsd(), info.getClockUrl());
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * get the task info
     * @return info
     */
    public TaskInfo getInfo() {
        return info;
    }
}
