package TestsPOM;

import Drivers.DriverFactory;
import Utils.ConfigurationReader;
import Utils.TestDataReader;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.TimeUnit;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {
    protected WebDriver driver;
    protected ConfigurationReader configuration;
    protected TestDataReader testData;

    private String testDataLocation = "src/test/resources/TestData.properties";
    private String configurationLocation = "src/Configs/Configuration.properties";

    @BeforeClass
    public void getConfiguration() {
        configuration = new ConfigurationReader(configurationLocation);
        testData = new TestDataReader(testDataLocation);
    }

    @BeforeMethod
    public void driverSetup() {
        DriverFactory driverFactory = new DriverFactory();
        driver = driverFactory.create(configuration);

        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
    }

    @AfterMethod
    public void driverQuit() {
        driver.quit();
    }
}
