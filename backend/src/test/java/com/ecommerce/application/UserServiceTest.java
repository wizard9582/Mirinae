package com.ecommerce.application;

import com.ecommerce.Application;
import com.ecommerce.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserServiceTest {
    @Autowired
    private IUserService userService;


    @Test
    public void testAdd() {
        User user = new User();
        user.setName("코인충");
        user.setEmail("코인충@yahoo.com");
        user.setPassword("qwer");

        User userAdded = this.userService.add(user);
        assert userAdded.getId() > 0;
        assert userAdded.getName().equals("코인충");
        assert userAdded.getEmail().equals("코인충@yahoo.com");
    }


    @Test
    public void testList() {
        List<User> userList = this.userService.list();

        assert userList.size() > 0;
    }

    @Test
    public void testGet() {
        long id = 4;
        User user = this.userService.get(id);

        assert user != null;
        assert user.getName().equals("비트맨");
    }


    @Transactional
    @Test
    public void testUpdate() {
        User user = new User();
        user.setId(4);
        user.setName("비트우먼");
        user.setEmail("bitwoman@yahoo.com");

        this.userService.update(user);

        User userUpdated = this.userService.get(4);
        assert userUpdated.getName().equals("비트우먼");
    }

    @Transactional
    @Test
    public void testDeleted() {
        long id = 4;
        this.userService.delete(id);

        User userDeleted = this.userService.get(id);
        assert userDeleted == null;
    }
}
