package com.simpleChat.DAO.impl;

import java.util.List;

import com.simpleChat.DAO.FriendDao;
import com.simpleChat.entity.Friend;
import com.simpleChat.entity.User;
import com.simpleChat.util.BeanHandler;
import com.simpleChat.util.BeanListHandler;
import com.simpleChat.util.CRUD;

public class FriendDaoimpl implements FriendDao {

	@Override
	public boolean save(User user, User friend) throws Exception {
		String sql="insert into friend(userId,friendId) values(?,?)";
		
		Object[] params=new Object[2];
		
		params[0]=user.getId();
		params[1]=friend.getId();
		
		int result = CRUD.update(sql, params);
		if (result == 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean delete(User user, User friend) throws Exception {
		String sql="delete from friend where userId=? and friendId=?";
		Object[] params=new Object[2];
		params[0]=user.getId();
		params[1]=friend.getId();
		if (CRUD.update(sql, params)==0) {
			return false;
		}else{
			return true;
		}
	}

	@Override
	public List<Friend> searchFriends(User user) throws Exception {
		String sql="select friendId from friend where userId=?";
		Object[] params=new Object[1];
		params[0]=user.getId();
		List<Friend> friends=(List<Friend>)CRUD.query(sql, params, new BeanListHandler(Friend.class));
		return friends;
	}

}
