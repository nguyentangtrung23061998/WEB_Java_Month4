package com.laptrinhjavaweb.reponsitory;

import java.util.List;
import java.util.Map;

import com.laptrinhjavaweb.paging.Pageble;

public interface GenericJDBC<T> {
	List<T> query(String sql, Object... parameters);
	void update(String sql, Object... parameters);
	Long insert(String sql, Object... parameters);
	Long insert(Object object);
	void update(Object object);
	void delete(long id);
	//nâng cao
	List<T> findAll(Map<String,Object> properties,Pageble pageble ,Object...where);
	<T> T searchID(Long id);
}
