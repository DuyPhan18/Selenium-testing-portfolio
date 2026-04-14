import core.BaseTest;
import core.Constants;
import core.ExtentManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.ExcelUtils;

public class LoginPageTest extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(LoginPageTest.class);

    @Test(dataProvider = "loginPageTest")
    public void loginPageTest(String testCaseId, String username, String password, String expectedResult){
        ExtentManager.getTest().info("Start running Test Case: " + testCaseId);

        LoginPage loginPage = new LoginPage(getDriver());
        ExtentManager.getTest().info("Navigate to URL: " + Constants.URL);

        Assert.assertTrue(loginPage.isLoginBoxDisplay());

        loginPage.login(username, password);
        ExtentManager.getTest().info("Login with username: " + username +", password: " + password);
        if (expectedResult.equals("Login success")) {
            Assert.assertTrue(getDriver().getCurrentUrl().contains("inventory"),
                    "Expected login success but failed");
            ExtentManager.getTest().pass("Login success - redirected to inventory page");
        } else {
            // Tất cả case failed đều check error message
            String actualError = loginPage.getErrorMessage();
            Assert.assertEquals(actualError, expectedResult,
                    "Error message not match");
            ExtentManager.getTest().pass("Login failed with correct error: " + actualError);
        }
    }
    @DataProvider(name = "loginPageTest", parallel = true)
    public Object[][] getData() {
        String path = "data" + java.io.File.separator + "test-data.xlsx";
        return ExcelUtils.getTableArray(path, "loginPageTest", false);
    }
}
