import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SqlToExcel {

    private static final int MAX_CELL_LENGTH = 32767;

    public static void main(String[] args) {
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
        File directory = new File("C:/Users/pragu/Downloads/" + dateFolder);
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
    }
}
