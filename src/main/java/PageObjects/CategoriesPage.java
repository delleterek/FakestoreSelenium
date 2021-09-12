package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Random;

public class CategoriesPage extends BasePage {
    public HeaderSection headerSection;
    private Random random = new Random();
    private By tripCategoriesLocator = By.cssSelector(".product-category");

    public CategoriesPage(WebDriver driver) {
        super(driver);
        headerSection = new HeaderSection(driver);
    }

    public ProductsInCategoryPage openRandomCategory() {
        List<WebElement> tripCategories = driver.findElements(tripCategoriesLocator);
        tripCategories.get(random.nextInt(tripCategories.size())).click();
        return new ProductsInCategoryPage(driver);
    }

    public ProductsInCategoryPage openCategory(String category) {
        driver.findElement(By.cssSelector("img[alt*=" + category + "]")).click();
        return new ProductsInCategoryPage(driver);
    }
}
