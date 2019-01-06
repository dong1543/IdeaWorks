package com.simpleChat.util;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class BeanHandler implements ResultSetHandler {

	//ͨ��class����Ҫ���н����࣬�����ض�̬����Ϣ
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
				//��ȡ��̬���ʵ������
				Object bean = classD.newInstance();
				//��ȡresultset��Ԫ���ݲ�������
				ResultSetMetaData metadata = rs.getMetaData();
				//��ȡresultset���ص�����
				int columnCount = metadata.getColumnCount();
				//ѭ��result��������ͨ��class�ķ�����ƣ���bean�е�����һһ���ж�Ӧ��������
				//��Ҫ��bean�����ݿ����ֶ�Ҫһһ��Ӧ����
				//��ô�����ǽ���������ѯ�����������Ӳ�ѯ�������ø÷���
				//�ܲ��ܿ��Ը��ݲ�ѯ�����Ľ�����ж�̬������bean
				for(int i=0;i<columnCount;i++){
					String coulmnName = metadata.getColumnName(i+1);	
					Object coulmnData = rs.getObject(i+1);//
					Field field = classD.getDeclaredField(coulmnName);	
					field.setAccessible(true);
					field.set(bean, coulmnData);
				}
				//����һ��bean����
				return bean;
			}catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

}
