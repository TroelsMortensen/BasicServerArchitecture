package customerexample;

import java.util.UUID;

public class CustomerModelManager {

    private CustomerDAO customerDAO;

    public void updateCustomer(UUID customerId,
                               String customerFirstName, String customerLastName,
                               String streetAddress1, String streetAddress2,
                               String city, String stateOrProvince,
                               String postalCode, String country,
                               String homePhone, String mobilePhone,
                               String primaryEmailAddress, String secondaryEmailAddress) {
        Customer customer = customerDAO.find(customerId);
        customer.setCustomerFirstName(customerFirstName);
        customer.setCustomerLastName(customerFirstName);
        customer.setStreetAddress1(streetAddress1);
        // ... the rest
    }
}
