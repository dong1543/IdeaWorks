package com.simpleChat.test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.simpleChat.DAO.FriendDao;
import com.simpleChat.DAO.MessageDao;
import com.simpleChat.DAO.UserDao;
import com.simpleChat.DAO.impl.FriendDaoimpl;
import com.simpleChat.DAO.impl.MessageDaoimpl;
import com.simpleChat.DAO.impl.UserDaoimpl;
import com.simpleChat.entity.Friend;
import com.simpleChat.entity.Message;
import com.simpleChat.entity.User;
import com.simpleChat.serviceimpl.UserServiceImpl;
import com.simpleChat.util.CRUD;

public class Test {

	public static void main(String[] args)throws Exception {
//		FriendDao friendDao=new FriendDaoimpl();
		UserDao userDao=new UserDaoimpl();
//		User user=userDao.searchByAccount("15984918367");
//		List<Friend> friendIds=friendDao.searchFriends(user);
//		for(Friend i:friendIds){
//			System.out.println(i.getFriendId());
//		}
//		MessageDao messageDao=new MessageDaoimpl();
//		List<Message> messages=messageDao.searchMessageByDate("2017-12-30", userDao.searchById(4), userDao.searchById(6));
//		for(Message message2:messages){
//			System.out.print(message2.getMessage());
//		}
//		UserServiceImpl userServiceImpl=new UserServiceImpl();
//		User user=userServiceImpl.serchUserByAccount("1543");
//		if (user==null) {
//			System.out.println("yes");
//		}else{
//			System.out.println("no");
//		}
		User user=userDao.searchByAccount("15984918367");
		System.out.println(user.isUnreadFlag());
		
		
	}

}
