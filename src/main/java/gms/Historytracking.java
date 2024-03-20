package SalesforceBase;

import gms.SalesforceBase;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import java.util.stream.Collectors;

public class Historytracking extends SalesforceBase{
    public Historytracking() {
        super();
        this.setUrl("https://comehome--test.sandbox.lightning.force.com/lightning/setup/ObjectManager/Account/FieldsAndRelationships/setHistoryTracking");
        this.setUserName("hmvuong@comehome.impl.test");
        this.setPassword("123456Pw!12");
    }
    @Test(priority = 1)
    public void checkFields() throws InterruptedException{
        //Get total rows of list
        int expectedField = 20;
        int actualField = 0;
        List<String> expectedList = GetFieldsInFile();
        List<String> actualList = new ArrayList<>();
        WebElement iframe = driver.findElement(By.xpath("//iframe|//frame"));
        driver.switchTo().frame(iframe);

        List<WebElement> elements = driver.findElements(By.xpath("//input[@type='checkbox']"));
        for (WebElement e : elements) {
            if (e.isSelected()) {
                String id = e.getAttribute("id").toString();
                WebElement label = driver.findElement(By.xpath("//label[@for='"+id+"']"));
                if(label!=null){
                    String labelText = label.getText();
                    if(!labelText.equals("Enable Account History")){
                        actualList.add(labelText);
                        actualField++;
                    }
                }
            }
        }

        System.out.println("--------------------actualList------------------------------ ");
        System.out.println(Arrays.toString(actualList.toArray()));
        System.out.println("-------------------------------------------------- ");
        List<String> listOne = expectedList.stream().map(String::toLowerCase).collect(Collectors.toList());
        List<String> listTwo = actualList.stream().map(String::toLowerCase).collect(Collectors.toList());
        // Switching the focus of selenium from iframe to main page
        driver.switchTo().parentFrame();
        //compare with original list.
        List<String> differences = new ArrayList<>(listOne);
        differences.removeAll(listTwo);
        System.out.println("khong co trong list excel");
        System.out.println(Arrays.toString(differences.toArray()));
        System.out.println("-------------------------------------------------- ");

        System.out.println("Check nhieu hon trong list excel");
        List<String> differences2 = new ArrayList<>(listTwo);
        differences2.removeAll(listOne);
        System.out.println(Arrays.toString(differences2.toArray()));
        System.out.println("-------------------------------------------------- ");

        Assert.assertTrue(expectedList.equals(actualList));
    }


    @Test
    public List<String> GetFieldsInFile(){
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
        return columnValues;
    }
}
