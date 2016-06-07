package com.mswang.learn.flow.dao.user;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mswang.learn.flow.dao.common.BaseDaoImpl;
import com.mswang.learn.flow.vo.User;
@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao{

	@Override
	public List<User> findSqlUser() {
		String sql = "select name id,password name,email age from a_employee";
		return super.queryBySQL(sql, new HashMap<String, Object>(), User.class);
	}
	
}
