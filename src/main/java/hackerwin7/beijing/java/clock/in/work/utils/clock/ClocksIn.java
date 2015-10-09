package hackerwin7.beijing.java.clock.in.work.utils.clock;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import org.apache.log4j.Logger;

/**
 * Created by hp on 6/19/15.
 */
public class ClocksIn {
    private Logger logger = Logger.getLogger(ClocksIn.class);

    /*external data*/

    /*driver*/
    private WebClient browser = null;

    /**
     * constructor and initialize the browser
     * @throws Exception
     */
    public ClocksIn() throws Exception {
        browser = new WebClient(BrowserVersion.CHROME);
    }

    /**
     * originally clocks in
     * @param logUrl
     * @param usr
     * @param psd
     * @param clockUrl
     * @return clock is successful
     * @throws Exception
     */
    private boolean clocks(String logUrl, String usr, String psd, String clockUrl) throws Exception {
        //login by using the username and password
        HtmlPage logPage = browser.getPage(logUrl);
        HtmlElement usrEle = logPage.getHtmlElementById("Name");
        usrEle.click();
        usrEle.type(usr);
        HtmlElement psdEle = logPage.getHtmlElementById("Password");
        psdEle.click();
        psdEle.type(psd);
        HtmlSubmitInput inBtn = logPage.getHtmlElementById("Logon");
        Page resLogPage = inBtn.click();
        if(resLogPage.getWebResponse().getStatusMessage().equalsIgnoreCase("ok")) {
            logger.info("login successful by user = " + usr);
        } else {
            logger.error("login failed by user = " + usr);
            return false;
        }
        //clock the kaoqin
        HtmlPage clockPage = browser.getPage(clockUrl);
        HtmlAnchor cloBtn = clockPage.getHtmlElementById("clockIn");
        Page resClockPage = cloBtn.click();
        browser.waitForBackgroundJavaScript(10000);
        if(resClockPage.getWebResponse().getStatusMessage().equalsIgnoreCase("ok")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * new thread direct call the clocks and ignore the return value
     * @param logUrl
     * @param usr
     * @param psd
     * @param clockUrl
     * @throws Exception
     */
    public void asyncClocks(final String logUrl, final String usr, final String psd, final String clockUrl) throws Exception {
        Thread clockThread = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean status = false;
                try {
                    status = clocks(logUrl, usr, psd, clockUrl);
                } catch (Exception e) {
                    logger.error("clock failed ...... " + e.getMessage(), e);
                } finally {
                    logger.info("async clocks in's status = " + status + ", in user = " + usr);
                }
            }
        });
        clockThread.start();
    }

    /**
     * call the clocks, and return the status value , so you can deal it by different return value with different action
     * @param logUrl
     * @param usr
     * @param psd
     * @param clockUrl
     * @return clock is successful
     * @throws Exception
     */
    public boolean syncClocks(String logUrl, String usr, String psd, String clockUrl) throws Exception {
        return clocks(logUrl, usr, psd, clockUrl);
    }
}
