package TestsPOM;

import PageObjects.CategoriesPage;
import PageObjects.OrderPage;
import PageObjects.OrderSummaryPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrderPlacementTests extends BaseTest {

    @Test
    public void orderPlacementWithoutCreatingAnAccountTest() {
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
                .scrollToPaymentArea().waitToLoad()
                .insertCardDetails(testData.getCard().getValidNumber(),testData.getCard().getValidExpirationDate(),testData.getCard().getCvc())
                .submitOrder(true).waitToLoad();
        assertAll(
                ()-> assertEquals("Dziękujemy. Otrzymaliśmy Twoje zamówienie.", orderSummary.getOrderConfirmationText(), "Order was not placed correctly"),
                ()-> assertTrue(orderSummary.isOrderIdDisplayed(), "Order summary does not contain order ID"),
                ()-> assertTrue(orderSummary.isDateOfOrderDisplayed(), "Order summary does not contain date of order"),
                ()-> assertTrue(orderSummary.isSumOfOrderDisplayed(), "Order summary does not contain sum of order"),
                ()-> assertTrue(orderSummary.isPaymentMethodDisplayed(), "Order summary does not contain payment method"),
                ()-> assertTrue(orderSummary.isProductQuantityDisplayed(), "Product quantity is not displayed"),
                ()-> assertTrue(orderSummary.isProductNameDisplayed(), "Product name is not displayed")
        );
    }

    @Test
    public void orderPlacementWithoutFillingPersonalData() {
        CategoriesPage categoriesPage = new CategoriesPage(driver).goToShop(configuration.getBaseUrl());
        categoriesPage.closeDemoNotice();
        OrderPage orderPage = categoriesPage
                .openRandomCategory().openRandomProduct().addToBasket().header.goToOrder()
                .scrollToPaymentArea().waitToLoad()
                .insertCardDetails(testData.getCard().getValidNumber(),testData.getCard().getValidExpirationDate(),testData.getCard().getCvc())
                .submitOrderUnsuccessfully(true).waitToLoad();
        assertEquals("Imię płatnika jest wymaganym polem.\n" +
                "Nazwisko płatnika jest wymaganym polem.\n" +
                "Ulica płatnika jest wymaganym polem.\n" +
                "Kod pocztowy płatnika nie jest prawidłowym kodem pocztowym.\n" +
                "Miasto płatnika jest wymaganym polem.\n" +
                "Telefon płatnika jest wymaganym polem.\n" +
                "Adres email płatnika jest wymaganym polem.", orderPage.getErrorMessage(), "Error message is not as expected");
    }

    @Test
    public void orderPlacementWithoutTermsAccepted() {
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
                .scrollToPaymentArea().waitToLoad()
                .insertCardDetails(testData.getCard().getValidNumber(),testData.getCard().getValidExpirationDate(),testData.getCard().getCvc())
                .submitOrderUnsuccessfully(false).waitToLoad();
        assertEquals("Proszę przeczytać i zaakceptować regulamin sklepu aby móc sfinalizować zamówienie.", orderPage.getErrorMessage(), "Error message is not as expected");
    }

    @Test
    public void orderPlacementWithCreatingAnAccountTest() {
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
                .scrollToPaymentArea().createAccount(testData.getContact().getUnregisteredPassword())
                .insertCardDetails(testData.getCard().getValidNumber(),testData.getCard().getValidExpirationDate(),testData.getCard().getCvc()).submitOrder(true).waitToLoad();
        assertAll(
                ()-> assertEquals("Dziękujemy. Otrzymaliśmy Twoje zamówienie.", orderSummary.getOrderConfirmationText(), "Order was not placed correctly"),
                ()-> assertTrue(orderSummary.isOrderIdDisplayed(), "Order summary does not contain order ID"),
                ()-> assertTrue(orderSummary.isDateOfOrderDisplayed(), "Order summary does not contain date of order"),
                ()-> assertTrue(orderSummary.isSumOfOrderDisplayed(), "Order summary does not contain sum of order"),
                ()-> assertTrue(orderSummary.isPaymentMethodDisplayed(), "Order summary does not contain payment method"),
                ()-> assertTrue(orderSummary.isProductQuantityDisplayed(), "Product quantity is not displayed"),
                ()-> assertTrue(orderSummary.isProductNameDisplayed(), "Product name is not displayed")
        );
        orderSummary.header.goToMyAccount().deleteAccount();
    }

    @Test
    public void orderPlacementWithSigningInAtOrderPage() {
        CategoriesPage categoriesPage = new CategoriesPage(driver).goToShop(configuration.getBaseUrl());
        categoriesPage.closeDemoNotice();
        OrderSummaryPage orderSummary = categoriesPage
                .openRandomCategory().openRandomProduct().addToBasket().header.goToOrder()
                .SignIn(testData.getContact().getRegisteredEmailAddress(), testData.getContact().getRegisteredPassword())
                .scrollToPaymentArea().waitToLoad()
                .insertCardDetails(testData.getCard().getValidNumber(),testData.getCard().getValidExpirationDate(),testData.getCard().getCvc())
                .submitOrder(true).waitToLoad();
        assertAll(
                ()-> assertEquals("Dziękujemy. Otrzymaliśmy Twoje zamówienie.", orderSummary.getOrderConfirmationText(), "Order was not placed correctly"),
                ()-> assertTrue(orderSummary.isOrderIdDisplayed(), "Order summary does not contain order ID"),
                ()-> assertTrue(orderSummary.isDateOfOrderDisplayed(), "Order summary does not contain date of order"),
                ()-> assertTrue(orderSummary.isSumOfOrderDisplayed(), "Order summary does not contain sum of order"),
                ()-> assertTrue(orderSummary.isPaymentMethodDisplayed(), "Order summary does not contain payment method"),
                ()-> assertTrue(orderSummary.isProductQuantityDisplayed(), "Product quantity is not displayed"),
                ()-> assertTrue(orderSummary.isProductNameDisplayed(), "Product name is not displayed")
        );
    }
}


