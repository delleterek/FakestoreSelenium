package Utils;

import java.util.Properties;

public class Contact {
        private final String phone;
        private final String unregisteredEmailAddress;
        private final String unregisteredPassword;
        private final String registeredEmailAddress;
        private final String registeredPassword;


        public Contact(Properties properties) {
            phone = properties.getProperty("contact.phone");
            unregisteredEmailAddress = properties.getProperty("contact.unregisteredEmailAddress");
            unregisteredPassword = properties.getProperty("contact.unregisteredPassword");
            registeredEmailAddress = properties.getProperty("contact.registeredEmailAddress");
            registeredPassword = properties.getProperty("contact.registeredPassword");
        }

    public String getPhone() {
        return phone;
    }

    public String getUnregisteredEmailAddress() {
        return unregisteredEmailAddress;
    }

    public String getUnregisteredPassword() {
            return unregisteredPassword;
    }

    public String getRegisteredEmailAddress() {
        return registeredEmailAddress;
    }

    public String getRegisteredPassword() {
        return registeredPassword;
    }
}
