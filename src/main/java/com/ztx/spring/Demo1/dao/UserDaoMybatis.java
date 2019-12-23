package com.ztx.spring.Demo1.dao;

import com.ztx.spring.Demo1.entity.User;
import org.springframework.stereotype.Repository;

public class UserDaoMybatis implements IUserDao{
    @Override
    public void selectUserTable() {
        System.out.println("mybatis查询表");
    }

    @Override
    public void updataUserTable(User user) {
        System.out.println("mybatis更新表");
    }
}
