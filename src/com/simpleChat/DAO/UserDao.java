package com.simpleChat.DAO;

import java.util.List;

import com.simpleChat.entity.User;

public interface UserDao {
	
	public boolean save(User user)throws Exception;
	
	public boolean update(User user,String[] fields)throws Exception;
	
	public boolean delete(User user)throws Exception;
	
	public User searchByAccount(String account)throws Exception;
	
	public User searchById(int id)throws Exception;
	
	public List<User> searchByKeyWord(String keyWord)throws Exception;
	
	public List<User> searchAll()throws Exception;

}
