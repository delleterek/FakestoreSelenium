package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class ProductsInCategoryPage extends BasePage {
    private WebDriverWait wait;
    private Random random = new Random();
    private By viewBasketButtonLocator = By.cssSelector(".added_to_cart");
    private By productsLocator = By.cssSelector("a.woocommerce-LoopProduct-link");
    private By addToCartButtonLocator = By.cssSelector("a.add_to_cart_button");
    private By productAddedToBasketLocator = By.cssSelector(".add_to_cart_button.added");
    private String productIdAttribute = "data-product_id";

    public ProductsInCategoryPage(WebDriver driver) {
       super(driver);
       wait = new WebDriverWait(driver, 7);
    }

    public ProductPage openRandomProduct() {
        List<WebElement> products = driver.findElements(productsLocator);
        products.get(random.nextInt(products.size())).click();
        return new ProductPage(driver);
    }

    public ProductsInCategoryPage addRandomProductToBasket() {
        List<WebElement> addToCartButtons = driver.findElements(addToCartButtonLocator);
        addToCartButtons.get(random.nextInt(addToCartButtons.size())).click();
        wait.until(ExpectedConditions.elementToBeClickable(viewBasketButtonLocator));
        return this;
    }

    public String getProductId() {
        WebElement productAddedToBasket = wait.until(ExpectedConditions.presenceOfElementLocated(productAddedToBasketLocator));
        return productAddedToBasket.getAttribute(productIdAttribute);
    }

    public BasketPage viewBasket() {
        driver.findElement(viewBasketButtonLocator).click();
        return new BasketPage(driver);
    }

    public Set<String> addAllProductsToBasket(){
        //WebElement shopButton = driver.findElement(By.cssSelector("#menu-item-198"));
        //shopButton.click();
        Set<String> productIdentifiers = new HashSet<>();
        List<WebElement> addToCartButtons = driver.findElements(addToCartButtonLocator);
        for (WebElement addToCartButton : addToCartButtons) {
            addToCartButton.click();
            productIdentifiers.add(addToCartButton.getAttribute(productIdAttribute));
        }
        wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector("a[class='button product_type_simple add_to_cart_button ajax_add_to_cart']"),0));
        wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector(".added_to_cart"),addToCartButtons.size()));
        return productIdentifiers;
    }
}
