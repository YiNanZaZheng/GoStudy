package com.ztx.spring.Demo1.service;

import com.ztx.spring.Demo1.dao.IUserDao;
import com.ztx.spring.Demo1.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserDao userDao;
    @Autowired(required=true)
    private User user;

    public UserService() {
    }

    public UserService(IUserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void loginUser() {
        userDao.updataUserTable(user);
    }
}