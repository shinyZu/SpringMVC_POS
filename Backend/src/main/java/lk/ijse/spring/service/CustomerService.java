package lk.ijse.spring.service;

import lk.ijse.spring.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {
    List<CustomerDTO> getAllCustomers();

    CustomerDTO searchCustomer(String id);

    String getLastCustomerID();

    String getCustomerCount();

    String isDuplicateContact(String id, int contact);

    CustomerDTO saveCustomer(CustomerDTO dto);

    CustomerDTO updateCustomer(CustomerDTO dto);

    void deleteCustomer(String id);
}
