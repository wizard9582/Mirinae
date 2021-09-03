package com.ecommerce.infrastructure.repository.factory;

import com.ecommerce.domain.Transaction;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionFactory {

    public static Transaction create(ResultSet rs) throws SQLException {
        if (rs == null) return null;
        Transaction tx = new Transaction();

        tx.setId(rs.getLong("id"));
        tx.setHash(rs.getString("hash"));
        tx.setNonce(rs.getString("nonce"));
        tx.setBlockHash(rs.getString("block_hash"));
        tx.setBlockNumber(rs.getString("block_number"));
        tx.setTransactionIndex(rs.getString("transaction_index"));
        tx.setFrom(rs.getString("from_hash"));
        tx.setTo(rs.getString("to_hash"));
        tx.setValue(rs.getString("value"));
        tx.setGasPrice(rs.getString("gas_price"));
        tx.setGas(rs.getString("gas"));
        tx.setInput(rs.getString("input"));
        tx.setCreates(rs.getString("creates"));
        tx.setPublicKey(rs.getString("public_key"));
        tx.setRaw(rs.getString("raw"));
        tx.setR(rs.getString("r"));
        tx.setS(rs.getString("s"));
        tx.setV(rs.getInt("v"));
        tx.setStoredAt(rs.getTimestamp("stored_at").toLocalDateTime());

        return tx;
    }
}
