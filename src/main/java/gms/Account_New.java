package gms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class Account_New extends SalesforceBase {
    public Account_New() {
        super();
        //this.setUrl("https://comehome--test.sandbox.lightning.force.com/lightning/r/Account/0011e00000mVMoXAAW/edit?inContextOfRef=1.eyJ0eXBlIjoic3RhbmRhcmRfX3JlY29yZFBhZ2UiLCJhdHRyaWJ1dGVzIjp7Im9iamVjdEFwaU5hbWUiOiJBY2NvdW50IiwicmVjb3JkSWQiOiIwMDExZTAwMDAwbVZNb1hBQVciLCJhY3Rpb25OYW1lIjoidmlldyJ9LCJzdGF0ZSI6e319&ws=%2Flightning%2Fr%2FAccount%2F0011e00000mVMoXAAW%2Fview&count=4");
    }
    //
    @Test(priority = 1)
    public void CreateAccount() throws InterruptedException{//a[contains(@class,'btnX')
        //Get total rows of list
        int currentRows = 0;
        List<WebElement> oldrows = driver.findElements(By.xpath("//table[@class='slds-table forceRecordLayout slds-table--header-fixed slds-table--edit slds-table--bordered resizable-cols slds-table--resizable-cols uiVirtualDataTable']/tbody/tr"));
        currentRows = oldrows.size();

        // Click New
        WebElement newButton = driver.findElement(By.xpath("//li[@data-target-selection-name = 'sfdc:StandardButton.Account.New']"));
        newButton.click();
        Thread.sleep(10000);

        Random rn = new Random();
        int codenumber = rn.nextInt(Integer.MAX_VALUE-1) + 1;
        String code = String.valueOf(codenumber);
        WebElement element_code = driver.findElement(By.xpath("//*[@name='AccountNumber']"));
        element_code.sendKeys(code);

        WebElement element_name = driver.findElement(By.xpath("//*[@name='Name']"));
        String acountName="Account_"+code;
        element_name.sendKeys(acountName);

        int phonenumber1 = rn.nextInt(8) + 1;
        int phonenumber2 = rn.nextInt(8) + 1;
        int phonenumber3 = rn.nextInt(8) + 1;
        int phonenumber4 = rn.nextInt(8) + 1;
        int phonenumber5 = rn.nextInt(8) + 1;
        String phone = "03345"+phonenumber1+phonenumber2+phonenumber3+phonenumber4+phonenumber5;
        WebElement element_phone = driver.findElement(By.xpath("//*[@name='Company_Phone__c']"));
        element_phone.sendKeys(phone);

        WebElement element_email = driver.findElement(By.xpath("//*[@name='Email__c']"));
        element_email.sendKeys("Account"+code+"@admin.com");

        //click Save
        WebElement save = driver.findElement(By.xpath("//button[@name = 'save']"));
        save.click();

        //Check result
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement y= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html[1]/body[1]/div[6]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]")));
        System.out.println(y.getText());
        Assert.assertEquals(y.getText(),"Success!");

    }

    @Test(priority = 2)
    public void EditAccount()  throws InterruptedException{
        Thread.sleep(5000);
        WebElement save = driver.findElement(By.xpath("//button[contains(text(),'Save')]"));
        save.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement y= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html[1]/body[1]/div[6]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]")));
        System.out.println(y.getText());
        Assert.assertEquals(y.getText(),"Success!");
    }
    @Test(priority = 3)
    public void checkFields() throws InterruptedException{//a[contains(@class,'btnX')
        //Get total rows of list
        int expectedField = 20;
        int actualField = 0;

        List<WebElement> findData = driver.findElements(By.xpath("//input[@type='checkbox']"));
        for (WebElement e : findData) {
            if (e.findElement(By.tagName("input")).isSelected()) {
                String name = e.getAttribute("name").toString();
                System.out.println(name);
                String value = e.getAttribute("value").toString();
                System.out.println(value);
                actualField++;
            }
        }

        List<WebElement> rows = driver.findElements(By.xpath("//table[@class='detailList']/tbody/tr"));
        for (WebElement e : rows) {
            if (e.findElement(By.tagName("input")).isSelected()) {
                String name = e.getAttribute("name").toString();
                System.out.println(name);
                String value = e.getAttribute("value").toString();
                System.out.println(value);
                actualField++;
            }
        }
        //compare with original account name.
        Assert.assertTrue(actualField==expectedField);
    }

}

