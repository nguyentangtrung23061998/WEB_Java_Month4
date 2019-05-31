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

	@Override
	public void delete(Object object) {
		Connection conn = null;
		PreparedStatement statement = null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			String sql = createSQLDelete();
			statement = conn.prepareStatement(sql);
			if (conn != null) {
				Class<?> zClass = object.getClass();
				Field[] fields = zClass.getDeclaredFields();
				Object id =null;		
				Class<?> parentClass = zClass.getSuperclass();
				int indexParent = 0;
				while(parentClass != null) {
					Field[] fieldParents= parentClass.getDeclaredFields();
					for(int i=0; i<fieldParents.length;i++) {
						Field fieldParent = fieldParents[i];
						fieldParent.setAccessible(true);
						String name = fieldParent.getName();
						if(name.equals("id")) {	
							id = fieldParent.get(object);
							indexParent++;
							break;
						}
					}
					parentClass = parentClass.getSuperclass();
				}
				statement.setObject(indexParent, id);
				statement.executeUpdate();
				conn.commit();
			}
		}  catch (IllegalAccessException | SQLException e) {
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
	public List<T> searchID (Object object) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet resultset = null;

		List<T> id = new ArrayList<>();
		ResultSetMapper<T> resultSetMapper = new ResultSetMapper<>();
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			String sql = createSQLFindID();
			statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			if (conn != null) {

				// check parent class
				Class<?> parentClass = zClass.getSuperclass();
				int indexParent = 0;
				Object ids = null;
				while (parentClass != null) {

					for (int i = 0; i < parentClass.getDeclaredFields().length; i++) {
						Field field = parentClass.getDeclaredFields()[i]; // lấy field từ class parent (createBy,
																			// CreateDate)
						field.setAccessible(true); // object cũng giống như một cô gái, muộn đụng vô thì phải xin cấp
													// quyền

						String name = field.getName();
						if (name.equals("id")) {
							ids = field.get(object);
							indexParent = indexParent + 1;
							break;
						}
						indexParent = indexParent + 1;
					}
					parentClass = parentClass.getSuperclass();
				}
				statement.setObject(indexParent, ids);
				resultset = statement.executeQuery();
				System.out.println(statement);
				
				conn.commit();
				return resultSetMapper.mapRow(resultset, this.zClass);
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
	public List<T> search(Object object) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		List<T> id =new ArrayList<>();
		ResultSetMapper<T> resultSetMapper = new ResultSetMapper<>();
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			String sql = createSQLSearch();
			statement = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			if(conn!=null) {
				Class<?> zClass = object.getClass();
				System.out.println(zClass.getDeclaredFields());
				Field[] fields = zClass.getDeclaredFields();
				for(int i=0;i<fields.length;i++) {
					int index = i+1;
					Field field = fields[i];
					field.setAccessible(true);
				}
				//check parent
				Class<?> zClassParent = zClass.getSuperclass();
				System.out.println(zClass.getDeclaredFields());
				int indexParent = fields.length +1;
				while(zClassParent!=null) {
					for(int i=0;i<zClass.getDeclaredFields().length;i++) {
						Field fieldParent = zClass.getDeclaredFields()[i];
						fieldParent.setAccessible(true);
						indexParent++;
					}
					zClassParent = zClassParent.getSuperclass();
				}
				resultSet = statement.executeQuery();
				conn.commit();
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
	//thua về sau :))
	private String createSQLSearch() {
		String tableName = "";
		StringBuilder where =new StringBuilder("");
		String nameColumn ="";
		String value="";
		if(zClass.isAnnotationPresent(Table.class)) {
			Table table = zClass.getAnnotation(Table.class);
			tableName = table.name();
		}
		
		Field[] fields = zClass.getDeclaredFields();
		for(int i=0 ;i<fields.length;i++) {
			Field field = fields[i];
			if(field.isAnnotationPresent(Column.class)) {
				Column column = zClass.getAnnotation(Column.class);
				nameColumn=column.name();
				
			}
		}
		return "select * from "+tableName +" where "+nameColumn;
	}
}
