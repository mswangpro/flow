package com.mswang.learn.flow.dao.common;

import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;

import com.mswang.learn.utils.PageVo;
import com.mswang.learn.utils.SQLPageParamVo;

public abstract interface BaseDao<T> {
	public abstract T get(String id);

	public abstract void save(T entity);

	public abstract void update(T entity);

	public abstract void saveOrUpdate(T entity);

	public abstract void delete(T entity);

	public abstract void deleteById(String id);

	public abstract void deleteByIds(List<String> ids);

	public abstract List<T> findBy(String propertyName, Object paramObject);

	public abstract T findUniqueBy(String propertyName, Object paramObject);
	
	public abstract SQLQuery buildSQLQuery(String sql, Map<String, Object> paramMap);
	
	public abstract List<T> queryBySQL(String sql, Map<String, Object> paramMap, Class<T> paramClass);
	public abstract PageVo<T> queryPageBySQL(Class<T> paramClass, SQLPageParamVo paramSQLPageParamVo);
}
