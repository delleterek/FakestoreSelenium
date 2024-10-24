package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BasePage {
    protected WebDriver driver;
    @FindBy(css = ".woocommerce-store-notice__dismiss-link") WebElement demoNotice;

    private By demoNoticeLocator = By.cssSelector(".woocommerce-store-notice__dismiss-link");

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void closeDemoNotice() {
        if (demoNotice.isDisplayed()) {
            demoNotice.click();
        }
    }

    public CategoriesPage goToShop(String baseUrl) {
        driver.navigate().to(baseUrl + "/shop/");
        return new CategoriesPage(driver);
    }

}
