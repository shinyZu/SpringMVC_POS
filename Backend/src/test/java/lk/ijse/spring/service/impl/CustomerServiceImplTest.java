package lk.ijse.spring.service.impl;

import lk.ijse.spring.config.WebAppConfig;
import lk.ijse.spring.entity.Customer;
import lk.ijse.spring.repo.CustomerRepo;
import lk.ijse.spring.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebAppConfiguration
@ContextConfiguration(classes = {WebAppConfig.class})
@ExtendWith({SpringExtension.class})
class CustomerServiceImplTest {

    @Autowired
    CustomerService customerService;

    /*@Autowired
    CustomerRepo customerRepo;*/

    @Test
    void getLastCustomerID() {
        /*List<Customer> list = customerRepo.findAll(Sort.by(Sort.Direction.DESC, "customerId"));
        System.out.println(list);
        System.out.println(list.get(0).getCustomerId());*/

        String lastCustomerID = customerService.getLastCustomerID();
        System.out.println(lastCustomerID);

    }

    @Test
    void isDuplicateContact() {
        String status = customerService.isDuplicateContact("C00-007", 716455457);
        System.out.println("status : "+status);
    }
}