package ru.btc.utils.excel2csv;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: Vova
 * Date: 28.01.14
 * Time: 2:38
 */


public class Test {
    public static void main(String[] args) throws SQLException {
        DataBaseWorker dataBaseWorker = new DataBaseWorker("jdbc:oracle:thin:@127.0.0.1:2621:pega", "aptp", "prpc2184", 6);
        dataBaseWorker.execSqlInBatch("insert into tst(val) values ('A')");
        dataBaseWorker.execSqlInBatch("insert into tst(val) values ('A')");
        dataBaseWorker.execSqlInBatch("insert into tst(val) values ('A')");
        dataBaseWorker.execSqlInBatch("insert into tst(val) values ('B')");
        dataBaseWorker.execSqlInBatch("insert into tst(val) values ('C')");
        dataBaseWorker.execSqlInBatch("insert into tst(val) values ('D')");
        dataBaseWorker.commitAllSkipErrors();
        dataBaseWorker.close();
    }
}
