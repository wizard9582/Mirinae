package com.ecommerce.domain;

import com.ecommerce.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class CryptoUtilTest{

    private static final Logger log = LoggerFactory.getLogger(CryptoUtilTest.class);

    @Value("${eth.admin.address}")
    private String ADMIN_ADDRESS;

    @Test
    public void testIntegration() {
        CryptoUtil cryptoUtil = CryptoUtil.of(ADMIN_ADDRESS);
        try {
            String rawPassword = "1234";
            String encryptedPassword = cryptoUtil.encryptBase64(rawPassword);
            log.debug(encryptedPassword);

            String decryptedPassword = cryptoUtil.decryptBase64(encryptedPassword);
            log.debug(decryptedPassword);

            assert decryptedPassword.equals(rawPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}