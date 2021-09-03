package com.ecommerce.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PurchaseInfo extends Purchase{
    private String itemName;
    private long itemId;
    private String image;

    public PurchaseInfo(Purchase p) {
        this.setId(p.getId());
        this.setPurchaseId(p.getPurchaseId());
        this.setState(p.getState());;
        this.setSellerId(p.getSellerId());
        this.setBuyerId(p.getBuyerId());;
        this.setItemId(itemId = p.getItemId());
        this.setCreatedAt(p.getCreatedAt());
        this.setContractAddress(p.getContractAddress());
    }
}
