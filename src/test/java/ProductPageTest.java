import core.BaseTest;
import core.Constants;
import core.ExtentManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductPage;
import utils.ExcelUtils;

public class ProductPageTest extends BaseTest {
    private ProductPage productPage;
    private static final Logger log = LoggerFactory.getLogger(ProductPageTest.class);

    @BeforeMethod
    public void setUp() {
        // BaseTest @BeforeMethod chạy trước (init driver + navigate)
        // setUp này chạy sau, login luôn
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login(Constants.STANDARD_USER, Constants.PASSWORD);
        productPage = new ProductPage(getDriver());
    }
    @Test(dataProvider = "productPageTest")
    public void productPageTest(String testCaseId, String itemName, String cartBadge){
        ExtentManager.getTest().info("Go to inventory page");

        Assert.assertEquals(productPage.isProductListDisplay6Item(), 6,"Match");
        ExtentManager.getTest().info("Have 6 item in inventory page");

        productPage.addToCartByNames(itemName);
        ExtentManager.getTest().info("add item successfully");

        Assert.assertTrue(productPage.checkCartBadge(Integer.parseInt(cartBadge)),
                "Cart badge should equal " + cartBadge);

    }
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (productPage != null) {
            productPage.clearCart();// ✅ clear cart trước khi closeDriver chạy
        }
    }

    @DataProvider(name = "productPageTest", parallel = false)
    public Object[][] getData() {
        String path = "data" + java.io.File.separator + "test-data.xlsx";
        return ExcelUtils.getTableArray(path, "productPageTest", false);
    }
}
