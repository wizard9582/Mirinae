package com.ecommerce.infrastructure.repository.factory;

import com.ecommerce.domain.Wallet;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WalletFactory
{
	public static Wallet create(ResultSet rs) throws SQLException
	{
		if (rs == null) return null;
		Wallet wallet = new Wallet();
		wallet.setId(rs.getLong("id"));
		wallet.setOwnerId(rs.getLong("owner_id"));
		wallet.setAddress(rs.getString("address"));
		wallet.setBalance(rs.getBigDecimal("balance"));
		wallet.setReceivingCount(rs.getInt("receiving_count"));
		wallet.setCash(rs.getInt("cash"));

		return wallet;
	}
}
