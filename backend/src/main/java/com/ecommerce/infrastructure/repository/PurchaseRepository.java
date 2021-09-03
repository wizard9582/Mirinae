package com.ecommerce.infrastructure.repository;

import com.ecommerce.domain.Purchase;
import com.ecommerce.domain.exception.RepositoryException;
import com.ecommerce.domain.repository.IPurchaseRepository;
import com.ecommerce.infrastructure.repository.factory.PurchaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Sub PJT Ⅲ 과제 3
 */
@Repository
public class PurchaseRepository implements IPurchaseRepository {
    private static final Logger log = LoggerFactory.getLogger(PurchaseRepository.class);

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public List<Purchase> list() {
        StringBuilder sbSql =  new StringBuilder("SELECT * FROM purchases");
        try {
            return this.jdbcTemplate.query(sbSql.toString(),
                    new Object[]{}, (rs, rowNum) -> PurchaseFactory.create(rs));
        } catch (Exception e) {
            throw new RepositoryException(e, e.getMessage());
        }
    }

    @Override
    public Purchase get(long id) {
        StringBuilder sbSql =  new StringBuilder("SELECT * FROM purchases WHERE id=?");
        try {
            return this.jdbcTemplate.queryForObject(sbSql.toString(),
                    new Object[] { id }, (rs, rowNum) -> PurchaseFactory.create(rs) );
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            throw new RepositoryException(e, e.getMessage());
        }
    }

    @Override
    public Purchase getByPurchaseId(long pid) {
        StringBuilder sbSql =  new StringBuilder("SELECT * FROM purchases WHERE purchase_id=?");
        try {
            return this.jdbcTemplate.queryForObject(sbSql.toString(),
                    new Object[] { pid }, (rs, rowNum) -> PurchaseFactory.create(rs) );
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            throw new RepositoryException(e, e.getMessage());
        }
    }

    @Override
    public List<Purchase> getBySeller(long id) {
        StringBuilder sbSql =  new StringBuilder("SELECT * FROM purchases WHERE seller_id=? ");
        try {
            return this.jdbcTemplate.query(sbSql.toString(),
                    new Object[]{id}, (rs, rowNum) -> PurchaseFactory.create(rs));
        } catch (Exception e) {
            throw new RepositoryException(e, e.getMessage());
        }
    }

    @Override
    public List<Purchase> getByBuyer(long id) {
        StringBuilder sbSql =  new StringBuilder("SELECT * FROM purchases WHERE buyer_id=? ");
        try {
            return this.jdbcTemplate.query(sbSql.toString(),
                    new Object[]{id}, (rs, rowNum) -> PurchaseFactory.create(rs));
        } catch (Exception e) {
            throw new RepositoryException(e, e.getMessage());
        }
    }

    @Override
    public long create(Purchase purchase) {
        try {
            log.debug(purchase.toString());
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("purchase_id", purchase.getPurchaseId());
            paramMap.put("created_at", purchase.getCreatedAt());
            paramMap.put("seller_id", purchase.getSellerId());
            paramMap.put("buyer_id", purchase.getBuyerId());
            paramMap.put("item_id", purchase.getItemId());
            paramMap.put("state", purchase.getState());
            paramMap.put("contract_address", purchase.getContractAddress());

            this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                    .withTableName("purchases")
                    .usingGeneratedKeyColumns("id");

            Number newId = simpleJdbcInsert.executeAndReturnKey(paramMap);
            log.debug("INSERTED: " + newId.longValue());
            return newId.longValue();

        } catch (Exception e) {
            throw new RepositoryException(e, e.getMessage());
        }
    }

    @Override
    public long update(final Purchase purchase) {
        StringBuilder sbSql =  new StringBuilder("UPDATE purchases ");
        sbSql.append("SET state=? ");
        sbSql.append("where id=?");
        try {
            return this.jdbcTemplate.update(sbSql.toString(),
                    new Object[] {
                            purchase.getState(),
                            purchase.getId()
                    });
        } catch (Exception e) {
            throw new RepositoryException(e, e.getMessage());
        }
    }
}
