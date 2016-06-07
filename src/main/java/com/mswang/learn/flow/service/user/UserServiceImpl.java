package com.mswang.learn.flow.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mswang.learn.flow.dao.user.UserDao;
import com.mswang.learn.flow.vo.User;
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDao userDao;
	@Override
	public List<User> findUser() {
		return userDao.findSqlUser();
	}

}
