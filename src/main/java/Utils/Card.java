package Utils;

import java.util.Properties;

public class Card {
    private final String validNumber;
    private final String invalidNumber;
    private final String secureNumber;
    private final String secureDeclinedNumber;
    private final String validExpirationDate;
    private final String invalidExpirationDate;
    private final String cvc;

    public Card(Properties properties) {
        validNumber = properties.getProperty("card.validNumber");
        validExpirationDate = properties.getProperty("card.validExpirationDate");
        invalidNumber = properties.getProperty("card.invalidNumber");
        secureNumber = properties.getProperty("card.3DSecureNumber");
        secureDeclinedNumber = properties.getProperty("card.3DSecureDeclinedNumber");
        invalidExpirationDate = properties.getProperty("card.invalidExpirationDate");
        cvc = properties.getProperty("card.cvc");
    }

    public String getValidNumber() { return validNumber; }

    public String getValidExpirationDate() {
        return validExpirationDate;
    }

    public String getInvalidNumber() {
        return invalidNumber;
    }

    public String getInvalidExpirationDate() {
        return invalidExpirationDate;
    }

    public String getSecureNumber() {
        return secureNumber;
    }

    public String getSecureDeclinedNumber() {
        return secureDeclinedNumber;
    }

    public String getCvc() {
        return cvc;
    }
}
