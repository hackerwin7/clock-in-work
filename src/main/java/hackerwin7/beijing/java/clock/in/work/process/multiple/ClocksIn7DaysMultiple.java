package hackerwin7.beijing.java.clock.in.work.process.multiple;

import hackerwin7.beijing.java.clock.in.work.utils.data.ClockConstants;
import hackerwin7.beijing.java.clock.in.work.utils.data.TaskInfo;
import hackerwin7.beijing.java.clock.in.work.utils.data.TaskInfoList;
import hackerwin7.beijing.java.magpie.client.migration.utils.file.FileUtils;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * main process for multiple clock
 * Created by hp on 6/29/15.
 */
public class ClocksIn7DaysMultiple {
    private Logger logger = Logger.getLogger(ClocksIn7DaysMultiple.class);

    /*data*/
    private boolean frameRunning = true;
    private boolean running = true;

    private TaskInfoList givenList = null;
    private List<TaskInfo> pendList = null;
    private List<TaskInfo> completeList = null;
    private TimerTaskPool pool = null;

    /*external driver*/
    private MysqlScanner scanner = null;
    private TaskSubmitter submitter = null;

    /**
     * constructor
     * @throws Exception
     */
    public ClocksIn7DaysMultiple() throws Exception {
        init();
    }

    /**
     * init the config
     * @throws Exception
     */
    private void init() throws Exception {
        initCommon();
        initConfig();
    }

    /**
     * init the common variables
     * @throws Exception
     */
    private void initCommon() throws Exception {
        givenList = new TaskInfoList();
        pendList = new LinkedList<TaskInfo>();
        completeList = new LinkedList<TaskInfo>();
        pool = new TimerTaskPool();
        List<List<TaskInfo>> lists = new LinkedList<List<TaskInfo>>();
        lists.add(givenList.getTaskList());
        lists.add(pendList);
        lists.add(completeList);
        TaskCleanner.start(lists);
    }

    /**
     * initialize some configurations from the config file
     * @throws Exception
     */
    private void initConfig() throws Exception {
        InputStream is = FileUtils.file2in(ClockConstants.CONF_FILE, ClockConstants.CONFI_SHELL);
        Properties pro = new Properties();
        pro.load(is);
        String mysqlHost = pro.getProperty("mysql.host");
        int mysqlPort = Integer.valueOf(pro.getProperty("mysql.port"));
        String mysqlUsr = pro.getProperty("mysql.usr");
        String mysqlPsd = pro.getProperty("mysql.psd");
        String mysqlDb = pro.getProperty("mysql.database.name");
        String mysqlTb = pro.getProperty("mysql.table.name");
        scanner = new MysqlScanner(mysqlHost, mysqlUsr, mysqlPsd, mysqlPort, mysqlDb, mysqlTb);
        submitter = new TaskSubmitter(pendList, completeList, pool);
    }

    /**
     * per turn execute the run() once
     * 1.scan the mysql and get the source task list
     * 2.compare the givenList, pendList and the completeList to decide what tasks will be submitted, deleted or updated
     * @throws Exception
     */
    private void run() throws Exception {
        givenList = scanSrc();
        analysisAndSubmit();
    }

    /**
     * scan the mysql and get the source task list
     * @throws Exception
     */
    private TaskInfoList scanSrc() throws Exception {
        return scanner.scan();
    }

    /**
     * analysis the three lists and submit the task
     * @throws Exception
     */
    private void analysisAndSubmit() throws Exception {
        submitter.scanAndSubmit(givenList.getTaskList());
    }

    /**
     * close the driver
     * @throws Exception
     */
    private void close() throws Exception {
        givenList.clear();
        pendList.clear();
        completeList.clear();
        scanner.close();
    }

    /**
     * main process for this framework
     * @throws Exception
     */
    public void start() throws Exception {
        while (frameRunning) {
            try {
                init();
                while (running) {
                    run();
                    Thread.sleep(10000);
                }
            } catch (Throwable e) {
                logger.error("framework catch exception : " + e.getMessage(), e);
            } finally {
                close();
            }
        }
    }

    /**
     * setter
     * @param frameRunning
     */
    public void setFrameRunning(boolean frameRunning) {
        this.frameRunning = frameRunning;
    }

    /**
     * setter
     * @param running
     */
    public void setRunning(boolean running) {
        this.running = running;
    }
}
