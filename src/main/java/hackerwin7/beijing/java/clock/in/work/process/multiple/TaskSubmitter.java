package hackerwin7.beijing.java.clock.in.work.process.multiple;

import hackerwin7.beijing.java.clock.in.work.utils.data.TaskInfo;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * submit the task by
 * compare the pending list and complete list and given list
 * Created by hp on 6/24/15.
 */
public class TaskSubmitter {
    private Logger logger = Logger.getLogger(TaskSubmitter.class);

    /*list data*/
    private List<TaskInfo> pendingList = null;
    private List<TaskInfo> completeList = null;

    /*timer pool*/
    private TimerTaskPool taskPool = null;

    /**
     * constructor
     * @param pending
     * @param complete
     * @throws Exception
     */
    public TaskSubmitter(List<TaskInfo> pending, List<TaskInfo> complete, TimerTaskPool _pool) throws Exception {
        pendingList = pending;
        completeList = complete;
        taskPool = _pool;
    }

    /**
     * scan the given list and pending list and complete list
     * if given list of the task tag id that the pending and complete list do not exists
     *     then start the task timer and add to the pending list , add to the task pool
     * else if pending list or complete exists
     *     then
     *     if given list of the task id that the pending and complete list do not exists
     *         then start the timer task and add to the pending list , and add to the timer task pool
     *     else if given list of the task id that is different from the pending list's task id
     *         then stop and remove the old task from pending list and task pool, besides start the new task add to pending list and addn to the task pool
     *     else if given list of the task id that is different from the complete list's task id
     *         then start the timer task and add to the pending list , and add to the timer task pool
     * @param givenList
     * @throws Exception
     */
    public void scanAndSubmit(List<TaskInfo> givenList) throws Exception {
        for(TaskInfo givenTask : givenList) {
            boolean isPendingTagExists = false;
            boolean isPendingIdExists = false;
            for(TaskInfo pendingTask : pendingList) {
                if(givenTask.getTaskTagId().equals(pendingTask.getTaskTagId())) {
                    isPendingTagExists = true;
                    break;
                }
                if(givenTask.getTaskId().equals(pendingTask.getTaskId())) {
                    isPendingIdExists = true;
                    break;
                }
            }
            boolean isCompleteTagExists = false;
            boolean isCompleteIdExists = false;
            for(TaskInfo completeTask : completeList) {
                if(givenTask.getTaskTagId().equals(completeTask.getTaskTagId())) {
                    isCompleteTagExists = true;
                    break;
                }
                if(givenTask.getTaskId().equals(completeTask.getTaskId())) {
                    isCompleteIdExists = true;
                    break;
                }
            }
            if(!isPendingTagExists && !isCompleteTagExists) {
                submitTask(givenTask);
            } else {
                if(!isPendingIdExists && !isCompleteIdExists) {
                    submitTask(givenTask);
                } else if(isPendingTagExists && !isPendingIdExists) {
                    killTask(givenTask);
                    submitTask(givenTask);
                } else if(isCompleteTagExists && !isCompleteIdExists) {
                    submitTask(givenTask);
                }
            }
        }
    }

    /**
     * submit the task
     * 1.start the timer task and add to timer object the task pool
     * 2.add the info to the pending list
     * @param info
     * @throws Exception
     */
    private void submitTask(TaskInfo info) throws Exception {
        taskPool.submit(info);
        pendingList.add(info);
    }

    /**
     * kill the timer task in the task pool
     * remove the info int the pending list
     * @param info
     * @throws Exception
     */
    private void killTask(TaskInfo info) throws Exception {
        taskPool.kill(info);
        for(TaskInfo pending : pendingList) {
            if(pending.getTaskTagId().equals(info.getTaskTagId())) {
                pendingList.remove(pending);
            }
        }
    }
}
