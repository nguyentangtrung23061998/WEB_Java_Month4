package com.laptrinhweb.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.laptrinhweb.annotation.Column;
import com.laptrinhweb.annotation.Entity;

public class ResultSetMapper<T> {

	public List<T> mapRow(ResultSet resultSet, Class<?> zClass){
		List<T> result = new ArrayList<>();
		if (zClass.isAnnotationPresent(Entity.class)) {
			try {
				ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
				Field[] fields = zClass.getDeclaredFields();
				while (resultSet.next()) {
					@SuppressWarnings("unchecked")
					T object = (T) zClass.newInstance();
					for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
						String columnName = resultSetMetaData.getColumnName(i+1);
						Object columnValue = resultSet.getObject(i+1);
						//curent class
						convert(fields, columnName, columnValue, object);
						//parent class
						Class<?> parentClass = zClass.getSuperclass();
						while (parentClass != null) {
							Field[] fieldParents = parentClass.getDeclaredFields();
							convert(fieldParents,columnName,columnValue,object);
							parentClass = parentClass.getSuperclass();
						}
					}
					result.add(object);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	private void convert(Field[] fields, String columnName, Object columnValue, T object) throws IllegalAccessException, InvocationTargetException {
		// TODO Auto-generated method stub
		for (Field field : fields) {
			if(field.isAnnotationPresent(Column.class)) {
				Column column = field.getAnnotation(Column.class);
				if(column.name().equals(columnName) && columnValue !=null) {
					BeanUtils.setProperty(object, field.getName(), columnValue);
					break;
				}
			}
		}
	}
}
