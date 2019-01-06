package com.simpleChat.DAO;

import java.util.List;

import com.simpleChat.entity.Friend;
import com.simpleChat.entity.User;

public interface FriendDao {
	public boolean save(User user,User friend)throws Exception;
	public boolean delete(User user,User friend)throws Exception;
	public List<Friend> searchFriends(User user)throws Exception;  
}
