package com.ecommerce.application;

import com.ecommerce.domain.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IUserService {
    List<User> list();
    User get(long id);
    User get(String email);

    @Transactional
    User add(User user);

    @Transactional
    User update(User user);

    @Transactional
    void delete(long id);
}
