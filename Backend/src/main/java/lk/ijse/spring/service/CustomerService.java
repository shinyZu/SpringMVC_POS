package lk.ijse.spring.service;

import lk.ijse.spring.dto.CustomerDTO;
import lk.ijse.spring.entity.Customer;

import java.util.List;

public interface CustomerService {
    List<CustomerDTO> getAllCustomers();
    CustomerDTO searchCustomer(String id);
    String getLastCustomerID();
    String isDuplicateContact(String id, int contact);
    CustomerDTO saveCustomer(CustomerDTO dto);
    CustomerDTO updateCustomer(CustomerDTO dto);
    void deleteCustomer(String id);
}
