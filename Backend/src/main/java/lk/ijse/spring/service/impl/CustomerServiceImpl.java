package lk.ijse.spring.service.impl;

import lk.ijse.spring.dto.CustomerDTO;
import lk.ijse.spring.entity.Customer;
import lk.ijse.spring.repo.CustomerRepo;
import lk.ijse.spring.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepo repo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return mapper.map(repo.findAll(), new TypeToken<List<CustomerDTO>>() {
        }.getType());
    }

    @Override
    public CustomerDTO searchCustomer(String id) {
        if (repo.existsById(id)) {
            return mapper.map(repo.findById(id), CustomerDTO.class);
        } else {
            throw new RuntimeException("No Customer with ID " + id);
        }
    }

    @Override
    public String getLastCustomerID() {
        List<Customer> list = repo.findAll(Sort.by(Sort.Direction.DESC, "customerId"));
        return list.get(0).getCustomerId();
    }

    @Override
    public String isDuplicateContact(String id, int contact) {
        System.out.println("contact : "+contact);
        List<Customer> all = repo.findAll();
        for (Customer customer : all) {
            if (customer.getCustomerContact() == contact) {
                if (customer.getCustomerId().equals(id)) {
                    return "Match";
                } else {
                    return "Duplicate";
                }
            }
        }
        return "Unique";
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO dto) {
        if (!repo.existsById(dto.getCustomerId())) {
            return mapper.map(repo.save(mapper.map(dto, Customer.class)), CustomerDTO.class);
        } else {
            throw new RuntimeException("Customer Already Exists...");
        }
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO dto) {
        return null;
    }

    @Override
    public void deleteCustomer(String id) {

    }
}
