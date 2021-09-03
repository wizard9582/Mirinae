package com.ecommerce.application;

import com.ecommerce.domain.Purchase;
import com.ecommerce.domain.PurchaseInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IPurchaseService {
    List<Purchase> list();
    Purchase get(long id);
    Purchase getByPurchaseId(int pid);
    List<PurchaseInfo> getBySeller(int id);
    List<PurchaseInfo> getByBuyer(int id);

    @Transactional
    Purchase create(Purchase purchase);

    @Transactional
    Purchase updateState(int pid, String state);
}
