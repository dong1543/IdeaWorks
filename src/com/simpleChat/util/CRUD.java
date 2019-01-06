package com.simpleChat.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class CRUD {

	public static int update(String sql, Object[] params) {
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = Connect.getConnection();
			pStatement = connection.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				pStatement.setObject(i + 1, params[i]);
			}
			return pStatement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Connect.close(pStatement);
			Connect.close(connection);
		}
	}

	public static Object query(String sql, Object[] params, ResultSetHandler rHandler) {
		Connection con = null;
		PreparedStatement pStatement = null;
		ResultSet resultSet = null;
		
		try {
			con = Connect.getConnection();
			pStatement = con.prepareStatement(sql);
			if (params.length!=0) {
				for (int i = 0; i < params.length; i++) {
					pStatement.setObject(i + 1, params[i]);
				}
			}
			resultSet = pStatement.executeQuery();
			Object result = rHandler.handler(resultSet);
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		} finally {
			Connect.close(pStatement);
			Connect.close(resultSet);
			Connect.close(con);
		}
	}
	
	public static Object[] setValueForParams(Object object,String[] fields)throws Exception{
		Class<?> clazz=object.getClass();
		Object[] params=new Object[fields.length+1];
		for(int i=0;i<fields.length;i++){
			Field field=clazz.getDeclaredField(fields[i]);
			field.setAccessible(true);
			params[i]=field.get(object);
		}
		Field field=clazz.getDeclaredField("id");
		field.setAccessible(true);
		params[fields.length]=field.get(object);
		return params;
	}
	
	public static void set(Object object,String fieldName,String fieldValue)throws Exception{
		Class<?> clazz=object.getClass();
		Field field=clazz.getDeclaredField(fieldName);
		String fieldTypeName=field.getType().getSimpleName();
		Object newFieldValue=changeType(fieldTypeName, fieldValue);
		field.setAccessible(true);
		field.set(object, newFieldValue);
	}
	
	private static Object changeType(String fieldTypeName,String fieldValue){
		if (fieldTypeName.equals("int")) {
			return Integer.parseInt(fieldValue);
		}else if (fieldTypeName.equals("double")) {
			return Double.parseDouble(fieldValue);
		}else if (fieldTypeName.equals("BigDecimal")) {
			return new BigDecimal(fieldValue);
		}else if (fieldTypeName.equals("Timestamp")) {
			Timestamp timestamp=new Timestamp(new Date().getTime());
			return timestamp=Timestamp.valueOf(fieldValue);
		}else {
			return fieldValue;
		}
	}

}
