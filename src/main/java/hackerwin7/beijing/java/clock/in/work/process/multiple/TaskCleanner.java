package hackerwin7.beijing.java.clock.in.work.process.multiple;

import hackerwin7.beijing.java.clock.in.work.utils.data.TaskInfo;
import org.apache.log4j.Logger;

import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by hp on 6/29/15.
 */
public class TaskCleanner {
    private Logger logger = Logger.getLogger(TaskCleanner.class);
    private static int signal = 0;

    public static void start(final List<List<TaskInfo>> lists) throws Exception {
        if(signal == 0) {
            Calendar everyDay = Calendar.getInstance();
            everyDay.set(Calendar.HOUR_OF_DAY, 0);
            everyDay.set(Calendar.MINUTE, 0);
            everyDay.set(Calendar.SECOND, 0);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    for (List<TaskInfo> list : lists) {
                        list.clear();
                    }
                }
            }, everyDay.getTime(), 24 * 3600 * 1000);
            signal = 1;
        }
    }
}
