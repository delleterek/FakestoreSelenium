package Drivers;

import Utils.ConfigurationReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {
    private RemoteWebDriver driver;

    public WebDriver create(ConfigurationReader configuration){
        WebDriver driver;
        if (configuration.isRemote()) {
            driver = getRemoteDriver(configuration);
        }
        else {
            driver = getLocalDriver(configuration);
        }
        return driver;
    }

    private WebDriver getRemoteDriver(ConfigurationReader configuration) {
        switch (Browser.valueOf(configuration.getBrowser())) { // tutaj potrzebne jest enum a nie String, dlatego trzeba użyć valueOf
            case CHROME:
                return getChromeDriver(configuration);
            case FIREFOX:
                return getFirefoxDriver(configuration);
            default:
                throw new IllegalArgumentException("Provided browser setup doesn't exist");
        }
    }

    private WebDriver getLocalDriver(ConfigurationReader configuration) {
        switch (Browser.valueOf(configuration.getBrowser())) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                if (configuration.isHeadless()){
                    options.addArguments("headless");
                }
                return new ChromeDriver(options);
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver();
            default:
                throw new IllegalArgumentException("Provided browser doesn't exist");
        }
    }

    private WebDriver getChromeDriver(ConfigurationReader configuration) {
        ChromeOptions options = new ChromeOptions();
        //options.setCapability(CapabilityType.VERSION, "66");
        return getDriver(options, configuration);
    }

    private WebDriver getFirefoxDriver(ConfigurationReader configuration) {
        FirefoxOptions options = new FirefoxOptions();
        return getDriver(options, configuration);
    }

    private WebDriver getDriver(MutableCapabilities options, ConfigurationReader configuration) { // zmienna options jest takiego typu dlatego że klasy FirefoxOptions i ChromeOptions są rozszerzeniem klasy MutableCapabilities
        try {
            driver = new RemoteWebDriver(new URL(configuration.getHubUrl()), options);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println(e + "was thrown. HubUrl in the configuration file is incorrect or missing. " +
                    "Check the configuration file: " + configuration.getConfigurationLocation());
        }
        return driver;
    }

}
