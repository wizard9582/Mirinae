package com.ecommerce.infrastructure.repository;

import com.ecommerce.domain.User;
import com.ecommerce.domain.exception.RepositoryException;
import com.ecommerce.domain.repository.IUserRepository;
import com.ecommerce.infrastructure.repository.factory.UserFactory;
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
public class UserRepository implements IUserRepository {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<User> list() {
        StringBuilder sbSql =  new StringBuilder("SELECT * FROM users ");
        try {
            return this.jdbcTemplate.query(sbSql.toString(),
                    new Object[]{}, (rs, rowNum) -> UserFactory.create(rs));
        } catch (Exception e) {
            throw new RepositoryException(e, e.getMessage());
        }
    }

    @Override
    public User get(long id) {
        StringBuilder sbSql =  new StringBuilder("SELECT * FROM users WHERE id=?");
        try {
            return this.jdbcTemplate.queryForObject(sbSql.toString(),
                    new Object[] { id }, (rs, rowNum) -> UserFactory.create(rs) );
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            throw new RepositoryException(e, e.getMessage());
        }
    }

    @Override
    public User get(final String email)
    {
        StringBuilder sbSql = new StringBuilder("SELECT * FROM users WHERE email=?");
        try{
            return this.jdbcTemplate.queryForObject(sbSql.toString(),
                                                    new Object[] {email}, (rs, rowNum) -> UserFactory.create(rs) );
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            throw new RepositoryException(e, e.getMessage());
        }
    }

    @Override
    public long create(User user) {

        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("name", user.getName());
            paramMap.put("email", user.getEmail());
            paramMap.put("created_at", LocalDateTime.now());
            paramMap.put("password", user.getPassword());

            this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                    .withTableName("users")
                    .usingGeneratedKeyColumns("id");

            Number newId = simpleJdbcInsert.executeAndReturnKey(paramMap);
            return newId.longValue();

        } catch (Exception e) {
            throw new RepositoryException(e, e.getMessage());
        }
    }

    @Override
    public int update(User user) {
        StringBuilder sbSql =  new StringBuilder("UPDATE users ");
        sbSql.append("SET name=?, email=?, password=? ");
        sbSql.append("WHERE id=?");
        try {
            return this.jdbcTemplate.update(sbSql.toString(),
                    new Object[] { user.getName(), user.getEmail(), user.getPassword(), user.getId() });
        } catch (Exception e) {
            throw new RepositoryException(e, e.getMessage());
        }
    }

    @Override
    public int delete(long id) {
        StringBuilder sbSql =  new StringBuilder("DELETE FROM users ");
        sbSql.append("WHERE id=?");
        try {
            return this.jdbcTemplate.update(sbSql.toString(),
                    new Object[] { id });
        } catch (Exception e) {
            throw new RepositoryException(e, e.getMessage());
        }
    }
}
