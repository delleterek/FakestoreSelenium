package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BasketPage extends BasePage {
    private WebDriverWait wait;
    private Actions actions;
    private By shoppingTableLocator = By.cssSelector(".woocommerce-cart-form>table");
    private By removeButtonLocator = By.cssSelector(".remove");
    private By quantityFieldLocator = By.cssSelector("div.quantity>input");
    private By updateBasketLocator = By.cssSelector("[name='update_cart']");
    private By LoadingAnimation = By.cssSelector("div.blockUI");
    private By alertMessage = By.cssSelector("div.woocommerce-message");
    private String productIdAttribute = "data-product_id";
    private String quantityAttribute = "value";


    public BasketPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, 7);
        actions = new Actions(driver);
    }

    public String getProductId() {
        wait.until(ExpectedConditions.presenceOfElementLocated(shoppingTableLocator));
        return driver.findElement(removeButtonLocator).getAttribute(productIdAttribute);
    }

    public int getQuantity() {
        WebElement quantityField = driver.findElement(quantityFieldLocator);
        return Integer.parseInt(quantityField.getAttribute(quantityAttribute));
    }

    public Set<String> getProductIdentifiers() {
        wait.until(ExpectedConditions.presenceOfElementLocated(shoppingTableLocator));
        List<WebElement> products = driver.findElements(removeButtonLocator);
        Set<String> productIdentifiers = new HashSet<>();
        for (WebElement product:products) {
            productIdentifiers.add(product.getAttribute(productIdAttribute));
        }
        return productIdentifiers;
    }

    public int sumQuantities() {
        List<WebElement> quantityFields = driver.findElements(quantityFieldLocator);
        int sumOfQuantities = 0;
        for (WebElement quantityField : quantityFields) {
            Integer productQuantity = Integer.valueOf(quantityField.getAttribute(quantityAttribute));
            sumOfQuantities += productQuantity;
        }
        return sumOfQuantities;
    }

    public BasketPage changeQuantityByMouse() {
        WebElement quantityInput = driver.findElement(quantityFieldLocator);
        actions.click(quantityInput).moveByOffset(20,-5).click().click().click().click().click().click().moveByOffset(0,10).click().click().build().perform();
        return this;
    }

    public BasketPage changeQuantity(int quantity) {
        WebElement quantityInput = driver.findElement(quantityFieldLocator);
        quantityInput.clear();
        quantityInput.sendKeys(Integer.toString(quantity));
        return this;
    }

    public BasketPage updateBasket() {
        driver.findElement(updateBasketLocator).click();
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(LoadingAnimation, 0));
        wait.until(ExpectedConditions.numberOfElementsToBe(LoadingAnimation, 0));
        return this;
    }

    public BasketPage removeProduct() {
        driver.findElement(removeButtonLocator).click();
       return this;
    }

    public String getAlertText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(alertMessage)).getText();
    }
}
