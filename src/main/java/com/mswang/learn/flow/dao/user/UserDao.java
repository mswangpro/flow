package com.mswang.learn.flow.dao.user;

import java.util.List;

import com.mswang.learn.flow.dao.common.BaseDao;
import com.mswang.learn.flow.vo.User;

public interface UserDao extends BaseDao<User>{

	List<User> findSqlUser();

}
