package lk.ijse.spring.controller;

import lk.ijse.spring.service.CustomerService;
import lk.ijse.spring.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/pos/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil getAllCustomers(){
        return new ResponseUtil(200,"OK",customerService.getAllCustomers());
    }

    @GetMapping(path = "{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil searchCustomer(@PathVariable String id){
        return new ResponseUtil(200,"Search Done",customerService.searchCustomer(id));
    }
}
