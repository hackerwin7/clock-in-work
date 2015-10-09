package hackerwin7.beijing.java.clock.in.work.utils.data;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hp on 6/23/15.
 */
public class TaskInfoList {
    /*data*/
    private List<TaskInfo> taskList = new LinkedList<TaskInfo>();

    /**
     * clear the list
     * @throws Exception
     */
    public void clear() throws Exception {
        taskList.clear();
    }

    /**
     * add the task information
     * @param task
     * @throws Exception
     */
    public void add(TaskInfo task) throws Exception {
        if(task != null) {
            taskList.add(task);
        }
    }

    /**
     * get the list data
     * @return task list
     */
    public List<TaskInfo> getTaskList() {
        return taskList;
    }
}
