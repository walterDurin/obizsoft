package ru.btc.utils.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * User: Vladimir
 * Date: 28.01.14
 * Time: 17:00
 */
public class DataBaseWorker {
    private static DataBaseWorker worker = null;

    private static Log log = LogFactory.getLog(DataBaseWorker.class);

    private String uri = null;
    private String user = null;
    private String pass = null;
    private int batchSize = 50;
    private DataSource ds = null;
    private Connection conn = null;
    private Statement stmt = null;
    private int unsavedRecords = 0;
    private boolean isDirty = false;
    private boolean initialised = false;
    private boolean isClosed = false;
    private double totalCommited = 0;
    private double totalErrorsCount = 0;
    private double executeCount = 0;

    private List<String> sqls = new LinkedList<String>();


    public DataBaseWorker(String uri, String user, String pass, int batchSize) {
        if (worker != null) {
            throw new RuntimeException("DBConnection already initialised! Please make sure that no parallel processes.");
        }
        this.uri = uri;
        this.user = user;
        this.pass = pass;
        this.batchSize = batchSize;
    }

    private void init() throws RuntimeException {
        try {
            if (conn != null || initialised == true) {
                throw new RuntimeException("DataBaseWorker already initialised!");
            }
            Properties connectionProps = new Properties();
            connectionProps.put("user", user);
            connectionProps.put("password", pass);
            conn = DriverManager.getConnection(uri, connectionProps);
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            initialised = true;
        } catch (Exception e) {
            throw new RuntimeException("Can't retrieve DataSource!", e);
        }
    }

    public void execSqlInBatch(String sql) throws SQLException {
        //if(true) return;
        if (isClosed) {
            log.error("Trying to use closed DataTableWorker!");
            throw new RuntimeException("Trying to use closed DataTableWorker!");
        }
        if (!initialised) {
            init();
        }
        sqls.add(sql);
        stmt.addBatch(sql);
        isDirty = true;
        unsavedRecords++;
        if (unsavedRecords == batchSize) {
            //log.info("Save next " + unsavedRecords + " records.");
            commit();
        }
    }

    public void commitAllSkipErrors() {
        while (unsavedRecords > 0) {
            try {
                commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void commit() throws SQLException {
        if (isClosed) {
            throw new RuntimeException("Trying to commit closed DataTableWorker!");
        }
        if (!initialised || conn == null) {
            throw new RuntimeException("Trying to commit non initialized DataTableWorker!");
        }
        if (isDirty) {
            if (unsavedRecords > 0) {
//                log.info("Save next " + unsavedRecords + " records.");
                double statemntsCount = 0;
                try {
                    executeCount++;
                    statemntsCount = stmt.executeBatch().length;
                    stmt.clearWarnings();
                    sqls.clear();
                    stmt.clearBatch();
                    conn.commit();
                    isDirty = false;
                    unsavedRecords = 0;
//                    stmt = conn.createStatement();
                   } catch (BatchUpdateException e) {
                    //log.error("Error when try commit batch: ", e);
                    String wrongSql = "";
                    boolean hasExecuteFailed = false;
                    for (int i = 0; i < e.getUpdateCounts().length; i++) {
                        int state = e.getUpdateCounts()[i];
                        if(state == Statement.EXECUTE_FAILED || state==0) {
                            hasExecuteFailed = true;
                            wrongSql+= "SQL: \"" + sqls.remove(0)+"\"\n" ;
                            totalErrorsCount++;
                        } else {
                            sqls.remove(0);
                        }
                    }
                    if(!hasExecuteFailed) {
                        totalErrorsCount++;
                        wrongSql+= "SQL: \"" + sqls.remove(0)+"\"";
                    }
                    wrongSql = wrongSql.substring(0, wrongSql.length()-1);
                    statemntsCount = unsavedRecords - sqls.size() - totalErrorsCount;
                    log.error("Found error \"" + e.getMessage() + "\n" + wrongSql);
//                    sqls = sqls.subList(sucessCount + 1, sqls.size());
                    stmt.clearWarnings();
                    conn.clearWarnings();
                    conn.commit();
                    stmt.clearBatch();
//                    stmt = conn.createStatement();
                    for (String sql : sqls) {
                        stmt.addBatch(sql);
                    }
                    unsavedRecords = sqls.size();
                    isDirty = unsavedRecords>0;
                } finally {
                    totalCommited+= statemntsCount;
                    log.info("Batch records are saved: " + statemntsCount + "\tIn totoal: "  + totalCommited + "\tErrors: " + totalErrorsCount);
                    conn.clearWarnings();
                    conn.commit();
                }
            }
        }
    }

    public void close() {
    worker = null;
    initialised = false;
    if (isClosed) {
        return;
    }
    log.info("Total commited statements: " + totalCommited);
    log.info("Total errors count: " + totalErrorsCount);
    isClosed = true;
    if (isDirty) {
        log.error("Found dirty data base session state!! Will be rollback.");
        try {
            conn.rollback();
        } catch (Exception e) {
            log.error("Error rollback dirty transaction!", e);
        } finally {
            isDirty = false;
        }
    }
    try {
        try {
            if (conn != null)
                conn.commit();
        } finally {
            if (conn != null && !conn.isClosed())
                conn.close();
        }
    } catch (Exception e) {
        log.error("Found exception when try to close DB resources!", e);
        throw new RuntimeException(e);
    }

}

}
