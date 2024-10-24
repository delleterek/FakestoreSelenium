package Utils;

public class ConfigurationReader extends FileReader {
    private String configurationLocation;

    private String baseUrl;
    private String hubUrl;
    private String browser;
    private boolean isRemote;
    private boolean isHeadless;

    public ConfigurationReader(String configurationLocation){
        super(configurationLocation);
        this.configurationLocation = configurationLocation;
    }

    void loadData() {
        hubUrl = System.getProperty("hubUrl")!=null ? System.getProperty("hubUrl") : properties.getProperty("hubUrl");
        baseUrl = System.getProperty("baseUrl")!=null ? System.getProperty("baseUrl") : properties.getProperty("baseUrl");
        browser = System.getProperty("browser")!=null ? System.getProperty("browser") : properties.getProperty("browser");
        isRemote = System.getProperty("isRemote")!=null ? Boolean.parseBoolean(System.getProperty("isRemote")) : Boolean.parseBoolean(properties.getProperty("isRemote"));
        isHeadless = System.getProperty("isHeadless")!=null ? Boolean.parseBoolean(System.getProperty("isHeadless")) : Boolean.parseBoolean(properties.getProperty("isHeadless"));
    }

    public boolean isHeadless() {
        return isHeadless;
    }

    public void setHeadless(boolean headless) {
        isHeadless = headless;
    }

    public String getConfigurationLocation() {
        return configurationLocation;
    }

    public String getBrowser() {
        return browser;
    }

    public String getHubUrl() {
        return hubUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public boolean isRemote() { return isRemote; }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setHubUrl(String hubUrl) {
        this.hubUrl = hubUrl;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public void setRemote(boolean remote) {
        isRemote = remote;
    }
}
