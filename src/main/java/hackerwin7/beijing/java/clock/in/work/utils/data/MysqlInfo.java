package hackerwin7.beijing.java.clock.in.work.utils.data;

/**
 * Created by hp on 6/23/15.
 */
public class MysqlInfo {

    /*data*/
    private String host = "";
    private String username = "";
    private String password = "";
    private String dbname = "punch";
    private String tbname = "punch_clock";
    private int port = 3306;
    private String driverName = "com.mysql.jdbc.Driver";

    /**
     * constructor
     * @param _host
     * @param _user
     * @param _pass
     * @param _port
     * @throws Exception
     */
    public MysqlInfo(String _host, String _user, String _pass, int _port) throws Exception {
        host = _host;
        username = _user;
        password = _pass;
        port = _port;
    }

    public MysqlInfo(String _host, String _usr, String _psd, int _port, String _db, String _tb) throws Exception {
        host = _host;
        username = _usr;
        password = _psd;
        port = _port;
        dbname = _db;
        tbname = _tb;
    }

    /**
     * getter
     * @return host
     */
    public String getHost() {
        return host;
    }

    /**
     * getter
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * getter
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * getter
     * @return port
     */
    public int getPort() {
        return port;
    }

    /**
     * getter
     * @return table name
     */
    public String getTbname() {
        return tbname;
    }

    /**
     * getter
     * @return database name
     */
    public String getDbname() {
        return dbname;
    }

    /**
     * getter
     * @return driver name
     */
    public String getDriverName() {
        return driverName;
    }

    /**
     * setter
     * @param dbname
     */
    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    /**
     * setter
     * @param tbname
     */
    public void setTbname(String tbname) {
        this.tbname = tbname;
    }

    /**
     * get the connection url
     * @return connection url
     * @throws Exception
     */
    public String getUrl() throws Exception {
        return "jdbc:mysql://" + host + "/" + dbname;
    }
}
