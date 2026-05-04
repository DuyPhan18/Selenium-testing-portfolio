package pages;

import core.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class CheckOutPage extends BasePage {
    public CheckOutPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(how = How.ID, using = "checkout")
    private WebElement checkOutBtn;
    @FindBy(how = How.ID, using = "first-name")
    private WebElement firstNameInput;
    @FindBy(how = How.ID, using = "last-name")
    private WebElement lastNameInput;
    @FindBy(how = How.ID, using = "postal-code")
    private WebElement postalCodeInput;
    @FindBy(how = How.ID, using = "continue")
    private WebElement continueBtn;
    @FindBy(how = How.XPATH, using = "//h3[@data-test='error']")
    private WebElement errorMsg;

    public void goToCheckOutPage(){
        click(checkOutBtn);
    }
    public void enterFirstName(String fname){
        type(firstNameInput, fname);
    }
    public void enterLastName(String lname){
        type(lastNameInput, lname);
    }
    public void enterPostalCode(String ptCode){
        type(postalCodeInput, ptCode);
    }

    public void enterInfo(String fname, String lname, String ptCode) {
        enterFirstName(fname);
        enterLastName(lname);
        enterPostalCode(ptCode);
        click(continueBtn);
    }
    public String getErrorMessage(){
        return getText(errorMsg);
    }


}
