package com.ztx.spring.Demo1.dao;

import com.ztx.spring.Demo1.entity.User;

public interface IUserDao {
    void selectUserTable();
    void updataUserTable(User user);
}
