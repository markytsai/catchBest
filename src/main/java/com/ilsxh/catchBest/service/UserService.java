package com.ilsxh.catchBest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ilsxh.catchBest.dao.UserDao;
import com.ilsxh.catchBest.domain.User;

@Service
public class UserService {

	@Autowired
	UserDao userDao;

	public User getById(int id) {
		return userDao.getById(id);
	}

	@Transactional
	public boolean tx() {
		User u1 = new User();
		u1.setId(3);
		u1.setName("2222");
		userDao.insert(u1);

		User u2 = new User();
		u2.setId(4);
		u2.setName("11111");
		userDao.insert(u2);

		return true;
	}

}