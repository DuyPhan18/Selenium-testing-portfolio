import core.BaseTest;
import core.Constants;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.LoginPage;
import utils.ExcelUtils;

public class CartPageTest extends BaseTest {
    @BeforeMethod
    public void setUp() {
        // BaseTest @BeforeMethod chạy trước (init driver + navigate)
        // setUp này chạy sau, login luôn
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login(Constants.STANDARD_USER, Constants.PASSWORD);
        CartPage cartPage = new CartPage(getDriver());
    }

    @Test()
    public void cartPageTest(){

    }

    @DataProvider(name = "cartPageTest", parallel = false)
    public Object[][] getData() {
        String path = "data" + java.io.File.separator + "test-data.xlsx";
        return ExcelUtils.getTableArray(path, "cartPageTest", false);
    }
}
