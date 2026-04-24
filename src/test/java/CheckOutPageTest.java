import core.BaseTest;
import core.Constants;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.CheckOutPage;
import pages.LoginPage;
import pages.ProductPage;
import utils.ExcelUtils;

public class CheckOutPageTest extends BaseTest {
    @BeforeMethod
    public void setUp() {
        // BaseTest @BeforeMethod chạy trước (init driver + navigate)
        // setUp này chạy sau, login luôn
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login(Constants.STANDARD_USER, Constants.PASSWORD);
        CheckOutPage checkOutPage = new CheckOutPage(getDriver());
    }
    @Test()
    public void checkOutPageTest(){

    }

    @DataProvider(name = "checkOutPageTest", parallel = false)
    public Object[][] getData() {
        String path = "data" + java.io.File.separator + "test-data.xlsx";
        return ExcelUtils.getTableArray(path, "checkOutPageTest", false);
    }

}
