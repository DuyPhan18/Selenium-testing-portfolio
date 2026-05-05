package pages;

import core.BasePage;
import org.openqa.selenium.By;
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
    @FindBy(how = How.CLASS_NAME, using = "inventory_item")
    private List<WebElement> productCardList;
    @FindBy(how = How.CLASS_NAME, using = "cart_item")
    private List<WebElement> cartItemList;
    @FindBy(how = How.CLASS_NAME, using = "inventory_item_name")
    private List<WebElement> cartItemNameList;

    public void goToCart(){
        click(cartBtn);
    }

    public boolean verifyItemInCart(String itemNames, String amount){
        int totalAmountInCart = cartItemList.size();
        if (totalAmountInCart != Integer.parseInt(amount)) {
            System.out.println("Cart amount mismatch! Expected: "
                    + amount + ", Actual: " + totalAmountInCart);
            return false;
        }
        String[] names = itemNames.split(",");
        for (String name : names) {
            boolean found = false;
            for (WebElement cartItem : cartItemNameList) {
                if (cartItem.getText().equals(name.trim())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Item not found in cart: " + name.trim());
                return false;  // ✅ return false nếu thiếu bất kỳ item nào
            }
        }
        return true;
    }
    public void removeFromCart(String itemNames) {
        String[] names = itemNames.split(",");
        for (String name : names) {
            for (int i = 0; i < cartItemNameList.size(); i++) {
                if (cartItemNameList.get(i).getText().equals(name.trim())) {
                    WebElement cartItem = cartItemList.get(i);
                    WebElement removeBtn = cartItem.findElement(
                            By.xpath(".//button[contains(text(), 'Remove')]"));
                    click(removeBtn);
                    System.out.println("Removed: " + name.trim());
                    break;
                }
            }
        }
    }
}
