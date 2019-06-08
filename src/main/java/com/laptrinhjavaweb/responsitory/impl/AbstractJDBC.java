package com.laptrinhjavaweb.responsitory.impl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.laptrinhjavaweb.paging.Pageble;
import com.laptrinhjavaweb.paging.Sorter;
import com.laptrinhjavaweb.reponsitory.GenericJDBC;
import com.laptrinhweb.annotation.Column;
import com.laptrinhweb.annotation.Table;
import com.laptrinhweb.mapper.ResultSetMapper;

public class AbstractJDBC<T> implements GenericJDBC<T> {

	private Class<T> zClass;

	@SuppressWarnings("unchecked")
	public AbstractJDBC() {
		Type type = getClass().getGenericSuperclass();
		ParameterizedType parameterizedType = (ParameterizedType) type;
		zClass = (Class<T>) parameterizedType.getActualTypeArguments()[0];
	}

	private Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/estate4month2019";
			String user = "root";
			String password = "1234";
			return DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List query(String sql, Object... parameters) {
		ResultSetMapper<T> resultSetMapper = new ResultSetMapper<>();
		List<T> results = new ArrayList<>();
		try (Connection conn = getConnection();
				PreparedStatement statement = conn.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery()) {
			if (conn != null) {
				// set parameter to statement
				for (int i = 0; i < parameters.length; i++) {
					// Object parameter = parameters[i];
					int index = i + 1;
					statement.setObject(index, parameters[i]);
				}
				return resultSetMapper.mapRow(resultSet, this.zClass);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public void update(String sql, Object... parameters) {
		Connection conn = null;
		PreparedStatement statement = null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			statement = conn.prepareStatement(sql);
			if (conn != null) {
				for (int i = 0; i < parameters.length; i++) {
					int index = i + 1;
					statement.setObject(index, parameters[i]);
				}

				statement.executeUpdate();
				conn.commit();
			}

		} catch (SQLException e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (statement != null) {
					statement.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	@Override
	public Long insert(String sql, Object... parameters) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);

			statement = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);
			// Class.forName("com.mysql.jdbc.Driver");
			if (conn != null) {

				// set parameter to statement
				for (int i = 0; i < parameters.length; i++) {
					// Object parameter = parameters[i];
					int index = i + 1;
					statement.setObject(index, parameters[i]);
				}

				int rowsInserted = statement.executeUpdate();
				resultSet = statement.getGeneratedKeys();
				conn.commit();
				if (rowsInserted > 0) {
					while (resultSet.next()) {
						Long id = resultSet.getLong(1);
						return id;
					}

				}
			}

		} catch (SQLException e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (statement != null) {
					statement.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public Long insert(Object object) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);

			String sql = createSQLInsert();
			statement = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);

			if (conn != null) {
				Class<?> zClass = object.getClass();
				Field[] fields = zClass.getDeclaredFields();
				// set parameter to statement
				for (int i = 0; i < fields.length; i++) {
					int index = i + 1;
					Field field = fields[i];
					field.setAccessible(true);
					statement.setObject(index, field.get(object));
				}

				Class<?> parentClass = zClass.getSuperclass();
				int indexParent = fields.length + 1;
				while (parentClass != null) {
					for (int i = 0; i < parentClass.getDeclaredFields().length; i++) {
						Field field = parentClass.getDeclaredFields()[i];
						field.setAccessible(true);
						statement.setObject(indexParent, field.get(object));
						indexParent += 1;
					}
					parentClass = parentClass.getSuperclass();
				}

				int rowsInserted = statement.executeUpdate();
				resultSet = statement.getGeneratedKeys();
				conn.commit();
				if (rowsInserted > 0) {
					while (resultSet.next()) {
						Long id = resultSet.getLong(1);
						return id;
					}

				}
			}

		} catch (IllegalAccessException | SQLException e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (statement != null) {
					statement.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return null;
	}

	public String createSQLInsert() {
		// String sql="INSERT INTO " + table +"("+fields+") ";
		String tableName = "";
		StringBuilder fields = new StringBuilder("");
		StringBuilder params = new StringBuilder("");
		if (zClass.isAnnotationPresent(Table.class)) {
			Table table = zClass.getAnnotation(Table.class);
			tableName = table.name();
		}

		// viết query cho class cha - (createdBy,createdDate...) values(?,?)
		for (Field field : zClass.getDeclaredFields()) {// lấy các column trong entity
			if (fields.length() > 1) {
				fields.append(",");
				params.append(",");
			}
			if (field.isAnnotationPresent(Column.class)) {
				Column column = field.getAnnotation(Column.class);
				fields.append(column.name());
				params.append("?");
			}
		}

		// check parent class
		Class<?> parentClass = zClass.getSuperclass();
		while (parentClass != null) {
			// Field[] fieldsParent = parentClass.getDeclaredFields();
			for (Field field : parentClass.getDeclaredFields()) {// get các mảng column trong entity
				if (fields.length() > 1) {
					fields.append(",");
					params.append(",");
				}
				if (field.isAnnotationPresent(Column.class)) {
					Column column = field.getAnnotation(Column.class);
					fields.append(column.name());
					params.append("?");
				}
			}
			parentClass = parentClass.getSuperclass();
		}

		return "INSERT INTO " + tableName + "(" + fields.toString() + ") values(" + params.toString() + ")";
	}

	@Override
	public void update(Object object) {
		Connection conn = null;
		PreparedStatement statement = null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);

			String sql = createSQLUpdate();
			statement = conn.prepareStatement(sql);

			if (conn != null) {
				Class<?> zClass = object.getClass();
				Field[] fields = zClass.getDeclaredFields();
				// set parameter to statement
				for (int i = 0; i < fields.length; i++) {
					int index = i + 1;
					Field field = fields[i];
					field.setAccessible(true);
					statement.setObject(index, field.get(object));
				}

				Class<?> parentClass = zClass.getSuperclass();
				int indexParent = fields.length + 1;
				Object id = null;
				while (parentClass != null) {
					for (int i = 0; i < parentClass.getDeclaredFields().length; i++) {
						Field field = parentClass.getDeclaredFields()[i];
						field.setAccessible(true);
						String name = field.getName();
						statement.setObject(indexParent, field.get(object));
						if (!name.equals("id")) {
							statement.setObject(indexParent, field.get(object));
							indexParent = indexParent + 1;
						} else {
							id = field.get(object);
						}
						// indexParent +=1;
					}
					parentClass = parentClass.getSuperclass();
				}

				statement.setObject(indexParent, id);
				statement.executeUpdate();
				conn.commit();
			}

		} catch (IllegalAccessException | SQLException e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (statement != null) {
					statement.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	public String createSQLUpdate() {
		// String sql="INSERT INTO " + table +"("+fields+") ";
		String tableName = "";
		StringBuilder sets = new StringBuilder("");
		String where = null;
		if (zClass.isAnnotationPresent(Table.class)) {
			Table table = zClass.getAnnotation(Table.class);
			tableName = table.name();
		}

		// viết query cho class cha - (createdBy,createdDate...) values(?,?)
		for (Field field : zClass.getDeclaredFields()) {// lấy các column trong entity
			if (field.isAnnotationPresent(Column.class)) {
				Column column = field.getAnnotation(Column.class);
				String columnName = column.name();
				String value = columnName + "=?";
				if (!columnName.equals("id")) {
					if (sets.length() > 1) {
						sets.append(", ");
					}
					sets.append(value);
				}
			}
		}

		// check parent class
		Class<?> parentClass = zClass.getSuperclass();
		while (parentClass != null) {
			// Field[] fieldsParent = parentClass.getDeclaredFields();
			for (Field field : parentClass.getDeclaredFields()) {// get các mảng column trong entity
				if (field.isAnnotationPresent(Column.class)) {
					Column column = field.getAnnotation(Column.class);
					String columnName = column.name();
					String value = columnName + "=?";
					if (!columnName.equals("id")) {
						if (sets.length() > 1) {
							sets.append(", ");
						}
						sets.append(value);
					} else {
						where = " WHERE " + value;
					}
				}
			}
			parentClass = parentClass.getSuperclass();
		}

		String sql = "Update " + tableName + " Set " + sets.toString() + where;
		return sql;
	}

	private String createSQLDelete() {
		// String sql="INSERT INTO " + table +"("+fields+") ";
				String tableName = "";
				StringBuilder sets = new StringBuilder("");
				String where = null;
				if (zClass.isAnnotationPresent(Table.class)) {
					Table table = zClass.getAnnotation(Table.class);
					tableName = table.name();
				}

				// viết query cho class cha - (createdBy,createdDate...) values(?,?)
				for (Field field : zClass.getDeclaredFields()) {// lấy các column trong entity
					if (field.isAnnotationPresent(Column.class)) {
						Column column = field.getAnnotation(Column.class);
						String columnName = column.name();
						String value = columnName + "=?";
						if(columnName.equals("id")) {
							sets.append(value);
						}
					}
				}

				// check parent class
				Class<?> parentClass = zClass.getSuperclass();
				while (parentClass != null) {
					// Field[] fieldsParent = parentClass.getDeclaredFields();
					for (Field field : parentClass.getDeclaredFields()) {// get các mảng column trong entity
						if (field.isAnnotationPresent(Column.class)) {
							Column column = field.getAnnotation(Column.class);
							String columnName = column.name();
							String value = columnName + "=?";
							if(columnName.equals("id"))
								where = " WHERE " + value;
						}
					}
					parentClass = parentClass.getSuperclass();
				}

				String sql = "Delete from "+tableName + where;
				return sql;
	}

	@Override
	public <T> T searchID (Long id) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		ResultSetMapper<T> resultSetMapper = new ResultSetMapper<>();
		String sql = createSQLFindID();
		try {
			conn = getConnection();
			statement = conn.prepareStatement(sql);
			statement.setObject(1, id);
			resultSet = statement.executeQuery();
			if (conn != null) {	
				return resultSetMapper.mapRow(resultSet, this.zClass).get(0);
			}
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println(e.getMessage());
		} finally {
			try {
				conn.close();
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	private String createSQLFindID() {
		String tableName = "";
		StringBuilder sets = new StringBuilder("");
		String where = null;
		if (zClass.isAnnotationPresent(Table.class)) {
			Table table = zClass.getAnnotation(Table.class);
			tableName = table.name();
		}

		// check parent class
		Class<?> parentClass = zClass.getSuperclass();
		while (parentClass != null) {
			// Field[] fieldsParent = parentClass.getDeclaredFields();
			for (Field field : parentClass.getDeclaredFields()) {// get các mảng column trong entity
				if (field.isAnnotationPresent(Column.class)) {
					Column column = field.getAnnotation(Column.class);
					String columnName = column.name();
					String value = columnName + "=?";
					if(columnName.equals("id"))
						where = " WHERE " + value;
				}
			}
			parentClass = parentClass.getSuperclass();
		}

		String sql = "Select * from "+tableName + where;
		return sql;
	}

	@Override
	public void delete(long id) {
		Connection conn = null;
		PreparedStatement statement = null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			
			String tableName="";
			if(zClass.isAnnotationPresent(Table.class)) {
				Table table = zClass.getAnnotation(Table.class);
				tableName = table.name();
			}
			
			String sql = "DELETE FROM " + tableName +"Where id=?";
			statement = conn.prepareStatement(sql);

			if (conn != null) {
				statement.setObject(1, id);
				statement.executeUpdate();
				conn.commit();
			}

		} catch (SQLException e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (statement != null) {
					statement.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	@Override
	public List<T> findAll(Map<String,Object> properties,Pageble pageble ,Object...where) {
		Connection conn = null;
		Statement statement = null;
		ResultSet resultSet = null;
		ResultSetMapper<T> resultSetMapper = new ResultSetMapper<>();
		StringBuilder sql = createSQLfindAll(properties);

		if(where != null && where.length >0) {
			sql.append(where[0]);
		}
		if(pageble !=null) {
			if(pageble.getSorter() != null) {
				Sorter sorter = pageble.getSorter();
				sql.append(" ORDER BY "+sorter.getSortName()+" "+sorter.getSortBy()+"");
			}
			if(pageble.getOffset() != null && pageble.getLimit() != null) {
				sql.append(" LIMIT " + pageble.getOffset()+", "+pageble.getLimit()+"");
			}
		}	
		try {
			conn=getConnection();
			statement= conn.createStatement();
			resultSet = statement.executeQuery(sql.toString());
			
			if(conn!=null) {
				return resultSetMapper.mapRow(resultSet, this.zClass);
			}
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println(e.getMessage());
		} finally {
			try {
				conn.close();
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	private StringBuilder createSQLfindAll(Map<String, Object> properties) {
		String tableName="";
		if(this.zClass.isAnnotationPresent(Table.class)) {
			Table table = zClass.getAnnotation(Table.class);
			tableName = table.name();
		}
		StringBuilder sql = new StringBuilder("select * from "+tableName+" where 1=1");
		if(properties != null && properties.size()>0) {
			//cấp params và values theo đối tượng phát động properties.size
			String[] params = new String[properties.size()];
			Object[] values = new Object[properties.size()];
			int i=0;
			for(Map.Entry<?, ?> item :properties.entrySet()) {
				params[i] = (String) item.getKey();
				values[i] = item.getValue();
				i++;
			}
			for(int j= 0 ;j<params.length;j++) {
				if(values[j] instanceof String) {
				 sql.append(" and lower ("+params[j]+") like '%"+values[j]+"%'");
				}
				if(values[j] instanceof Integer) {
					sql.append(" and "+params[j]+" = "+values[j]+"");
				}
			}
		}
		return sql;
	}
}
