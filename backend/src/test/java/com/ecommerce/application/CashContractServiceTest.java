package com.ecommerce.application;

import com.ecommerce.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class CashContractServiceTest {
    private static final Logger log = LoggerFactory.getLogger(CashContractServiceTest.class);

    @Autowired
    private ICashContractService cashContractService;

    @Value("${eth.admin.address}")
    private String ADMIN_ADDRESS;

    @Test
    public void testGetBalance() {
        int balance = this.cashContractService.getBalance(ADMIN_ADDRESS);

        log.info(balance + "");
    }
}