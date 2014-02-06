package ru.btc.utils.excel2csv;

import com.csvreader.CsvReader;
import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.rarfile.FileHeader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.btc.utils.utils.CompressUtil;
import ru.btc.utils.utils.DataBaseWorker;

import java.io.*;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Vova
 * Date: 28.01.14
 * Time: 0:48
 */
public class LoadCSV {

    private static DataBaseWorker dataBaseWorker;

    private static Log log = LogFactory.getLog(LoadCSV.class);


    public static void main(String[] args) throws IOException, SQLException {
        int argNum = 0;
        String fileName = args[argNum++];
        String fileEncoding = args[argNum++];
        String delimeter = args[argNum++];
//        String recordDelimeter = args[argNum++];
        final String skipFistLine = args[argNum++];
        final String dbTableName = args[argNum++];
        final String dbFields = args[argNum++];
        final String dbValuesFrom = args[argNum++];
        String dbURI = args[argNum++];
        String dbUser = args[argNum++];
        String dbPass = args[argNum++];
        final int rowsToProcess = Integer.parseInt(args[argNum++]);
        final int batchSize = Integer.parseInt(args[argNum++]);
        System.out.println("Параметры запуска:");
        for (String arg : args) {
            System.out.print("\"" + arg + "\" ");
        }

        dataBaseWorker = new DataBaseWorker(dbURI, dbUser, dbPass, batchSize);

        try {

            Date started = new Date();
            System.out.println("\nОткрытие файла: \"" + fileName + "\"..");
            InputStream inputStream;

            inputStream = CompressUtil.getFirstItemInputStream(fileName);

            CsvReader csvReader = new CsvReader(inputStream, Charset.forName(fileEncoding));

            csvReader.setDelimiter(delimeter.charAt(0));
//            csvReader.setRecordDelimiter(recordDelimeter.charAt(0));
            csvReader.setSkipEmptyRecords(true);
            csvReader.setTrimWhitespace(true);

            Pattern pattern = Pattern.compile("#(\\d+)");
            while (csvReader.readRecord()) {
                if(rowsToProcess > 0 && csvReader.getCurrentRecord() > rowsToProcess) {
                    log.info("Reached max records to load.");
                    break;
                }
                if(csvReader.getCurrentRecord()==0 && (skipFistLine.equalsIgnoreCase("Y") || skipFistLine.equalsIgnoreCase("Yes"))) {
                    continue;
                }
                String record[] = csvReader.getValues();
                //TODO: вынести из цикла парсинг строки из параметров!!
                String sqlValues = dbValuesFrom;
                Matcher m = pattern.matcher(dbValuesFrom);
                while (m.find()) {
                    String ref = m.group(1);
                    String cellData="";
                    int i = Integer.parseInt(ref);
                    if( i <= record.length)
                        cellData= record[i -1];
                    cellData = cellData.replaceAll("'", "''");
                    sqlValues = sqlValues.replaceFirst(m.group(), cellData);
                }

                String sql = "INSERT /*+ APPEND */ INTO " + dbTableName + "(" + dbFields + ") VALUES(" + sqlValues + ")";
//                System.out.println(sql);
                try {
                    dataBaseWorker.execSqlInBatch(sql);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            dataBaseWorker.commitAllSkipErrors();
            Date finished = new Date();
            System.out.println("Started: " + started + "\nFinished: " + finished + "\nSeconds in processs: " + (finished.getTime()-started.getTime())/1000);
        } catch (Exception e) {
            log.error("", e);
            e.printStackTrace();
        } finally {
            dataBaseWorker.close();
        }

    }

}
