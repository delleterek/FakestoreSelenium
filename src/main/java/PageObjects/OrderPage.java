package PageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OrderPage extends BasePage {
    public HeaderSection header;
    private JavascriptExecutor js = (JavascriptExecutor) driver;
    private WebDriverWait wait;
    private By firstNameInputLocator = By.cssSelector("#billing_first_name");
    private By lastNameInputLocator = By.cssSelector("#billing_last_name");
    private By addressInputLocator = By.cssSelector("#billing_address_1");
    private By postalCodeInputLocator = By.cssSelector("#billing_postcode");
    private By cityInputLocator = By.cssSelector("#billing_city");
    private By phoneInputLocator = By.cssSelector("#billing_phone");
    private By emailInputLocator = By.cssSelector("#billing_email");
    private By paymentAreaLocator = By.cssSelector("[class='payment_box payment_method_stripe']");
    private By LoadingAnimationLocator = By.cssSelector("div.blockUI");
    private By orderReviewLocator = By.cssSelector("h1.entry-title");
    private int cardNumberFrameLocator = 0;
    private By cardNumberInputLocator = By.cssSelector("input[name='cardnumber']");
    private int expDateFrameLocator = 1;
    private By expDateInputLocator = By.cssSelector("input[name='exp-date']");
    private int cvcFrameLocator = 2;
    private By cvcInputLocator = By.cssSelector("input[name='cvc']");
    private By errorMessageLocator = By.cssSelector(".woocommerce-error");
    private By createAccountLocator = By.cssSelector("#createaccount");
    private By createAccountPasswordInputLocator = By.cssSelector("#account_password");
    private By placeOrderButtonLocator = By.cssSelector("#place_order");
    private By termsCheckboxLocator = By.cssSelector("#terms");
    private By loginToggleLocator = By.cssSelector(".showlogin");
    private By submitLoginButtonLocator = By.cssSelector("button[name='login']");
    private By usernameInputLocator = By.cssSelector("#username");
    private By passwordInputLocator = By.cssSelector("#password");
    private By loginFormLocator = By.cssSelector(".woocommerce-form-login-toggle");
    private By outerStripeFrameLocator = By.xpath(".//body/div/iframe");
    private String innerStripeFrameLocator = "challengeFrame";
    private String moreInnerStripeFrameLocator = "acsFrame";
    //private By moreInnerStripeFrameLocator =
    private By authorizePaymentButtonLocator = By.cssSelector("#test-source-authorize-3ds");


    public OrderPage(WebDriver driver){
        super(driver);
        header = new HeaderSection(driver);
        wait = new WebDriverWait(driver, 60);
    }

    public OrderPage scrollToOrderReview() {
        WebElement orderReview = driver.findElement(orderReviewLocator);
        js.executeScript("arguments[0].scrollIntoView(true)", orderReview);
        return this;
    }

    public OrderPage scrollToPaymentArea() {
        WebElement paymentArea = driver.findElement(paymentAreaLocator);
        js.executeScript("arguments[0].scrollIntoView(true)", paymentArea);
        return this;
    }

    public OrderPage waitToLoad() {
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(LoadingAnimationLocator, 0));
        wait.until(ExpectedConditions.numberOfElementsToBe(LoadingAnimationLocator, 0));
        return this;
    }

    public OrderPage insertCardDetails(String cardNumber, String expDate, String cvc) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(cardNumberFrameLocator));
        WebElement cardNumberInput = wait.until(ExpectedConditions.visibilityOfElementLocated(cardNumberInputLocator));
        slowType(cardNumberInput, cardNumber);
        driver.switchTo().parentFrame();
        driver.switchTo().frame(expDateFrameLocator).findElement(expDateInputLocator).sendKeys(expDate);
        driver.switchTo().parentFrame();
        driver.switchTo().frame(cvcFrameLocator).findElement(cvcInputLocator).sendKeys(cvc);
        driver.switchTo().defaultContent();
        return this;
    }

    private void slowType (WebElement element, String text) {
        for (int i = 0; i < text.length(); i++) {
            element.sendKeys(Character.toString(text.charAt(i)));
        }
    }

    public OrderPage fillInPersonalData(String firstName, String lastName, String street,  String postalCode, String city, String phoneNumber, String mailAddress) {
        driver.findElement(firstNameInputLocator).sendKeys(firstName);
        driver.findElement(lastNameInputLocator).sendKeys(lastName);
        driver.findElement(addressInputLocator).sendKeys(street);
        driver.findElement(postalCodeInputLocator).sendKeys(postalCode);
        driver.findElement(cityInputLocator).sendKeys(city);
        driver.findElement(phoneInputLocator).sendKeys(phoneNumber);
        driver.findElement(emailInputLocator).sendKeys(mailAddress);
        return this;
    }

    public OrderSummaryPage submitOrder(boolean acceptTerms) {
        if (acceptTerms) {
            driver.findElement(termsCheckboxLocator).click();
        }
        driver.findElement(placeOrderButtonLocator).click();
        return new OrderSummaryPage(driver);
    }

    public OrderPage submitOrderToSwitchToStripeFrame(boolean acceptTerms) {
        if (acceptTerms) {
            driver.findElement(termsCheckboxLocator).click();
        }
        driver.findElement(placeOrderButtonLocator).click();
        return this;
    }

    public OrderPage submitOrderUnsuccessfully(boolean acceptTerms) {
        if (acceptTerms) {
            driver.findElement(termsCheckboxLocator).click();
        }
        driver.findElement(placeOrderButtonLocator).click();
        return this;
    }

    public OrderPage createAccount(String password) {
        driver.findElement(createAccountLocator).click();
        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(createAccountPasswordInputLocator));
        passwordInput.sendKeys(password);
        return this;
    }

    public OrderPage SignIn(String username, String password) {
        WebElement loginToggle = wait.until(ExpectedConditions.elementToBeClickable(loginToggleLocator));
        loginToggle.click();
        WebElement submitLoginButton = wait.until(ExpectedConditions.elementToBeClickable(submitLoginButtonLocator));
        driver.findElement(usernameInputLocator).sendKeys(username);
        driver.findElement(passwordInputLocator).sendKeys(password);
        submitLoginButton.click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loginFormLocator));
        return this;
    }

    public String getErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessageLocator)).getText();
    }

    public String getPaymentRelatedErrorMessage(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ul.woocommerce-error>li"))).getText();
    }

    public OrderPage switchToStripeFrame(){
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(outerStripeFrameLocator));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(innerStripeFrameLocator));
        return this;
    }

    public OrderSummaryPage authorizePayment() {
        wait.until(ExpectedConditions.elementToBeClickable(authorizePaymentButtonLocator)).submit();
        driver.switchTo().defaultContent();
        return new OrderSummaryPage(driver);
    }

    public OrderPage authorizePaymentForDeclinedCard() {
        try{
            Thread.sleep(2000);
        }
        catch(InterruptedException ie){
        }
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(moreInnerStripeFrameLocator));
        wait.until(ExpectedConditions.elementToBeClickable(authorizePaymentButtonLocator)).submit();
        driver.switchTo().defaultContent();
        return this;
    }

    public OrderPage failPaymentAuthorization() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#test-source-fail-3ds"))).submit();
        driver.switchTo().defaultContent();
        return this;
    }
}
