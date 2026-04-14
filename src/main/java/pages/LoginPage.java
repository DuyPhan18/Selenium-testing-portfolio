package pages;

import core.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    //locator
    @FindBy(how = How.CLASS_NAME, using = "login-box")
    private WebElement loginBox;
    @FindBy(how = How.ID, using = "user-name")
    private WebElement userNameInput;
    @FindBy(how = How.ID, using = "password")
    private WebElement passwordInput;
    @FindBy(how = How.ID, using = "login-button")
    private WebElement loginBtn;
    @FindBy(how = How.XPATH, using = "//h3[@data-test='error']")
    private WebElement errorMsg;

    public void enterUsername(String username){
        type(userNameInput, username);
    }

    public void enterPassword(String password){
        type(passwordInput, password);
    }

    public  void clickLoginBtn(){
        click(loginBtn);
    }
    public boolean isLoginBoxDisplay(){
       return isDisplayed(loginBox);
    }
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginBtn();
    }
    public String getErrorMessage(){
        return getText(errorMsg);
    }

}
