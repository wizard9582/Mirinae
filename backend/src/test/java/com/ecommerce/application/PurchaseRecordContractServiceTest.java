package com.ecommerce.application;

import com.ecommerce.Application;
import com.ecommerce.application.impl.PurchaseRecordContractService;
import com.ecommerce.domain.Record;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class PurchaseRecordContractServiceTest {
    private static final Logger log = LoggerFactory.getLogger(PurchaseRecordContractService.class);

    @Autowired
    private IPurchaseRecordContractService purchaseRecordContractService;

    @Test
    public void testGetHistory() {
        String EXAMPLE_CA= "0x247F85ec1dE70bf7eeD8BEE9f1Ff40AE981A582A"; // escrow contract address
        List<Record> records = this.purchaseRecordContractService.getHistory(EXAMPLE_CA);

        log.info(records.size() + "");
        assert(records.size() > 0);
        Record r = records.get(0);
        log.info(r.getState());
        log.info(r.getWhen() + "");
        log.info(r.getBy());
    }

}
