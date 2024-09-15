package org.example;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SqlToExcelScheduler {
   private static final Date now = new Date();
    private static final int MAX_CELL_LENGTH = 32767;

    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
            String url = "jdbc:mysql://tags-tags.a.aivencloud.com:19982/tags";
            String user = "avnadmin";
            String password = "AVNS_B_4-CPt4gIRMBH_LxmW";

            // Define your queries and file names
            String[] queries = {
                    "SELECT * FROM user",
                    "SELECT * FROM login"
            };

            String[] fileNames = {
                    "UserData.xlsx",
                    "LoginData.xlsx"
            };

            if (queries.length != fileNames.length) {
                System.err.println("Queries and file names arrays must be of the same length.");
                return;
            }

            // Get today's date and format it
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MMM_yyyy");
            String dateFolder = dateFormat.format(new Date());
            File directory = new File("C:/Users/pragu/Downloads/ayupin/every/automate/" + dateFolder);
            System.out.println("Saving to directory: " + directory);
            System.out.println("Scheduler ran at "+now);
            if (!directory.exists()) {
                directory.mkdirs(); // Create the directory if it doesn't exist
            }

            try (Connection conn = DriverManager.getConnection(url, user, password)) {

                for (int i = 0; i < queries.length; i++) {
                    String query = queries[i];
                    String fileName = fileNames[i];
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(query);
                         Workbook workbook = new XSSFWorkbook()) {

                        Sheet sheet = workbook.createSheet("Sheet1");
                        int rowNum = 0;

                        // Write header
                        Row headerRow = sheet.createRow(rowNum++);
                        int columnCount = rs.getMetaData().getColumnCount();
                        for (int col = 1; col <= columnCount; col++) {
                            headerRow.createCell(col - 1).setCellValue(rs.getMetaData().getColumnName(col));
                        }

                        // Write data
                        while (rs.next()) {
                            Row row = sheet.createRow(rowNum++);
                            for (int col = 1; col <= columnCount; col++) {
                                String cellValue = rs.getString(col);
                                if (cellValue != null && cellValue.length() > MAX_CELL_LENGTH) {
                                    cellValue = cellValue.substring(0, MAX_CELL_LENGTH);
                                }
                                row.createCell(col - 1).setCellValue(cellValue);
                            }
                        }

                        // Save to the file in the date-named folder
                        try (FileOutputStream fileOut = new FileOutputStream(new File(directory, fileName))) {
                            workbook.write(fileOut);
                        }
                    }
                }

            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        };


        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 17); // 5 PM
        calendar.set(Calendar.MINUTE, 12);
        calendar.set(Calendar.SECOND, 30);


        Date firstRunTime = calendar.getTime();
        if (firstRunTime.before(now)) {
            calendar.add(Calendar.WEEK_OF_YEAR, 1);
            firstRunTime = calendar.getTime();
        }

        long initialDelay = firstRunTime.getTime() - now.getTime();
        long period = TimeUnit.DAYS.toMillis(7); // 1 week

        scheduler.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.MILLISECONDS);

    }
}
