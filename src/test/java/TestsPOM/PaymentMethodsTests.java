package TestsPOM;

import PageObjects.CategoriesPage;
import PageObjects.OrderPage;
import PageObjects.OrderSummaryPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.testng.annotations.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentMethodsTests extends BaseTest {

    @Test
    public void authentication3DSecureCompletedTest() {
        CategoriesPage categoriesPage = new CategoriesPage(driver).goToShop(configuration.getBaseUrl());
        categoriesPage.closeDemoNotice();
        OrderSummaryPage orderSummary = categoriesPage
                .openRandomCategory().openRandomProduct().addToBasket().header.goToOrder().scrollToOrderReview()
                .fillInPersonalData(
                        testData.getCustomer().getName(),
                        testData.getCustomer().getLastName(),
                        testData.getAddress().getStreet(),
                        testData.getAddress().getPostalCode(),
                        testData.getAddress().getCity(),
                        testData.getContact().getPhone(),
                        testData.getContact().getUnregisteredEmailAddress())
                .scrollToPaymentArea()
                .waitToLoad()
                .insertCardDetails(testData.getCard().getSecureNumber(),testData.getCard().getValidExpirationDate(),testData.getCard().getCvc())
                .submitOrderToSwitchToStripeFrame(true)
                .switchToStripeFrame()
                .authorizePayment()
                .waitToLoad();
        assertEquals("Dziękujemy. Otrzymaliśmy Twoje zamówienie.",orderSummary.getOrderConfirmationText(), "Authentication 3D Secure Test was not completed correctly");
    }

    @Test
    public void authentication3DSecureFailedTest() {
        CategoriesPage categoriesPage = new CategoriesPage(driver).goToShop(configuration.getBaseUrl());
        categoriesPage.closeDemoNotice();
        OrderPage orderPage = categoriesPage
                .openRandomCategory().openRandomProduct().addToBasket().header.goToOrder().scrollToOrderReview()
                .fillInPersonalData(
                        testData.getCustomer().getName(),
                        testData.getCustomer().getLastName(),
                        testData.getAddress().getStreet(),
                        testData.getAddress().getPostalCode(),
                        testData.getAddress().getCity(),
                        testData.getContact().getPhone(),
                        testData.getContact().getUnregisteredEmailAddress())
                .scrollToPaymentArea()
                .waitToLoad()
                .insertCardDetails(testData.getCard().getSecureNumber(),testData.getCard().getValidExpirationDate(),testData.getCard().getCvc())
                .submitOrderToSwitchToStripeFrame(true)
                .switchToStripeFrame()
                .failPaymentAuthorization();
        assertEquals("Nie można przetworzyć tej płatności, spróbuj ponownie lub użyj alternatywnej metody.",orderPage.getPaymentRelatedErrorMessage(), "Authentication 3D Secure Test was not failed correctly");
    }

    @Test
    public void authentication3DSecureDeclinedCardTest() {
        CategoriesPage categoriesPage = new CategoriesPage(driver).goToShop(configuration.getBaseUrl());
        categoriesPage.closeDemoNotice();
        OrderPage orderPage = categoriesPage
                .openRandomCategory().openRandomProduct().addToBasket().header.goToOrder().scrollToOrderReview()
                .fillInPersonalData(
                        testData.getCustomer().getName(),
                        testData.getCustomer().getLastName(),
                        testData.getAddress().getStreet(),
                        testData.getAddress().getPostalCode(),
                        testData.getAddress().getCity(),
                        testData.getContact().getPhone(),
                        testData.getContact().getUnregisteredEmailAddress())
                .scrollToPaymentArea()
                .waitToLoad()
                .insertCardDetails(testData.getCard().getSecureDeclinedNumber(),testData.getCard().getValidExpirationDate(),testData.getCard().getCvc())
                .submitOrderToSwitchToStripeFrame(true)
                .switchToStripeFrame()
                .authorizePaymentForDeclinedCard();
        assertEquals("Karta została odrzucona.", orderPage.getPaymentRelatedErrorMessage(), "Card was not declined");
    }

    @DisplayName("Incorrect card details tests")
    @ParameterizedTest(name = "{3}")
    @CsvFileSource(resources = "/IncorrectCardDetailsTestData.csv", numLinesToSkip = 1) // żeby odczytywało poprawnie polskie znaki z pliku tekstowego, należy zmienić kodowanie z ANSI na UTF-8
    void incorrectCardDetailsTest (String cardNumber, String expDate, String cvc, String expectedAlertMessage){
        CategoriesPage categoriesPage = new CategoriesPage(driver).goToShop(configuration.getBaseUrl());
        categoriesPage.closeDemoNotice();
        OrderPage orderPage = categoriesPage
                .openRandomCategory().openRandomProduct().addToBasket().header.goToOrder().scrollToPaymentArea()
                .waitToLoad()
                .insertCardDetails(cardNumber, expDate, cvc)
                .submitOrderUnsuccessfully(true);
        assertEquals(expectedAlertMessage,orderPage.getPaymentRelatedErrorMessage(),"Error message was not as expected");
    }

    @DisplayName("Declined payment without authentication tests")
    @ParameterizedTest(name = "{3}")
    @CsvFileSource(resources = "/PaymentDeclinedTestData.csv", numLinesToSkip = 1)
    void declinedPaymentWithoutAuthenticationTest (String cardNumber, String expDate, String cvc, String expectedAlertMessage){
        CategoriesPage categoriesPage = new CategoriesPage(driver).goToShop(configuration.getBaseUrl());
        categoriesPage.closeDemoNotice();
        OrderPage orderPage = categoriesPage
                .openRandomCategory().openRandomProduct().addToBasket().header.goToOrder().scrollToOrderReview()
                .fillInPersonalData(
                        testData.getCustomer().getName(),
                        testData.getCustomer().getLastName(),
                        testData.getAddress().getStreet(),
                        testData.getAddress().getPostalCode(),
                        testData.getAddress().getCity(),
                        testData.getContact().getPhone(),
                        testData.getContact().getUnregisteredEmailAddress())
                .scrollToPaymentArea()
                .waitToLoad()
                .insertCardDetails(cardNumber,expDate,cvc)
                .submitOrderUnsuccessfully(true)
                .waitToLoad();
        assertEquals(expectedAlertMessage,orderPage.getErrorMessage(),"Error message was not as expected");
    }
}


