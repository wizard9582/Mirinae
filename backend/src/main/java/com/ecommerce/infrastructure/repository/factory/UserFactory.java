package com.ecommerce.infrastructure.repository.factory;

import com.ecommerce.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserFactory {

    public static User create(ResultSet rs) throws SQLException {
        if (rs == null) return null;
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

        return user;
    }
}