package com.ecommerce.application.impl;

import com.ecommerce.application.IPurchaseService;
import com.ecommerce.domain.Item;
import com.ecommerce.domain.Purchase;
import com.ecommerce.domain.PurchaseInfo;
import com.ecommerce.domain.exception.ApplicationException;
import com.ecommerce.domain.repository.IItemRepository;
import com.ecommerce.domain.repository.IPurchaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseService implements IPurchaseService {

    public static final Logger logger = LoggerFactory.getLogger(ItemService.class);

    private IPurchaseRepository purchaseRepository;
    private IItemRepository itemRepository;

    @Autowired
    public PurchaseService(IPurchaseRepository purchaseRepository,
                           IItemRepository itemRepository) {
        this.purchaseRepository = purchaseRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public List<Purchase> list() {
        return this.purchaseRepository.list();
    }

    @Override
    public Purchase get(long id) {
        return this.purchaseRepository.get(id);
    }

    @Override
    public Purchase getByPurchaseId(int pid) {
        return this.purchaseRepository.getByPurchaseId(pid);
    }

    /**
     * TODO Sub PJT Ⅲ 과제 3
     * 판매자 관련 Purchase 조회
     */
    @Override
    public List<PurchaseInfo> getBySeller(int id) {
        return null;
    }

    /**
     * TODO Sub PJT Ⅲ 과제 3
     * 구매자 관련 Purchase 조회
     */
    @Override
    public List<PurchaseInfo> getByBuyer(int id) {
       return null;
    }

    /**
     * TODO Sub PJT Ⅲ 과제 3
     * Purchase 정보 등록
     * @param purchase
     * @return
     */
    @Override
    public Purchase create(final Purchase purchase) {
        return null;
    }

    /**
     * TODO Sub PJT Ⅲ 과제 3
     * Purchase 상태 업데이트
     * @return
     */
    @Override
    public Purchase updateState(int pid, String state) {
        return null;
    }

}
