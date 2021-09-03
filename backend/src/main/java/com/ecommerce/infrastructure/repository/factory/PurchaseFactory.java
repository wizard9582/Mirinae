package com.ecommerce.infrastructure.repository.factory;

import com.ecommerce.domain.Purchase;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PurchaseFactory {

    public static Purchase create(ResultSet rs) throws SQLException
    {
        if (rs == null) return null;
        Purchase purchase = new Purchase();
        purchase.setId(rs.getLong("id"));
        purchase.setPurchaseId(rs.getLong("purchase_id"));
        purchase.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        purchase.setSellerId(rs.getLong("seller_id"));
        purchase.setBuyerId(rs.getLong("buyer_id"));
        purchase.setItemId(rs.getLong("item_id"));
        purchase.setState(rs.getString("state"));
        purchase.setContractAddress(rs.getString("contract_address"));

        return purchase;
    }
}
