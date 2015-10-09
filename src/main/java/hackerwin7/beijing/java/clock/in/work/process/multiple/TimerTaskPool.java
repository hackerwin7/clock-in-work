package hackerwin7.beijing.java.clock.in.work.process.multiple;

import hackerwin7.beijing.java.clock.in.work.utils.data.TaskInfo;
import hackerwin7.beijing.java.clock.in.work.utils.date.DateTimeUtils;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;

/**
 * Created by hp on 6/24/15.
 */
public class TimerTaskPool {
    private Logger logger = Logger.getLogger(TimerTaskPool.class);

    /*timer pool for list*/
    private List<ClockTimerTask> taskList = new LinkedList<ClockTimerTask>();

    /*timer*/
    private Timer timer = new Timer();

    /**
     * submit the timer, and the timer task is started,  add the object to the task pool
     * @param info
     * @throws Exception
     */
    public void submit(TaskInfo info) throws Exception {
        String rdTime = DateTimeUtils.getRandomTime(info.getStartTime(), info.getEndTime());
        Date startDate = DateTimeUtils.getDate(DateTimeUtils.getCurYear(), DateTimeUtils.getCurMonth(), DateTimeUtils.getCurDay(), rdTime);
        ClockTimerTask task = new ClockTimerTask(info);
        timer.schedule(task, startDate);
        taskList.add(task);
    }

    /**
     * kill the timer, and remove the task pool according to task tag id (or task id ??)
     * @param info
     * @throws Exception
     */
    public void kill(TaskInfo info) throws Exception {
        for(ClockTimerTask task : taskList) {
            if(task.getInfo().getTaskTagId().equals(info.getTaskTagId())) {
                task.cancel();
                taskList.remove(task);
            }
        }
    }
}
