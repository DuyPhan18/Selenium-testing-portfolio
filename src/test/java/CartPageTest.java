import core.BaseTest;
import core.Constants;
import core.ExtentManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.LoginPage;
import pages.ProductPage;
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

    @Test(dataProvider = "cartPageTest")
    public void cartPageTest(String testCaseId, String itemName, String totalItemQuantity){
        CartPage cartPage = new CartPage(getDriver());
        cartPage.addToCartByName(itemName);
        ExtentManager.getTest().info("add item successfully");

        cartPage.goToCart();
        ExtentManager.getTest().info("go to cart");

        Assert.assertTrue(cartPage.verifyItemInCart(itemName, totalItemQuantity), "Match");
        ExtentManager.getTest().info("Finish");


    }

    @DataProvider(name = "cartPageTest", parallel = false)
    public Object[][] getData() {
        String path = "data" + java.io.File.separator + "test-data.xlsx";
        return ExcelUtils.getTableArray(path, "cartPageTest", false);
    }
}
