package hackerwin7.beijing.java.clock.in.work.executor;

import hackerwin7.beijing.java.clock.in.work.process.multiple.ClocksIn7DaysMultiple;
import hackerwin7.beijing.java.clock.in.work.utils.data.ClockConstants;
import hackerwin7.beijing.java.magpie.client.migration.utils.file.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Created by hp on 6/29/15.
 */
public class MultipleExecutor {
    private Logger logger = Logger.getLogger(MultipleExecutor.class);

    /**
     * main process
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        PropertyConfigurator.configure(FileUtils.file2in(ClockConstants.LOG4J_FILE, ClockConstants.LOG4J_SHELL));
        ClocksIn7DaysMultiple handler = new ClocksIn7DaysMultiple();
        handler.start();
    }
}
