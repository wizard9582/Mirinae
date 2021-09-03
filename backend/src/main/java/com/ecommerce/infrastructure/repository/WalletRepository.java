package com.ecommerce.infrastructure.repository;

import com.ecommerce.domain.Wallet;
import com.ecommerce.domain.exception.RepositoryException;
import com.ecommerce.domain.repository.IWalletRepository;
import com.ecommerce.infrastructure.repository.factory.WalletFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class WalletRepository implements IWalletRepository
{
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert simpleJdbcInsert;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Wallet> list()
	{
        StringBuilder sbSql =  new StringBuilder("SELECT * FROM wallets");
		try {
			return this.jdbcTemplate.query(sbSql.toString(),
							   new Object[]{}, (rs, rowNum) -> WalletFactory.create(rs));
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public Wallet get(final long ownerId)
	{
		StringBuilder sbSql =  new StringBuilder("SELECT * FROM wallets WHERE owner_id=?");
		try {
			return this.jdbcTemplate.queryForObject(sbSql.toString(),
								new Object[] { ownerId }, (rs, rowNum) -> WalletFactory.create(rs) );
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public Wallet get(final String wAddress)
	{
		StringBuilder sbSql =  new StringBuilder("SELECT * FROM wallets WHERE address=?");
		try {
			return this.jdbcTemplate.queryForObject(sbSql.toString(),
								new Object[] {wAddress}, (rs, rowNum) -> WalletFactory.create(rs) );
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public long create(final Wallet wallet)
	{
		try{
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("owner_id", wallet.getOwnerId());
			paramMap.put("address", wallet.getAddress());
			paramMap.put("balance", wallet.getBalance());
			paramMap.put("receiving_count", 0);
			paramMap.put("cash", 0);

			this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
					.withTableName("wallets")
					.usingGeneratedKeyColumns("id");

			Number newId = simpleJdbcInsert.executeAndReturnKey(paramMap);
			return newId.longValue();

		}catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public int updateBalance(String wAddress, BigDecimal balance, int cash) {
		StringBuilder sbSql =  new StringBuilder("UPDATE wallets ");
		sbSql.append("SET balance=?, cash=? ");
		sbSql.append("where address=?");
		try {
			return this.jdbcTemplate.update(sbSql.toString(),
					new Object[] {balance, cash, wAddress});
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public int updateRequestNo(final String wAddress){
		StringBuilder sbSql =  new StringBuilder("UPDATE wallets SET receiving_count = receiving_count + 1 ");
		sbSql.append("WHERE address=?");
		try {
			return this.jdbcTemplate.update(sbSql.toString(),
			                                new Object[] {wAddress});
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}
}
