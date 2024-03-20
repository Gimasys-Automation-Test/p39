import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.List;

public class Checkbox {
    public static void main(String[] args) {
        // Khởi tạo đối tượng WebDriver (ChromeDriver)
        WebDriver driver = new ChromeDriver();
        // Mở trang web Salesforce
        driver.get("https://comehome--test.sandbox.lightning.force.com/lightning/setup/ObjectManager/Account/FieldsAndRelationships/setHistoryTracking");

        // Điền thông tin đăng nhập
        driver.findElement(By.id("username")).sendKeys("hmvuong@comehome.impl.test");
        driver.findElement(By.id("password")).sendKeys("123456Pw!12");
        driver.findElement(By.id("Login")).click();

        // Chờ cho trang History Tracking load thành công
        // (Bạn có thể sử dụng các phương thức waitForElementVisible, waitForPageLoad, waitForAjax, etc. tùy vào cách trang web được xây dựng)
        // Ở đây, tôi sẽ sử dụng một cách đơn giản là sleep trong thời gian cố định
        try {
            Thread.sleep(5000); // Chờ 5 giây cho trang load thành công
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Lấy tất cả các checkbox đã được kiểm tra trên trang
        List<WebElement> checkedCheckboxes = driver.findElements(By.xpath("//input[@type='checkbox' and @checked='checked']"));

        // In ra tên của các checkbox đã được kiểm tra
        System.out.println("Các checkbox được kiểm tra:");
        for (WebElement checkbox : checkedCheckboxes) {
            System.out.println(checkbox.getAttribute("id"));
        }

        // Đóng trình duyệt
        driver.quit();
    }
}
