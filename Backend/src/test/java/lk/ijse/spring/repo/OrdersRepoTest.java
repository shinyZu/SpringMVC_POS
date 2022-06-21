package lk.ijse.spring.repo;

import lk.ijse.spring.config.WebAppConfig;
import lk.ijse.spring.dto.OrdersDTO;
import lk.ijse.spring.entity.Orders;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@WebAppConfiguration
@ContextConfiguration(classes = {WebAppConfig.class})
@ExtendWith({SpringExtension.class})
class OrdersRepoTest {

    @Autowired
    OrdersRepo ordersRepo;

    @Test
    void getOrderByOrderId() {
        Orders order = ordersRepo.getOrderByOrderId("OID-002");
        System.out.println(order);
    }
}