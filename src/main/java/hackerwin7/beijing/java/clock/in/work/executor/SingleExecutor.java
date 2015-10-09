package hackerwin7.beijing.java.clock.in.work.executor;

import hackerwin7.beijing.java.clock.in.work.process.single.ClocksIn7DaysSingle;
import hackerwin7.beijing.java.clock.in.work.utils.data.ClockConstants;
import hackerwin7.beijing.java.magpie.client.migration.utils.file.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * single executor
 * Created by hp on 6/19/15.
 */
public class SingleExecutor {
    private static Logger logger = Logger.getLogger(SingleExecutor.class);

    private static boolean running = true;
    public static void main(String[] args) throws Exception {
        PropertyConfigurator.configure(FileUtils.file2in(ClockConstants.LOG4J_FILE, ClockConstants.LOG4J_SHELL));
        ClocksIn7DaysSingle handler =  new ClocksIn7DaysSingle();
        while (running) {
            handler.init();
            try {
                while (true) {
                    handler.run();
                    Thread.sleep(10000);
                }
            } catch (Throwable e) {
                logger.error(e.getMessage(), e);
            }
            Thread.sleep(5000);
        }
    }
}
