package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HeaderSection extends BasePage {

    protected HeaderSection(WebDriver driver) {
        super(driver);
    }

    private By orderLocator = By.cssSelector("#menu-item-199");
    private By totalPriceLocator = By.cssSelector(".cart-contents");
    private By accountLocator = By.cssSelector("#menu-item-201");

    public BasketPage viewBasket(){
        driver.findElement(totalPriceLocator).click();
        return new BasketPage(driver);
    }

    public OrderPage goToOrder() {
        driver.findElement(orderLocator).click();
        return new OrderPage(driver);
    }

    public AccountPage goToMyAccount() {
        driver.findElement(accountLocator).click();
        return new AccountPage(driver);
    }
}
