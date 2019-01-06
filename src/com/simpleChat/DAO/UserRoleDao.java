package com.simpleChat.DAO;

import java.util.List;


import com.simpleChat.entity.User;

public interface UserRoleDao {
	public List<String> searchRole(User user)throws Exception;
	public boolean save(int roleId,User user)throws Exception;
}
