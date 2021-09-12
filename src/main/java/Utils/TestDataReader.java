package Utils;

public class TestDataReader extends FileReader {

    private String testDataLocation;

    private Address address;
    private Card card;
    private Contact contact;
    private Customer customer;

    public TestDataReader(String testDataLocation){
        super(testDataLocation);
        this.testDataLocation = testDataLocation;
    }

    void loadData() {
        address = new Address(properties);
        card = new Card(properties);
        contact = new Contact(properties);
        customer = new Customer(properties);
    }

    public String getTestDataLocation() {
        return testDataLocation;
    }

    public Address getAddress() {
        return address;
    }

    public Card getCard() {
        return card;
    }

    public Contact getContact() {
        return contact;
    }

    public Customer getCustomer() {
        return customer;
    }
}
