import core.BaseTest;
import core.Constants;
import core.ExtentManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.CheckOutPage;
import pages.LoginPage;
import pages.ProductPage;
import utils.ExcelUtils;

public class CheckOutPageTest extends BaseTest {
    private ProductPage productPage;
    private CartPage cartPage;
    private CheckOutPage checkOutPage;

    @BeforeMethod(alwaysRun = true, dependsOnMethods = "initDriver")
    public void setUp() {
        // BaseTest @BeforeMethod chạy trước (init driver + navigate)
        // setUp này chạy sau, login luôn
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login(Constants.STANDARD_USER, Constants.PASSWORD);
        this.cartPage = new CartPage(getDriver());
        this.checkOutPage = new CheckOutPage(getDriver());
        this.productPage = new ProductPage(getDriver());
    }
    @Test(dataProvider = "checkOutPageTest")
    public void checkOutPageTest(String testCaseId, String itemName, String fname, String lname, String postalCode, String expectedResult){

        productPage.addToCartByNames(itemName);
        ExtentManager.getTest().info("Add item to cart.");

        cartPage.goToCart();
        ExtentManager.getTest().info("Go to cart.");

        checkOutPage.goToCheckOutPage();
        ExtentManager.getTest().info("Go to checkout page");

        checkOutPage.enterInfo(fname, lname, postalCode);
        ExtentManager.getTest().info("Checkout with fname: " + fname +", lname: " + lname +", postal code: " + postalCode);

        if (expectedResult.equals("Checkout success")){
            Assert.assertTrue(getDriver().getCurrentUrl().contains("checkout-step-two"),
                    "Expected checkout success but failed");
            ExtentManager.getTest().pass("Checkout success - directed to overview page");
        }else {
            // all case failed -> check error message
            String actualError = checkOutPage.getErrorMessage();
            Assert.assertEquals(actualError, expectedResult,
                    "Error message not match");
            ExtentManager.getTest().pass("Checkout failed with correct error: " + actualError);
        }
    }

    @DataProvider(name = "checkOutPageTest", parallel = false)
    public Object[][] getData() {
        String path = "data" + java.io.File.separator + "test-data.xlsx";
        return ExcelUtils.getTableArray(path, "checkOutPageTest", false);
    }

}
