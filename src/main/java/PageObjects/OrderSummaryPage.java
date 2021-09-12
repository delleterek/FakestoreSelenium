package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OrderSummaryPage extends BasePage {
    public HeaderSection header;
    private WebDriverWait wait;
    private By LoadingAnimationLocator = By.cssSelector("div.blockUI");

    public OrderSummaryPage(WebDriver driver) {
        super(driver);
        header = new HeaderSection(driver);
        wait = new WebDriverWait(driver, 10);
    }

    public OrderSummaryPage waitToLoad() {
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(LoadingAnimationLocator, 0));
        wait.until(ExpectedConditions.numberOfElementsToBe(LoadingAnimationLocator, 0));
        return this;
    }

    public String getOrderConfirmationText() {
        return driver.findElement(By.cssSelector("div.woocommerce-order>p")).getText();
    }

    public boolean isOrderIdDisplayed() {
        return driver.findElement(By.cssSelector("li.order")).isDisplayed();
    }

    public boolean isDateOfOrderDisplayed() {
        return driver.findElement(By.cssSelector("li.date")).isDisplayed();
    }

    public boolean isSumOfOrderDisplayed() {
        return driver.findElement(By.cssSelector("li.total")).isDisplayed();
    }

    public boolean isPaymentMethodDisplayed() {
        return driver.findElement(By.cssSelector("li.method")).isDisplayed();
    }

    public boolean isProductQuantityDisplayed() {
        return driver.findElement(By.cssSelector(".product-quantity")).isDisplayed();
    }

    public boolean isProductNameDisplayed() {
        return driver.findElement(By.cssSelector("td.product-name")).isDisplayed();
    }

}
