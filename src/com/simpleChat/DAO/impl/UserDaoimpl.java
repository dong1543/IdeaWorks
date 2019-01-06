package com.simpleChat.DAO.impl;

import java.util.List;
import com.simpleChat.DAO.UserDao;
import com.simpleChat.entity.User;
import com.simpleChat.util.BeanHandler;
import com.simpleChat.util.BeanListHandler;
import com.simpleChat.util.CRUD;

public class UserDaoimpl implements UserDao {

	@Override
	public boolean save(User user) throws Exception {
		String sql="insert into user(account,password,name,age,city,gender) values(?,?,?,?,?,?)";
		
		Object[] params=new Object[6];
		
		params[0]=user.getAccount();
		params[1]=user.getPassword();
		params[2]=user.getName();
		params[3]=user.getAge();
		params[4]=user.getCity();
		params[5]=user.getGender();
		
		int result = CRUD.update(sql, params);
		if (result == 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean update(User user, String[] fields) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("update user set ");
		for(int i=0;i<fields.length;i++){
			if (i==fields.length-1) {
				sql.append(fields[i]).append("=? ");
			}else {
				sql.append(fields[i]).append("=?,");
			}
		}
		sql.append("where id=?");
		Object[] params=CRUD.setValueForParams(user, fields);
		if (CRUD.update(new String(sql), params)==0) {
			return false;
		}else {
			return true;
		}
		
	}

	@Override
	public boolean delete(User user) throws Exception {
		String sql="delete from user where id=?";
		Object[] params=new Object[1];
		params[0]=user.getId();
		if (CRUD.update(sql, params)==0) {
			return false;
		}else{
			return true;
		}
	}

	@Override
	public User searchByAccount(String account) throws Exception {
		String sql="select * from user where account=?";
		Object[] params=new Object[1];
		params[0]=account;
		User user=(User)CRUD.query(sql, params, new BeanHandler(User.class));
		return user;
	}

	@Override
	public User searchById(int id) throws Exception {
		String sql="select * from user where id=?";
		Object[] params=new Object[1];
		params[0]=id;
		User user=(User)CRUD.query(sql, params, new BeanHandler(User.class));
		return user;
	}

	@Override
	public List<User> searchByKeyWord(String keyWord) throws Exception {
		String sql="select * from user where "
				+"name like '%"+keyWord+"%'"
				+"or city like '%"+keyWord+"%'"
				+"or gender like '%"+keyWord+"%'"
				+"or account like '%"+keyWord+"%'";
		Object[] params=new Object[0];
		@SuppressWarnings("unchecked")
		List<User> users=(List<User>)CRUD.query(sql, params, new BeanListHandler(User.class));
		return users;
	}

	@Override
	public List<User> searchAll() throws Exception {
		String sql="select * from user";
		Object[] params=new Object[0];
		@SuppressWarnings("unchecked")
		List<User> users=(List<User>)CRUD.query(sql, params, new BeanListHandler(User.class));
		return users;
	}

}
