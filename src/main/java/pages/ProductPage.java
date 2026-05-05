package pages;

import core.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class ProductPage extends BasePage {

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(how = How.XPATH, using = "//div[@class='inventory_item']")
    private List<WebElement> productCardList;
    @FindBy(how = How.CLASS_NAME, using = "shopping_cart_badge")
    private WebElement shoppingCartBadge;
    @FindBy(how = How.CLASS_NAME, using = "inventory_item_name")
    private List<WebElement> itemNameList;

    public int isProductListDisplay6Item(){
        return productCardList.size();
    }
//    public void addToCart(int itemIndex){
//
//        int index = itemIndex-1;
//        WebElement item = productCardList.get(index);
//        scrollToElement(item);
//        WebElement addToCartBtn = item.findElement(By.xpath(".//button[contains(text(), 'Add to cart')]"));
//        if (isDisplayed(addToCartBtn)){
//            System.out.println("Found item");
//        }
//        click(addToCartBtn);
//        System.out.println("Clicked Add to cart for item: " + itemIndex);
//        scrollToElement(shoppingCartBadge);
//
//    }
//    public void addToCart(String itemIndexes) {
//        String[] indexes = itemIndexes.split(",");
//        System.out.println("Total items to add: " + indexes.length);
//        for (String index : indexes) {
//            System.out.println("Adding item index: " + index.trim());
//            addToCart(Integer.parseInt(index.trim()));
//        }
//    }
public void addToCartByName(String itemName) {
    for (int i = 0; i < itemNameList.size(); i++) {
        String itemNameText = itemNameList.get(i).getText();
        if (itemNameText.equals(itemName)) {
            WebElement productCard = productCardList.get(i);
            scrollToElement(productCard);
            WebElement addToCartBtn = productCard.findElement(
                    By.xpath(".//button[contains(text(), 'Add to cart')]"));
            if (isDisplayed(addToCartBtn)) {
                System.out.println("Found item: " + itemName);
            }
            click(addToCartBtn);
            System.out.println("Clicked Add to cart for item: " + itemName);
            scrollToElement(shoppingCartBadge);
            return;
        }
    }
    System.out.println("Item not found: " + itemName);
}

    public void addToCartByNames(String itemNames) {
        String[] names = itemNames.split(",");
        System.out.println("Total items to add: " + names.length);
        for (String name : names) {
            System.out.println("Adding item: " + name.trim());
            addToCartByName(name.trim());
        }
    }

    public void removeFromCart(int itemIndex){
        int index = itemIndex -1;
        WebElement item = productCardList.get(index);
        WebElement removeFromCartBtn = item.findElement(By.xpath(".//button[contains(text(), 'Remove')]"));
        click(removeFromCartBtn);
    }
    public int getCartBadgeCount() {
        try {
            List<WebElement> badges = driver.findElements(
                    By.className("shopping_cart_badge"));
            if (badges.isEmpty()) {
                return 0;  // ✅ không có badge = 0 item
            }
            return Integer.parseInt(badges.get(0).getText());
        } catch (Exception e) {
            return 0;
        }
    }
    public boolean checkCartBadge(int expectedCount) {
        try {
            int badgeText = getCartBadgeCount();
            System.out.println("Badge text: " + badgeText);
            return badgeText == expectedCount;
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            System.out.println("Badge not found, expected: " + expectedCount);
            return expectedCount == 0;
        }
    }
    public void clearCart() {
        try {
            List<WebElement> removeButtons = driver.findElements(
                    By.xpath("//button[contains(text(), 'Remove')]"));
            for (WebElement btn : removeButtons) {
                btn.click();
            }
            System.out.println("Cart cleared!");
        } catch (Exception e) {
            System.out.println("Cart already empty");
        }
    }



}
