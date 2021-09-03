package com.ecommerce.infrastructure.repository;

import com.ecommerce.domain.Transaction;
import com.ecommerce.domain.exception.RepositoryException;
import com.ecommerce.domain.repository.ITransactionRepository;
import com.ecommerce.infrastructure.repository.factory.TransactionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TransactionRepository implements ITransactionRepository {
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Transaction> list() {
        StringBuilder sbSql =  new StringBuilder("SELECT * FROM transactions ");
        try {
            return this.jdbcTemplate.query(sbSql.toString(),
                    new Object[]{}, (rs, rowNum) -> TransactionFactory.create(rs));
        } catch (Exception e) {
            throw new RepositoryException(e, e.getMessage());
        }
    }

    @Override
    public Transaction get(String hash) {
        StringBuilder sbSql =  new StringBuilder("SELECT * FROM transactions WHERE hash=?");
        try {
            return this.jdbcTemplate.queryForObject(sbSql.toString(),
                    new Object[] { hash }, (rs, rowNum) -> TransactionFactory.create(rs) );
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            throw new RepositoryException(e, e.getMessage());
        }
    }

    @Override
    public List<Transaction> getByAddress(final String address)
    {
        StringBuilder sbSql =  new StringBuilder("SELECT * FROM transactions WHERE from_hash=? OR to_hash=?");
        try {
            return this.jdbcTemplate.query(sbSql.toString(),
                                           new Object[] { address, address }, (rs, rowNum) -> TransactionFactory.create(rs) );
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            throw new RepositoryException(e, e.getMessage());
        }
    }

    @Override
    public long add(Transaction tx) {

        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("hash", tx.getHash());
            paramMap.put("nonce", tx.getNonce());
            paramMap.put("block_hash", tx.getBlockHash());
            paramMap.put("block_number", tx.getBlockNumber());
            paramMap.put("transaction_index", tx.getTransactionIndex());
            paramMap.put("from_hash", tx.getFrom());
            paramMap.put("to_hash", tx.getTo());
            paramMap.put("value", tx.getValue());
            paramMap.put("gas_price", tx.getGasPrice());
            paramMap.put("gas", tx.getGas());
            paramMap.put("input", tx.getInput());
            paramMap.put("creates", tx.getCreates());
            paramMap.put("public_key", tx.getPublicKey());
            paramMap.put("raw", tx.getRaw());
            paramMap.put("r", tx.getR());
            paramMap.put("s", tx.getS());
            paramMap.put("v", tx.getV());
            paramMap.put("stored_at", LocalDateTime.now());

            this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                    .withTableName("transactions")
                    .usingGeneratedKeyColumns("id");

            Number newId = simpleJdbcInsert.executeAndReturnKey(paramMap);
            return newId.longValue();

        } catch (Exception e) {
            throw new RepositoryException(e, e.getMessage());
        }
    }
}
