package lk.ijse.spring.controller;

import lk.ijse.spring.dto.CustomerDTO;
import lk.ijse.spring.service.CustomerService;
import lk.ijse.spring.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/pos/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil getAllCustomers() {
        return new ResponseUtil(200, "OK", customerService.getAllCustomers());
    }

    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil searchCustomer(@PathVariable String id) {
        return new ResponseUtil(200, "Search Done", customerService.searchCustomer(id));
    }

    @GetMapping(path = "lastID", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil getLastCustomerID() {
        return new ResponseUtil(200, "Last ID", customerService.getLastCustomerID());
    }

    @GetMapping(path = "{id}/{contact}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil checkIfDuplicateContact(@PathVariable String id, @PathVariable int contact) {
        return new ResponseUtil(200, customerService.isDuplicateContact(id,contact), null);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil saveCustomer(@ModelAttribute CustomerDTO dto) {
        return new ResponseUtil(201, "Customer Saved Successfully..!", customerService.saveCustomer(dto));
    }
}
