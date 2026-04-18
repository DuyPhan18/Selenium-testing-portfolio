package pages;

import core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class ProductPage extends BasePage {

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(how = How.XPATH, using = "//div[@class='inventory_item']")
    private List<WebElement> productCardList;
    @FindBy(how = How.CLASS_NAME, using = "shopping_cart_badge")
    private WebElement shoppingCartBadge;


    public int isProductListDisplay6Item(){
        int totalProduct =0;
        for (WebElement i : productCardList ){
            totalProduct++;
        }
        return totalProduct;
    }
    public void getItem(int itemIndex){

    }

    public void addToCart(int itemIndex){
        int index = Integer.parseInt(String.valueOf(itemIndex))-1;
         WebElement item = productCardList.get(index);

        WebElement addToCartBtn = item.findElement(By.xpath(".//button[contains(text(), 'Add to cart')]"));
        click(addToCartBtn);
    }

    public void removeFromCart(int itemIndex){
        int index = Integer.parseInt(String.valueOf(itemIndex)) -1;
        WebElement item = productCardList.get(index);

        WebElement removeFromCartBtn = item.findElement(By.xpath(".//button[contains(text(), 'Remove')]"));
        click(removeFromCartBtn);
    }

    public void checkCartBadge(){

    }

}
