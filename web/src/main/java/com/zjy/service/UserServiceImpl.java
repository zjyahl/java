package com.zjy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjy.dao.UserDao;
import com.zjy.entity.User;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;

	public User selectUserById(int id) {
		return userDao.selectUserById(id);

	}
}
