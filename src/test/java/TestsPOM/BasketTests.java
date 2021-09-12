package TestsPOM;

import PageObjects.BasketPage;
import PageObjects.CategoriesPage;
import PageObjects.ProductPage;
import PageObjects.ProductsInCategoryPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BasketTests extends BaseTest {

    @Test
    public void addToCartFromProductViewTest() {
        CategoriesPage categoriesPage = new CategoriesPage(driver);
        categoriesPage.goToShop(configuration.getBaseUrl()).closeDemoNotice();
        ProductPage chosenProduct = categoriesPage.openRandomCategory().openRandomProduct();
        String chosenProductId = chosenProduct.getProductId();
        String productIdInBasket = chosenProduct.addToBasket().viewBasket().getProductId();
        assertEquals(chosenProductId, productIdInBasket, "Product added to basket is not seen in the basket.");
    }

    @Test
    public void addToCartFromCategoryViewTest() {
        CategoriesPage categoriesPage = new CategoriesPage(driver);
        categoriesPage.goToShop(configuration.getBaseUrl()).closeDemoNotice();
        ProductsInCategoryPage chosenProduct = categoriesPage.openRandomCategory().addRandomProductToBasket();
        String chosenProductId = chosenProduct.getProductId();
        String productIdInBasket = chosenProduct.viewBasket().getProductId();
        assertEquals(chosenProductId, productIdInBasket, "Product added to basket is not seen in the basket.");
    }

    @Test
    public void addOneProductTenTimesTest() {
        CategoriesPage categoriesPage = new CategoriesPage(driver);
        categoriesPage.goToShop(configuration.getBaseUrl()).closeDemoNotice();
        ProductPage chosenProduct = categoriesPage.openRandomCategory().openRandomProduct();
        String chosenProductId = chosenProduct.getProductId();
        BasketPage productInBasket = chosenProduct.changeQuantity(10).addToBasket().viewBasket();
        String productIdInBasket = productInBasket.getProductId();
        int quantityInBasket = productInBasket.getQuantity();
        assertAll(
                ()->assertEquals(chosenProductId, productIdInBasket, "Product added to basket is not seen in the basket"),
                ()->assertEquals(10, quantityInBasket, "Quantity of product is not as expected")
        );
    }

    @Test
    public void addAtLeast10RandomProductsInRandomOrderTest() {
        int quantity = 0;
        Set<String> addedProductsIdentifiers = new HashSet<>();
        CategoriesPage categoriesPage = new CategoriesPage(driver);
        categoriesPage.goToShop(configuration.getBaseUrl()).closeDemoNotice();
        ProductPage chosenProduct;
        do {
            chosenProduct = categoriesPage.goToShop(configuration.getBaseUrl()).openRandomCategory().openRandomProduct();
            addedProductsIdentifiers.add(chosenProduct.getProductId());
            quantity = quantity + chosenProduct.insertRandomQuantity(10);
            chosenProduct.addToBasket();
        } while (quantity < 10);
        final int quantityOfProductsAdded = quantity;
        BasketPage productsInBasket = chosenProduct.viewBasket();
        Set<String> productsInBasketIdentifiers = productsInBasket.getProductIdentifiers();
        int quantityOfProductsInBasket = productsInBasket.sumQuantities();
        assertAll(
                ()->assertEquals(addedProductsIdentifiers, productsInBasketIdentifiers, "Products added to basket are not the same as the products in basket"),
                ()->assertEquals(quantityOfProductsAdded, quantityOfProductsInBasket, "Quantity of products added to basket is not equal to quantity of products in basket")
        );
    }

    @Test
    public void add11DifferentProductsToCartTest() {
        String categoryWindsurfing = "Windsurfing";
        String categoryYoga = "Yoga";
        Set<String> addedProductsIdentifiers = new HashSet<>();
        CategoriesPage categoriesPage = new CategoriesPage(driver);

        categoriesPage.goToShop(configuration.getBaseUrl()).closeDemoNotice();

        addedProductsIdentifiers.addAll(categoriesPage.openCategory(categoryYoga).addAllProductsToBasket());
        addedProductsIdentifiers.addAll(categoriesPage.goToShop(configuration.getBaseUrl()).openCategory(categoryWindsurfing).addAllProductsToBasket());
        BasketPage productsInBasket = categoriesPage.headerSection.viewBasket();
        Set<String> productsInBasketIdentifiers = productsInBasket.getProductIdentifiers();
        assertAll(
                ()->assertEquals(addedProductsIdentifiers, productsInBasketIdentifiers, "Products added to basket are not the same as the products in basket"),
                ()->assertEquals(11, productsInBasketIdentifiers.size(), "The amount of different products in basket is not as expected")
        );
    }

    @Test
    public void changeProductQuantityInBasketTest() {
        CategoriesPage categoriesPage = new CategoriesPage(driver);
        categoriesPage.goToShop(configuration.getBaseUrl()).closeDemoNotice();
        int changedQuantity = categoriesPage.openRandomCategory().openRandomProduct().addToBasket().viewBasket().changeQuantity(8).updateBasket().getQuantity();
        assertEquals(8, changedQuantity, "Actual quantity of product is not as expected");
    }

    @Test
    public void removeProductFromBasketTest() {
        CategoriesPage categoriesPage = new CategoriesPage(driver).goToShop(configuration.getBaseUrl());
        categoriesPage.closeDemoNotice();
        String alertText = categoriesPage.openRandomCategory().openRandomProduct().addToBasket().viewBasket().removeProduct().getAlertText();
        Assertions.assertTrue(alertText.contains("Usunięto"), "Product was not removed from basket");
    }
}
