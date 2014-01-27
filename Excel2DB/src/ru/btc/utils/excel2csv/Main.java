package ru.btc.utils.excel2csv;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: Vova
 * Date: 28.01.14
 * Time: 0:48
 */
public class Main {

    public static void main(String[] args) throws IOException, SQLException {
        String fileName = args[0];
        String sheetName = args[1];
        String skipFistLine = args[2];
        String dbTableName = args[3];
        String dbValuesFrom = args[4];
        String dbURI = args[5];
        String dbUser = args[6];
        String dbPass = args[7];

        System.out.println("Параметры запуска:");
        for (String arg : args) {
            System.out.print("\"" + arg + "\" ");
        }

//        Connection connection = getConnection(dbURI, dbUser, dbPass);
//        System.out.println("\nConnected: " + connection.getMetaData().getDatabaseProductName() + " ver. " +connection.getMetaData().getDatabaseProductVersion());

        System.out.println("\nОткрытие файла: \"" + fileName + "\"..");

        InputStream in = new FileInputStream(fileName);
        // Внимание InputStream будет закрыт
        // Если нужно не закрывающий см. JavaDoc по POIFSFileSystem :  http://goo.gl/1Auu7
        XSSFWorkbook wb = new XSSFWorkbook(in);

//        Sheet sheet = wb.getSheetAt(cc[0]);
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

        while (it.hasNext()) {
            Row row = it.next();

            Iterator<Cell> cells = row.iterator();
/*
            List<String> lineVals = new ArrayList<String>();
            while (cells.hasNext()) {
                Cell cell = cells.next();
                String val = getCellVal(cell);
                lineVals.add(val);
            }
*/

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

            sqlValues = sqlValues.substring(0, sqlValues.length() - 1);

            String sql = "INSERT INTO " + dbTableName + " VALUES(" + sqlValues + ")";

            System.out.println(sql);

            //connection.createStatement().execute(sql);

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


    private static Connection getConnection(String uri, String user, String pass) throws SQLException {
        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", user);
        connectionProps.put("password", pass);
        conn = DriverManager.getConnection(uri, connectionProps);
        return conn;
    }
}
