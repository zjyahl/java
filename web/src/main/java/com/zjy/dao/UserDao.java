package com.zjy.dao;

import com.zjy.entity.User;

public interface UserDao {

	/**
     * @param userId
     * @return User
     */
    public User selectUserById(int id);  
}
