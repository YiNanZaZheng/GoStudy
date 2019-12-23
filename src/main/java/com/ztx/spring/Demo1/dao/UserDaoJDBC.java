package com.ztx.spring.Demo1.dao;

import com.ztx.spring.Demo1.entity.User;
import org.springframework.stereotype.Repository;


public class UserDaoJDBC implements IUserDao {
    @Override
    public void selectUserTable() {
        System.out.println("jdbc查询表");
    }

    @Override
    public void updataUserTable(User user) {
        System.out.println("jdbc更新表");
    }
}
