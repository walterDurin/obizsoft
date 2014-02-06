package ru.btc.utils.excel2csv;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.btc.utils.utils.DataBaseWorker;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Vova
 * Date: 28.01.14
 * Time: 0:48
 */
public class Main {

    private static DataBaseWorker dataBaseWorker;


    public static void main(String[] args) throws IOException, SQLException {
        int argNum = 0;
        String fileName = args[argNum++];
        String sheetName = args[argNum++];
        String skipFistLine = args[argNum++];
        String dbTableName = args[argNum++];
        String dbFields = args[argNum++];
        String dbValuesFrom = args[argNum++];
        String dbURI = args[argNum++];
        String dbUser = args[argNum++];
        String dbPass = args[argNum++];

        System.out.println("Параметры запуска:");
        for (String arg : args) {
            System.out.print("\"" + arg + "\" ");
        }

        int rowsToProcess = 100000;
        dataBaseWorker = new DataBaseWorker(dbURI, dbUser, dbPass, 1000);

        try {

            System.out.println(new Date());
            System.out.println("\nОткрытие файла: \"" + fileName + "\"..");

            InputStream in = new FileInputStream(fileName);
            // Внимание InputStream будет закрыт
            // Если нужно не закрывающий см. JavaDoc по POIFSFileSystem :  http://goo.gl/1Auu7
            XSSFWorkbook wb = new XSSFWorkbook(in);

            System.out.println("Открытие листа: \"" + sheetName + "\"..");

//        AreaReference aref = new AreaReference(sheetName + "!A3:E5");
//        System.out.println(aref.getFirstCell().getRow() + "x" + aref.getFirstCell().getCol() + " - " + aref.getLastCell().getRow() + "x" + aref.getLastCell().getCol());

            Sheet sheet = wb.getSheet(sheetName);

            Iterator<Row> it = sheet.iterator();
            if (it.hasNext() && skipFistLine != null && (skipFistLine.equalsIgnoreCase("Y") || skipFistLine.equalsIgnoreCase("Yes"))) {
                System.out.println("Skip first line...");
                it.next();
            }

            Pattern pattern = Pattern.compile("#(\\w+)");

            while (rowsToProcess-- > 0 && it.hasNext()) {
                Row row = it.next();


                String sqlValues = dbValuesFrom;
                Matcher m = pattern.matcher(dbValuesFrom);
                while (m.find()) {
                    String ref = m.group(1);
                    Cell cell = row.getCell(new CellReference(ref).getCol());
                    String s;
                    if (cell == null) {
                        s = "";
                    } else {
                        s = getCellVal(cell);
                    }
                    s = s.replaceAll("'", "''");
                    sqlValues = sqlValues.replaceFirst(m.group(), s);
                }

                //sqlValues = sqlValues.substring(0, sqlValues.length() - 1);

                String sql = "INSERT INTO " + dbTableName + "(" + dbFields + ") VALUES(" + sqlValues + ")";
                //System.out.println(sqlValues);
                dataBaseWorker.execSqlInBatch(sql);
            }
            dataBaseWorker.commitAllSkipErrors();
            //System.out.println(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dataBaseWorker.close();
        }


    }

    private static String getCellVal(Cell cell) {
        int cellType = cell.getCellType();
        String val = null;
        switch (cellType) {
            case Cell.CELL_TYPE_STRING:
                val = cell.getStringCellValue().trim();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                val = cell.getNumericCellValue() + "";
                break;
            case Cell.CELL_TYPE_FORMULA:
                val = cell.getNumericCellValue() + "";
                break;
            default:
                val = "";
                break;
        }
        return val;
    }

}
