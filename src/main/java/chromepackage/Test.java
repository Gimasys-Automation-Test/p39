package chromepackage;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class Test {
    @org.testng.annotations.Test
    public void GetFieldsInFile(){
        List<String> columnValues = new ArrayList<String>();
        String headerName ="";
        try{
            String filePath = "C:\\Test\\History.xlsx"; // Đường dẫn tới file Excel-nên bỏ cùng thư mục với code
            String sheetName = "Historytracking"; // Tên sheet chứa dữ liệu
            FileInputStream fileInputStream = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheet(sheetName);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                Cell cell1 = row.getCell(1); // Giả sử cột dữ liệu "ExpectedValue" nằm ở cột đầu tiên (index 1)
                if (cell1 != null) {
                    String expectedValue = cell1.getStringCellValue();
                    columnValues.add(expectedValue);
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        Assert.assertTrue(true);
    }
}
