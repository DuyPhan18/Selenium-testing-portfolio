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
    public void removeFromCart(String itemName) {
        for (int i = 0; i < cartItemList.size(); i++) {
            String itemNameText = cartItemList.get(i).getText();
            if (itemNameText.equals(itemName)) {
                WebElement productCard = productCardList.get(i);  // ✅ lấy card tương ứng
                scrollToElement(productCard);
                WebElement addToCartBtn = productCard.findElement(
                        By.xpath(".//button[contains(text(), 'Remove')]"));
                if (isDisplayed(addToCartBtn)) {
                    System.out.println("Found item: " + itemName);
                }
                click(addToCartBtn);
                System.out.println("Clicked Add to cart for item: " + itemName);
                return;  // ✅ thêm return sau khi add xong
            }
        }
        System.out.println("Item not found: " + itemName);
    }
    public void removeFromCart(int itemIndex){
        int index = itemIndex -1;
        WebElement item = cartItemList.get(index);
        WebElement removeFromCartBtn = item.findElement(By.xpath(".//button[contains(text(), 'Remove')]"));
        click(removeFromCartBtn);
    }

}
