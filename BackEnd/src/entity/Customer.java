package entity;

public class Customer {
    private String customerId;
    private String customerName;
    private String customerAddress;
    private int customerContact;

    public Customer() {
    }

    public Customer(String customerId) {
        this.customerId = customerId;
    }

    public Customer(String customerId, String customerName) {
        this.customerId = customerId;
        this.customerName = customerName;
    }

    public Customer(String customerId, int customerContact) {
        this.customerId = customerId;
        this.customerContact = customerContact;
    }

    public Customer(String customerId, String customerName, String customerAddress, int customerContact) {
        this.setCustomerId(customerId);
        this.setCustomerName(customerName);
        this.setCustomerAddress(customerAddress);
        this.setCustomerContact(customerContact);
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public int getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(int customerContact) {
        this.customerContact = customerContact;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerAddress='" + customerAddress + '\'' +
                ", customerContact=" + customerContact +
                '}';
    }
}
