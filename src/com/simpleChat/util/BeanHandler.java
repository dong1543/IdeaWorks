package com.simpleChat.util;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class BeanHandler implements ResultSetHandler {

	//通过class反射要进行解析类，并加载动态类信息
		private Class<?> classD; 
		public BeanHandler(Class<?> class1) {
			this.classD=class1;
		}
		@Override
		public Object handler(ResultSet rs) {
			try{
				if(!rs.next()){
					return null;
				}
				//获取动态类的实例对象
				Object bean = classD.newInstance();
				//获取resultset的元数据操作对象
				ResultSetMetaData metadata = rs.getMetaData();
				//获取resultset返回的列数
				int columnCount = metadata.getColumnCount();
				//循环result的列数，通过class的反射机制，将bean中的属性一一与列对应设置起来
				//这要求bean和数据库表的字段要一一对应起来
				//那么当我们进行条件查询，或者是链接查询则不能适用该方法
				//能不能可以根据查询出来的结果进行动态的生成bean
				for(int i=0;i<columnCount;i++){
					String coulmnName = metadata.getColumnName(i+1);	
					Object coulmnData = rs.getObject(i+1);//
					Field field = classD.getDeclaredField(coulmnName);	
					field.setAccessible(true);
					field.set(bean, coulmnData);
				}
				//返回一个bean对象
				return bean;
			}catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

}
