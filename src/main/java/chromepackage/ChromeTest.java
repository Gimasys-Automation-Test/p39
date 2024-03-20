package chromepackage;

//import jdk.javadoc.internal.doclets.toolkit.util.DocFile;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v85.performance.Performance;
import org.openqa.selenium.devtools.v85.performance.model.Metric;
import org.testng.annotations.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import java.util.*;
import java.util.stream.Collectors;

public class ChromeTest {

    //Bài 1: Checked vào checkbox
        @Test
        void validateCheckSuccess(){
            WebDriver driver = new ChromeDriver();
            driver.get("https://the-internet.herokuapp.com/checkboxes");

            check(driver.findElement(By.xpath("//form[@id='checkboxes']/input[1]")));
            Assert.assertTrue(driver.findElement(By.xpath("//form[@id='checkboxes']/input[1]")).isSelected());

            check(driver.findElement(By.xpath("//form[@id='checkboxes']/input[2]")));
            Assert.assertTrue(driver.findElement(By.xpath("//form[@id='checkboxes']/input[2]")).isSelected());
            driver.quit();

        }
        //Bài 2: UnCheck trên checkbox
        @Test
         void validateUncheckSuccess(){
            WebDriver driver = new ChromeDriver();
            driver.get("https://the-internet.herokuapp.com/checkboxes");

            uncheck(driver.findElement(By.xpath("//form[@id='checkboxes']/input[1]")));
            Assert.assertFalse(driver.findElement(By.xpath("//form[@id='checkboxes']/input[1]")).isSelected());

            uncheck(driver.findElement(By.xpath("//form[@id='checkboxes']/input[2]")));
            Assert.assertFalse(driver.findElement(By.xpath("//form[@id='checkboxes']/input[2]")).isSelected());
            driver.quit();
        }
        //Hàm check dùng cho validateCheckSuccess
        static void check(WebElement element){
            if(!element.isSelected()) element.click();
        }

        //Hàm uncheck dùng cho validateUncheckSuccess
        static void uncheck(WebElement element){
            if(element.isSelected()) element.click();
        }

        //Bài 3: Validate Url
        @Test
        public static void ValidatePageUrl() {
            WebDriver driver = new ChromeDriver();
            driver.get("https://selenium.dev/");
            Assert.assertEquals(driver.getCurrentUrl(),"https://www.selenium.dev/");
            driver.quit();
        }

        //Bài 4: Validate Url với headlessmode
        @Test
        public static void validatePageUrlWithHeadlessMode() {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--headless=new");

            WebDriver driver = new ChromeDriver(chromeOptions);
            driver.get("https://selenium.dev/");
            Assert.assertEquals(driver.getCurrentUrl(),"https://www.selenium.dev/");
            driver.quit();
        }

        //Bài 5: Validate Url với mobile view
        @Test
        public static void validatePageUrlWithMobileView() {
            Map<String, Object> deviceMetrics = new HashMap<>();
            deviceMetrics.put("width", 412);
            deviceMetrics.put("height", 915);
            deviceMetrics.put("pixelRadio", 1.0);
            Map<String, Object> mobileEmulation = new HashMap<>();
            mobileEmulation.put("deviceMetrics", deviceMetrics);
            mobileEmulation.put("userAgent","Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Mobile Safari/537.36");

            ChromeOptions chromeOptions = new ChromeOptions();

            chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
            WebDriver driver = new ChromeDriver(chromeOptions);
            driver.get("https://www.selenium.dev");
            Assert.assertEquals(driver.getCurrentUrl(),"https://www.selenium.dev/");
            driver.quit();
        }

        //Bài 6: Chụp hình
        @Test
        void openSeleniumHomePageAndCapturePerformanceMetrics() {
            ChromeDriver driver = new ChromeDriver();
            DevTools devTools = driver.getDevTools();
            devTools.createSession();
            devTools.send(Performance.enable(Optional.empty()));
            List<Metric> metricList = devTools.send(Performance.getMetrics());
            driver.get("https://selenium.dev");

            Assert.assertEquals(driver.getTitle(), "Selenium");
            driver.quit();

            for (Metric m : metricList) {
                System.out.println(m.getName() + " = " + m.getValue());
            }
        }

        //Bài 7: Chọn 1 option
        @Test
        void dropDown() {
           // WebDriverManager.chromedriver().setup();
            WebDriver driver = new ChromeDriver();
            driver.get("https://the-internet.herokuapp.com/dropdown");

            Select dropdown = new Select(driver.findElement(By.id("dropdown")));
            dropdown.selectByIndex(1);
            dropdown.selectByVisibleText("Option 2");
            dropdown.selectByValue("2");
            //capture screenshot
            File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File DestFile=new File("./target/screenshot/"
                    +"contextClick"
                    + "_"
                    +System.currentTimeMillis()+".png");
            try {
                FileUtils.copyFile(file,DestFile);
            } catch (IOException e){
                throw new RuntimeException(e);
            }
        }

        //Bài 8: điền username password và nhấn nút Submit
    @Test
    void validCredentials1() {
            WebDriver driver = new ChromeDriver();
            driver.get("https://the-internet.herokuapp.com/login");
            driver.findElement(By.id("username")).sendKeys("tomsmith");
            driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
            driver.findElement(By.cssSelector("button[type=submit]")).click();
            Assert.assertEquals(driver.getCurrentUrl(), "https://the-internet.herokuapp.com/secure");
            driver.quit();
        }
    /* validCredentials1:Textbox username
     * Tag: input
     * Attributes:
     * - type = "text" --> remove (vì mang nghĩa chung chung)
     * - name = "username"
     * - id = "username"
     * Text: n/a
     * */
    /* validCredentials1: Nút login
     * Tag: button
     * class = radius --> remove vì ko có nghĩa
     * type = submit
     * */
    //driver.findElement(By.tagName("button")).click();
    //driver.findElement(By.className("radius")).click();

    //driver.findElement(By.cssSelector("[type=submit]")).click(); => nên dùng này hoặc thêm 'button' vào
    //driver.findElement(By.xpath("//*[@type='submit']")).click();

    //hàm class viết tắ bằng dấu chấm . => .radius => remove vì mang nghĩa chung chung
    //driver.findElement(By.cssSelector(".radius")).click();
    //driver.findElement(By.cssSelector("input.radius")).click();

    //driver.findElement(By.cssSelector("button[type=submit]")).click();
    //driver.findElement(By.xpath("//button[@type='submit']")).click();

    //driver.findElement(By.xpath("//input[@class='radius']")).click(); => remove vì mang nghĩa chung chung
    //driver.findElement(By.xpath("//*[@class='radius']")).click(); => remove vì mang nghĩa chung chung

    // Element <E> with attribute A containing text 't' exactly
    /*
     * E --> tagName
     * A --> attribute name
     * t --> attribute value
     * css = E[A=t]
     * xpath = //[@A='t']
     */
    // id -> tagName -> name -> css -> xpath
    // ID -> name -> classname -> css -> xpath
    // End validCredentials1

    //Bài 9: Hyperlink
    @Test
        void HyperlinkText(){
        WebDriver driver = new ChromeDriver();
            driver.get("https://the-internet.herokuapp.com/status_codes");

            driver.findElement(By.linkText("200")).click();
            Assert.assertEquals(driver.getCurrentUrl(),"https://the-internet.herokuapp.com/status_codes/200");
            driver.findElement(By.linkText("here")).click();

            driver.findElement(By.linkText("301")).click();
            Assert.assertEquals(driver.getCurrentUrl(),"https://the-internet.herokuapp.com/status_codes/301");
            driver.findElement(By.linkText("here")).click();

            driver.findElement(By.linkText("404")).click();
            Assert.assertEquals(driver.getCurrentUrl(),"https://the-internet.herokuapp.com/status_codes/404");
            driver.findElement(By.linkText("here")).click();

            driver.findElement(By.linkText("500")).click();
            Assert.assertEquals(driver.getCurrentUrl(),"https://the-internet.herokuapp.com/status_codes/500");
            driver.findElement(By.linkText("here")).click();
        //capture screenshot
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File DestFile=new File("./target/screenshot/"
                +"contextClick"
                + "_"
                +System.currentTimeMillis()+".png");
        try {
            FileUtils.copyFile(file,DestFile);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

            //Bài 10
//    @Test
//    void validateLargestDuePerson() {
//        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.addArguments("--remote-allow-origins=*");
//        WebDriver driver = new ChromeDriver(chromeOptions);
//        driver.get("https://the-internet.herokuapp.com/tables");
//        List<WebElement> rows = driver.findElements(By.xpath("//table[@id='table']/tbody/tr"));
//        List<Person> persons = rows.stream().map(row ->{
//            List<WebElement> cells = row.findElements(By.tagName("id"));
//            String firstName = cells.get(1).getText();
//            String lastName = cells.get(0).getText();
//            double due = Double.parseDouble(cells.get(3).getText().replace("$",""));
//            return new Person(firstName,lastName,due);
//        }).collect(Collectors.toList());
//        Assert.assertEquals(
//                persons
//                        .stream()
//                        .max(Comparator.comparing(Person::getDue))
//                        .orElseThrow()
//                        .getFullName(),
//                "Jason Doe");
//    }

//Bài 11: Nested frames
//    Open browser
//    Navigate to https://the-internet.herokuapp.com/nested_frames
//    Verify Text present: LEFT RIGHT MIDDLE BOTTOM
    @Test
    void frame() {
       // WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/nested_frames");

        driver.switchTo().frame("frame-top");

        driver.switchTo().frame("frame-left");
        System.out.println(driver.findElement(By.xpath("html/body")).getText());
        driver.switchTo().parentFrame();

        driver.switchTo().frame("frame-middle");
        System.out.println(driver.findElement(By.id("content")).getText());
        driver.switchTo().parentFrame();

        driver.switchTo().frame("frame-right");
        System.out.println(driver.findElement(By.xpath("html/body")).getText());
        driver.switchTo().parentFrame();

        driver.switchTo().parentFrame();
        driver.switchTo().frame("frame-bottom");
        System.out.println(driver.findElement(By.xpath("html/body")).getText());

        //capture screenshot
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File DestFile=new File("./target/screenshot/"
                +"contextClick"
                + "_"
                +System.currentTimeMillis()+".png");
        try {
            FileUtils.copyFile(file,DestFile);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
