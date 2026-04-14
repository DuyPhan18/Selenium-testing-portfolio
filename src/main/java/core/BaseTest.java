package core;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utils.ConfigReader;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class BaseTest {

    protected static ExtentReports extent;
    protected ExtentTest test;
    private Set<Cookie> cookies;

    // Static block
    static {
        Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
    }

    @BeforeSuite
    public void beforeSuite() {
        // Khởi tạo báo cáo duy nhất 1 lần cho cả đợt chạy
        extent = ExtentManager.getInstance();
    }

    @BeforeMethod
    public void initDriver() {
        WebDriver driver = createDriver();
        DriverManager.setDriverThreadLocal(driver);
        driver.get(Constants.URL);
    }
    private WebDriver createDriver() {
        // 1. Lấy tên trình duyệt từ file config (mặc định là chrome nếu file trống)
        String browserName = ConfigReader.getProperty("browser");
        if (browserName == null || browserName.isEmpty()) {
            browserName = "chrome";
        }

        // 2. Switch-case để chọn Browser
        switch (browserName.toLowerCase().trim()) {
            case "chrome":
                System.setProperty("webdriver.chrome.silentOutput", "true");
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--headless=new"); // Dùng mode headless mới nhất
                chromeOptions.addArguments("--window-size=1920,1080"); // Ép màn hình Full HD
                chromeOptions.addArguments("--no-sandbox"); // Cần thiết cho Linux/Docker
                chromeOptions.addArguments("--disable-dev-shm-usage"); // Tránh crash trên máy ảo
                return new ChromeDriver(chromeOptions);


            case "firefox":
                // Firefox không dùng ChromeOptions mà dùng FirefoxOptions
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("-headless");
                firefoxOptions.addArguments("--no-sandbox"); // Vượt qua rào cản bảo mật của OS
                firefoxOptions.addArguments("--disable-dev-shm-usage"); // Tránh lỗi thiếu bộ nhớ đệm (/dev/shm)
                firefoxOptions.addArguments("--window-size=1920,1080");
                return new FirefoxDriver(firefoxOptions);

            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--disable-gpu");
                edgeOptions.addArguments("-headless");
                edgeOptions.addArguments("--no-sandbox"); // Vượt qua rào cản bảo mật của OS
                edgeOptions.addArguments("--disable-dev-shm-usage"); // Tránh lỗi thiếu bộ nhớ đệm (/dev/shm)
                edgeOptions.addArguments("--window-size=1920,1080");
                return new EdgeDriver(edgeOptions);

            default:
                System.out.println("Browser: " + browserName + " không hợp lệ. Đang khởi tạo Chrome mặc định...");
                return new ChromeDriver();
        }
//        driver.manage().window().maximize();

        // 2. Đẩy vào ThreadLocal ngay lập tức

    }
    // 3. Luôn lấy driver từ ThreadLocal thông qua hàm này
    public WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    @AfterMethod
    public void closeDriver() {
        // 4. Giải phóng driver và xóa ThreadLocal cho sạch ngăn kéo
        DriverManager.quitDriver();
    }
    @AfterSuite
    public void afterSuite() {
        if (extent != null) {
            extent.flush();
        }
    }
    // --- Phần Cookie giữ nguyên logic của ông ---
    public void saveCookies() {
        cookies = getDriver().manage().getCookies();
    }

    public void loadCookies(String currentDomain) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getDomain().equals(currentDomain)) {
                    getDriver().manage().addCookie(cookie);
                }
            }
        }
    }
}