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
    private CartPage cartPage;
    private ProductPage productPage;

    @BeforeMethod
    public void setUp() {
        // BaseTest @BeforeMethod chạy trước (init driver + navigate)
        // setUp này chạy sau, login luôn
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login(Constants.STANDARD_USER, Constants.PASSWORD);
        this.cartPage = new CartPage(getDriver());
        this.productPage = new ProductPage(getDriver());
    }

    @Test(dataProvider = "cartPageTest")
    public void cartPageTest(String testCaseId, String itemName, String cartBadge, String action){

        if (action.equals("add")){
            productPage.addToCartByNames(itemName);
            ExtentManager.getTest().info("add item successfully");

            cartPage.goToCart();
            ExtentManager.getTest().info("go to cart");

            Assert.assertTrue(cartPage.verifyItemInCart(itemName, cartBadge), "Match");
            ExtentManager.getTest().info("Add item to cart successfully");
        }else if (action.equals("remove")){
            productPage.addToCartByNames(itemName);
            cartPage.goToCart();
            cartPage.removeFromCart(itemName);

            int badgeCount = productPage.getCartBadgeCount();
            Assert.assertEquals(badgeCount, Integer.parseInt(cartBadge),
                    "Cart badge should be " + cartBadge);
        }

    }

    @DataProvider(name = "cartPageTest", parallel = false)
    public Object[][] getData() {
        String path = "data" + java.io.File.separator + "test-data.xlsx";
        return ExcelUtils.getTableArray(path, "cartPageTest", false);
    }
}
