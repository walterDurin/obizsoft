java -cp "sqlite-jdbc-3.8.7.jar;CsvExcelXLSX2DB.jar" ru.btc.utils.excel2csv.LoadCSV sampleCSV.csv "windows-1251" ";" Y USERS "NAME, DATE_OF_BIRTH" " '#1', date('#2')" "jdbc:sqlite:sample.db" dbuser dbpasswd 0 2000