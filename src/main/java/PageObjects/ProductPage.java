package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Random;

public class ProductPage extends BasePage {
    private WebDriverWait wait;
    public HeaderSection header;
    private Random random = new Random();
    private By addToBasketButtonLocator = By.cssSelector("button[name='add-to-cart']");
    private By viewBasketButtonLocator = By.cssSelector(".woocommerce-message>.button");
    private By quantityInputLocator = By.cssSelector("input[name='quantity']");
    private String productIdAttribute = "value";


    public ProductPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, 7);
        header = new HeaderSection(driver);
    }

    public String getProductId() {
        return driver.findElement(addToBasketButtonLocator).getAttribute(productIdAttribute);
    }

    public ProductPage addToBasket() {
        driver.findElement(addToBasketButtonLocator).click();
        return this;
    }

    public ProductPage changeQuantity (int quantity) {
        WebElement quantityInput = driver.findElement(quantityInputLocator);
        quantityInput.clear();
        quantityInput.sendKeys(String.valueOf(quantity));
        return this;
    }

    public int insertRandomQuantity (int bound) {
        WebElement quantityInput = driver.findElement(By.cssSelector("input[name='quantity']"));
        quantityInput.clear();
        int randomQuantity = random.nextInt(bound) + 1;
        quantityInput.sendKeys(String.valueOf(randomQuantity));
        return randomQuantity;
    }

    public BasketPage viewBasket() {
        wait.until(ExpectedConditions.elementToBeClickable(viewBasketButtonLocator)).click();
        return new BasketPage(driver);
    }
}
