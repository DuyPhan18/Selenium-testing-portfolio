package pages;

import core.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class CartPage extends BasePage {
    public CartPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(how = How.ID, using = "shopping_cart_container")
    private WebElement cartBtn;
    @FindBy(how = How.CLASS_NAME, using = "inventory_item_name")
    private List<WebElement> itemNameList;

}
