package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BasePage {
    protected WebDriver driver;
    private By demoNoticeLocator = By.cssSelector(".woocommerce-store-notice__dismiss-link");

    protected BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public void closeDemoNotice() {
        driver.findElement(demoNoticeLocator).click();
    }

    public CategoriesPage goToShop(String baseUrl) {
        driver.navigate().to(baseUrl + "/shop/");
        return new CategoriesPage(driver);
    }

}
