package com.simpleChat.service;

import java.io.File;
import java.util.List;

import com.simpleChat.entity.User;
import com.simpleChat.entity.UserInfo;

public interface UserService {
	public UserInfo login(String account,String password)throws Exception;
	public String register(User user)throws Exception;
	public String checkUserRole(User user)throws Exception;
	public String addUserRole(User user,String role)throws Exception;
	public String deleteUserRole(User user,String role)throws Exception;
	public UserInfo completeUser(User user)throws Exception;
	public String addFriend(int userId,int friendId)throws Exception;
	public String deleteFriend(int userId,int friendId)throws Exception;
	public List<User> searchUsers(String keyWord)throws Exception;
	public String update(User user, String[] fields)throws Exception;
	public String upload(User user,File file)throws Exception;
	public User serchUserByAccount(String account)throws Exception;
	public User serchUserById(int id)throws Exception;
}
