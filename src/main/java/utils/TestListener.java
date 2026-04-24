package utils;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.epam.healenium.SelfHealingDriver;
import core.DriverManager;
import core.ExtentManager;
import helpers.CaptureHelpers;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class    TestListener implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {
        // Khởi tạo test và lưu vào ThreadLocal thông qua setTest
        ExtentTest test = ExtentManager.getInstance().createTest(result.getName());
        ExtentManager.setTest(test);
        System.out.println(">>> Đang bắt đầu chạy: " + result.getName());
    }
    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = DriverManager.getDriver();
        if (driver != null) {
            try {
                // ✅ Unwrap SelfHealingDriver để lấy driver thật
                WebDriver originalDriver = driver instanceof SelfHealingDriver
                        ? ((SelfHealingDriver) driver).getDelegate()
                        : driver;

                String screenshotPath = CaptureHelpers.captureScreenshot(result.getName());
                if (screenshotPath != null && !screenshotPath.isEmpty()) {
                    ExtentManager.getTest().fail("FAILED: " + result.getThrowable(),
                            MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                }
            } catch (Exception e) {
                ExtentManager.getTest().fail("FAILED: " + result.getThrowable());
            }
        }
    }
    @Override
    public void onFinish(ITestContext context) {
        // Xuất báo cáo ra file HTML
        ExtentManager.getInstance().flush();
        System.out.println(">>> Đã xuất báo cáo Extent Report!");
    }
}