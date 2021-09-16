package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccountPage extends BasePage {
    public HeaderSection header;
    private WebDriverWait wait;
    private By deleteAccountLocator = By.cssSelector(".delete-me");
    private By usernameLocator = By.cssSelector("#username");
    private By passwordLocator = By.cssSelector("#password");
    private By logInButtonLocator = By.cssSelector("button[name='login']");


    public AccountPage(WebDriver driver) {
        super(driver);
        header = new HeaderSection(driver);
        wait = new WebDriverWait(driver, 7);
    }

    public void deleteAccount() {
        driver.findElement(deleteAccountLocator).click();
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    public AccountPage logIn(String username, String password){
        driver.findElement(usernameLocator).sendKeys(username);
        driver.findElement(passwordLocator).sendKeys(password);
        driver.findElement(logInButtonLocator).click();
        return this;
    }
}
