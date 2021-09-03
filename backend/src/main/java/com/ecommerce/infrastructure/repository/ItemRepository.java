package com.ecommerce.infrastructure.repository;

import com.ecommerce.domain.Item;
import com.ecommerce.domain.exception.RepositoryException;
import com.ecommerce.domain.repository.IItemRepository;
import com.ecommerce.infrastructure.repository.factory.ItemFactory;
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

@Repository
public class ItemRepository implements IItemRepository
{
	private static final Logger log = LoggerFactory.getLogger(ItemRepository.class);

	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert simpleJdbcInsert;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Item> list() {
		StringBuilder sbSql =  new StringBuilder("SELECT * FROM items WHERE available=?"); // where available
		try {
			return this.jdbcTemplate.query(sbSql.toString(),
							   new Object[]{true}, (rs, rowNum) -> ItemFactory.create(rs));
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public List<Item> getByUser(final long userId) {
		StringBuilder sbSql =  new StringBuilder("SELECT * FROM items WHERE seller=? ");
		try {
			return this.jdbcTemplate.query(sbSql.toString(),
					new Object[]{ userId }, (rs, rowNum) -> ItemFactory.create(rs));
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public Item get(final long id) {
		StringBuilder sbSql =  new StringBuilder("SELECT * FROM items WHERE id=?");
		try {
			return this.jdbcTemplate.queryForObject(sbSql.toString(),
								new Object[] { id }, (rs, rowNum) -> ItemFactory.create(rs) );
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public long create(final Item item) {
		try {
			log.debug(item.toString());
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("name", item.getName());
			paramMap.put("category", item.getCategory());
			paramMap.put("explanation", item.getExplanation());
			paramMap.put("available", item.getAvailable());
			paramMap.put("seller", item.getSeller());
			paramMap.put("registered_at", item.getRegisteredAt());
			paramMap.put("image", item.getImage());

			this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
					.withTableName("items")
					.usingGeneratedKeyColumns("id");

			Number newId = simpleJdbcInsert.executeAndReturnKey(paramMap);
			log.debug("INSERTED: " + newId.longValue());
			return newId.longValue();

		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public int update(final Item item) {
		StringBuilder sbSql =  new StringBuilder("UPDATE items ");
		sbSql.append("SET name=?, category=?, explanation=?, available=? ");
		sbSql.append("where id=?");
		try {
			return this.jdbcTemplate.update(sbSql.toString(),
								new Object[] {
										item.getName(),
										item.getCategory(),
										item.getExplanation(),
										item.getAvailable(),
										item.getId()
								});
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public int delete(final long id) {
		StringBuilder sbSql =  new StringBuilder("UPDATE items ");
		sbSql.append("SET available=? ");
		sbSql.append("where id=?");

		try {
			return this.jdbcTemplate.update(sbSql.toString(),
								new Object[] { false, id });
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}

	}

}
