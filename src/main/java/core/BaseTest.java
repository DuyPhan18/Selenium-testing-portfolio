package core;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.epam.healenium.SelfHealingDriver;
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

import java.util.Map;
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
        String browserName = ConfigReader.getProperty("browser");
        if (browserName == null || browserName.isEmpty()) {
            browserName = "chrome";
        }

        WebDriver driver;  // ✅ khai báo driver trước

        switch (browserName.toLowerCase().trim()) {
            case "chrome":
                System.setProperty("webdriver.chrome.silentOutput", "true");
                ChromeOptions chromeOptions = new ChromeOptions();

                // ✅ Disable password manager popup
                chromeOptions.addArguments("--incognito");
                String headless = ConfigReader.getProperty("headless");
                if ("true".equals(headless)) {
                    chromeOptions.addArguments("--headless=new");
                    chromeOptions.addArguments("--window-size=1920,1080");
                }
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                driver = new ChromeDriver(chromeOptions);  // ✅ assign thay vì return
                if (!"true".equals(headless)) {
                    driver.manage().window().maximize();  // ✅ maximize khi không headless
                }
                break;

            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("-headless");
                firefoxOptions.addArguments("--no-sandbox");
                firefoxOptions.addArguments("--disable-dev-shm-usage");
                firefoxOptions.addArguments("--window-size=1920,1080");
                driver = new FirefoxDriver(firefoxOptions);  // ✅ assign thay vì return
                break;

            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--disable-gpu");
                edgeOptions.addArguments("-headless");
                edgeOptions.addArguments("--no-sandbox");
                edgeOptions.addArguments("--disable-dev-shm-usage");
                edgeOptions.addArguments("--window-size=1920,1080");
                driver = new EdgeDriver(edgeOptions);  // ✅ assign thay vì return
                break;

            default:
                System.out.println("Browser: " + browserName + " không hợp lệ. Đang khởi tạo Chrome mặc định...");
                driver = new ChromeDriver();
                break;
        }

        // ✅ Wrap bằng SelfHealingDriver rồi mới return
        return SelfHealingDriver.create(driver);
    }
    // 3. Luôn lấy driver từ ThreadLocal thông qua hàm này
    public WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    @AfterMethod(alwaysRun = true)
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