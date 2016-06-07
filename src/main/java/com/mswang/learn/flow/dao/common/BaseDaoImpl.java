package com.mswang.learn.flow.dao.common;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.mswang.learn.utils.PageVo;
import com.mswang.learn.utils.SQLPageParamVo;
public class BaseDaoImpl<T> implements BaseDao<T> {

	private static final Logger LOGGER = LoggerFactory.getLogger(BaseDaoImpl.class);
	protected Class<T> entityClass;
	private SessionFactory sessionFactory;

	public BaseDaoImpl() {
		// 使用反射技术得到T的真实类型
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass(); // 获取当前new的对象的 泛型的父类 类型
		this.entityClass = (Class<T>) pt.getActualTypeArguments()[0]; // 获取第一个类型参数的真实类型
		LOGGER.info("clazz ---> " + entityClass);
	}

	@Resource(name = "sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getCurrentSession() {
		return this.sessionFactory.getCurrentSession();
	}

	@Override
	public T get(String id) {
		return (T) getCurrentSession().get(this.entityClass, id);
	}

	@Override
	public void save(T entity) {
		getCurrentSession().save(entityClass);
	}

	@Override
	public void update(T entity) {
		getCurrentSession().update(entityClass);

	}

	@Override
	public void saveOrUpdate(T entity) {
		getCurrentSession().saveOrUpdate(entityClass);
	}

	@Override
	public void delete(T entity) {
		getCurrentSession().delete(entity);
	}

	@Override
	public void deleteById(String id) {
		T pojo = get(id);
		if (null == pojo) {
			LOGGER.warn("cant delete object,cant find by id:" + id);
			return;
		}
		delete(pojo);
	}

	@Override
	public void deleteByIds(List<String> ids) {
		for (String id : ids) {
			deleteById(id);
		}
	}

	@Override
	public List<T> findBy(String propertyName, Object paramObject) {
		Criterion eqCriterion = Restrictions.eq(propertyName, paramObject);
		return createCriteria(Lists.newArrayList(new Criterion[] { eqCriterion })).list();
	}

	@Override
	public T findUniqueBy(String propertyName, Object paramObject) {
		Criterion eqCriterion = Restrictions.eq(propertyName, paramObject);
		return (T) createCriteria(Lists.newArrayList(new Criterion[] { eqCriterion })).uniqueResult();
	}

	protected Criteria createCriteria(List<Criterion> criterions) {
		Criteria criteria = getCurrentSession()
				.createCriteria(this.entityClass);
		if (null != criterions) {
			for (Criterion c : criterions) {
				criteria.add(c);
			}
		}
		return criteria;
	}

	@Override
	public SQLQuery buildSQLQuery(String sql, Map<String, Object> paramMap) {
		SQLQuery sqlQuery = getCurrentSession().createSQLQuery(sql);
		if (null == paramMap) {
			return sqlQuery;
		}
		for (Map.Entry entry : paramMap.entrySet()) {
			if ((null == entry.getValue())
					|| (StringUtils.isBlank(entry.getValue().toString()))) {
				continue;
			}
			if ((entry.getValue() instanceof Collection))
				sqlQuery.setParameterList((String) entry.getKey(),(Collection) entry.getValue());
			else if ((entry.getValue() instanceof Object[]))
				sqlQuery.setParameterList((String) entry.getKey(),(Object[]) (Object[]) entry.getValue());
			else {
				sqlQuery.setParameter((String) entry.getKey(), entry.getValue());
			}
		}
		return sqlQuery;
	}

	@Override
	public List<T> queryBySQL(String sql, Map<String, Object> paramMap,
			Class<T> paramClass) {
		SQLQuery sqlQuery = getCurrentSession().createSQLQuery(sql);
		if (null != paramMap) {
			for (Map.Entry entry : paramMap.entrySet()) {
				if ((null == entry.getValue())|| (StringUtils.isBlank(entry.getValue().toString()))) {
					continue;
				}
				if ((entry.getValue() instanceof Collection))
					sqlQuery.setParameterList((String) entry.getKey(),(Collection) entry.getValue());
				else if ((entry.getValue() instanceof Object[]))
					sqlQuery.setParameterList((String) entry.getKey(),(Object[]) (Object[]) entry.getValue());
				else {
					sqlQuery.setParameter((String) entry.getKey(),entry.getValue());
				}
			}
		}
		addScalarByClassNotContain(sqlQuery, paramClass, new String[0]);
		return sqlQuery.list();
	}

	protected void addScalarByClassNotContain(SQLQuery query, Class<?> clazz,String[] fieldNames) {
		addScalarByClass(query, clazz, false, fieldNames);
		query.setResultTransformer(Transformers.aliasToBean(clazz));
	}

	protected void addScalarByClassContain(SQLQuery query, Class<?> clazz,String... fieldNames) {
		addScalarByClass(query, clazz, true, fieldNames);
		query.setResultTransformer(Transformers.aliasToBean(clazz));
	}

	private void addScalarByClass(SQLQuery query, Class<?> clazz,boolean isContain, String[] fieldNames) {
		List fieldNameList = Lists.newArrayList(fieldNames);
		Class superClass = clazz.getSuperclass();
		List<Field> fields = Lists.newArrayList();
		fields.addAll(Arrays.asList(superClass.getDeclaredFields()));
		fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
		for (Field field : fields) {
			Class c = field.getType();
			String fieldName = field.getName();
			if (fieldName.equals("serialVersionUID")) {
				continue;
			}
			if (((isContain) && (fieldNameList.contains(fieldName)))|| ((!isContain) && (!fieldNameList.contains(fieldName)))) {
				addScalar(query, fieldName, c);
			}
		}
	}

	private void addScalar(SQLQuery query, String fieldName, Class<?> c) {
		if (c == Integer.class)
			query.addScalar(fieldName, StandardBasicTypes.INTEGER);
		else if (c == Integer.TYPE)
			query.addScalar(fieldName, StandardBasicTypes.INTEGER);
		else if (c == Long.class)
			query.addScalar(fieldName, StandardBasicTypes.LONG);
		else if (c == Long.TYPE)
			query.addScalar(fieldName, StandardBasicTypes.LONG);
		else if (c == Boolean.class)
			query.addScalar(fieldName, StandardBasicTypes.BOOLEAN);
		else if (c == Boolean.TYPE)
			query.addScalar(fieldName, StandardBasicTypes.BOOLEAN);
		else if (c == Date.class)
			query.addScalar(fieldName, StandardBasicTypes.TIMESTAMP);
		else if (c == Timestamp.class)
			query.addScalar(fieldName, StandardBasicTypes.TIMESTAMP);
		else if (c == Byte.class)
			query.addScalar(fieldName, StandardBasicTypes.BYTE);
		else if (c == Byte.TYPE)
			query.addScalar(fieldName, StandardBasicTypes.BYTE);
		else if (c == Short.class)
			query.addScalar(fieldName, StandardBasicTypes.SHORT);
		else if (c == Short.TYPE)
			query.addScalar(fieldName, StandardBasicTypes.SHORT);
		else if (c == Float.class)
			query.addScalar(fieldName, StandardBasicTypes.FLOAT);
		else if (c == Float.TYPE)
			query.addScalar(fieldName, StandardBasicTypes.FLOAT);
		else if (c == Double.class)
			query.addScalar(fieldName, StandardBasicTypes.DOUBLE);
		else if (c == Double.TYPE)
			query.addScalar(fieldName, StandardBasicTypes.DOUBLE);
		else if (c == BigDecimal.class)
			query.addScalar(fieldName, StandardBasicTypes.BIG_DECIMAL);
		else
			query.addScalar(fieldName, StandardBasicTypes.STRING);
	}

	@Override
	public PageVo<T> queryPageBySQL(Class<T> paramClass,SQLPageParamVo paramVo) {
		Integer Total = paramVo.getTotal();
	    if ((null == Total) || (0 >= Total.intValue())) {
	      SQLQuery totalSizeSQLQuery = getCurrentSession().createSQLQuery(paramVo.getTotalSQL());
	      if (null != paramVo.getParam()) {
	        totalSizeSQLQuery.setProperties(paramVo.getParam());
	      }
	      Total = Integer.valueOf(((Number)totalSizeSQLQuery.uniqueResult()).intValue());
	    }

	    PageVo pageVo = new PageVo();
	    pageVo.setTotal(Total.intValue());
	    int totalPages = Total.intValue() / paramVo.getPageSize().intValue();
	    if (0 != Total.intValue() % paramVo.getPageSize().intValue()) {
	      totalPages++;
	    }

	    pageVo.setTotalPages(totalPages);
	    pageVo.setPageSize(paramVo.getPageSize().intValue());
	    pageVo.setPageIndex(paramVo.getPageIndex().intValue());

	    SQLQuery sqlQuery = getCurrentSession().createSQLQuery(paramVo.getSql());
	    addScalarByClass(sqlQuery, paramClass);
	    if ((null != paramVo.getParam()) && (!paramVo.getParam().isEmpty())) {
	      sqlQuery.setProperties(paramVo.getParam());
	    }
	    sqlQuery.setResultTransformer(Transformers.aliasToBean(paramClass));

	    sqlQuery.setFirstResult(paramVo.getPageSize().intValue() * paramVo.getPageIndex().intValue());
	    sqlQuery.setMaxResults(paramVo.getPageSize().intValue());
	    pageVo.setData(sqlQuery.list());
	    pageVo.setCurrentPageSize(pageVo.getData().size());

	    return pageVo;
	}
	private void addScalarByClass(SQLQuery query, Class<?> clazz){
	    Class superClass = clazz.getSuperclass();
	    List<Field> fields = Lists.newArrayList();
	    fields.addAll(Arrays.asList(superClass.getDeclaredFields()));
	    fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
	    for (Field field : fields) {
	      Class c = field.getType();
	      String fieldName = field.getName();
	      if (fieldName.equals("serialVersionUID")) {
	        continue;
	      }
	      addScalar(query, fieldName, c);
	    }
	  }
}
