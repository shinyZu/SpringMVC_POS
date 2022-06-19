package lk.ijse.spring.service.impl;

import lk.ijse.spring.config.WebAppConfig;
import lk.ijse.spring.service.ItemService;
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
class ItemServiceImplTest {

    @Autowired
    ItemService itemService;

    @Test
    void getLastItemCode() {
        assertEquals("C00-000",itemService.getLastItemCode());
//        String lastItemCode = itemService.getLastItemCode();
//        System.out.println(lastItemCode);
    }
}