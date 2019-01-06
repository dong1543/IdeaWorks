package com.simpleChat.serviceimpl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.simpleChat.DAO.FriendDao;
import com.simpleChat.DAO.MessageDao;
import com.simpleChat.DAO.UnreadMessageDao;
import com.simpleChat.DAO.UserDao;
import com.simpleChat.DAO.impl.FriendDaoimpl;
import com.simpleChat.DAO.impl.MessageDaoimpl;
import com.simpleChat.DAO.impl.UnreadMessageDaoimpl;
import com.simpleChat.DAO.impl.UserDaoimpl;
import com.simpleChat.entity.Friend;
import com.simpleChat.entity.Message;
import com.simpleChat.entity.UnreadMessage;
import com.simpleChat.entity.User;
import com.simpleChat.entity.UserInfo;
import com.simpleChat.service.UserService;

public class UserServiceImpl implements UserService {

	@Override
	public UserInfo login(String account,String password) throws Exception {
		UserDao userDao=new UserDaoimpl();
		User user=userDao.searchByAccount(account);
		if (user.getPassword().equals(password)) {
			return completeUser(user);
		}
		else {
			return null;
		}
		
	}

	@Override
	public String register(User user) throws Exception {
		UserDao userDao=new UserDaoimpl();
		User user2=userDao.searchByAccount(user.getAccount());
		if (user2==null) {
			if (userDao.save(user)) {
				return "注册成功";
			}else{
				return "注册失败";
			}
		}else{
			return "该用户名已存在";
		}
	}

	@Override
	public String checkUserRole(User user) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addUserRole(User user, String role) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteUserRole(User user, String role) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserInfo completeUser(User user) throws Exception {
		UserDao userDao=new UserDaoimpl();
		FriendDao friendDao=new FriendDaoimpl();
		UnreadMessageDao unreadMessageDao=new UnreadMessageDaoimpl();
		UserInfo userInfo=new UserInfo();
		userInfo.setUserBasic(user);
		List<Friend> friendIds=friendDao.searchFriends(user);
		if (friendIds!=null) {
			for(Friend friend:friendIds){
				User user2=userDao.searchById(friend.getFriendId());
				List<UnreadMessage> unreadMessages=unreadMessageDao.searchAll(user2,user);
				if (unreadMessages!=null) {
					user2.setUnreadFlag(true);
				}
				userInfo.getFriends().add(user2);
			}
		}
		return userInfo;
		
	}

	@Override
	public String addFriend(int userId, int friendId) throws Exception {
		FriendDao friendDao=new FriendDaoimpl();
		UserDao userDao=new UserDaoimpl();
		User user=userDao.searchById(userId);
		User newFriend=userDao.searchById(friendId);
		List<Friend> friends=friendDao.searchFriends(user);
		boolean flag=true;
		if (friendId==userId) {
			return "不能添加自己为好友";
		}else{
			if (friends!=null) {
				for(Friend friend:friends){
					if (friendId==friend.getFriendId()) {
						flag=false;
					}
				}
			}
			if (flag) {
				if (friendDao.save(user,newFriend)) {
					friendDao.save(newFriend, user);
					return "true";
				}else {
					return "好友添加失败";
				}
			}else{
				return "好友已存在";
			}
		}
	}

	@Override
	public String deleteFriend(int userId, int friendId) throws Exception {
		FriendDao friendDao=new FriendDaoimpl();
		UserDao userDao=new UserDaoimpl();
		MessageDao messageDao=new MessageDaoimpl();
		
		User user=userDao.searchById(userId);
		User friend=userDao.searchById(friendId);
		
		List<Message> messages=messageDao.searchMessageForDelete(user, friend);
		if (messages!=null) {
			for(Message message:messages){
				messageDao.delete(message);
			}
		}
		friendDao.delete(user, friend);
		friendDao.delete(friend, user);
		return "删除好友成功";
	}

	@Override
	public List<User> searchUsers(String keyWord) throws Exception {
		UserDao userDao=new UserDaoimpl();
		return userDao.searchByKeyWord(keyWord);
	}

	@Override
	public String update(User user, String[] fields) throws Exception {
		UserDao userDao=new UserDaoimpl();
		if (userDao.update(user, fields)) {
			return "更新成功";
		}else{
			return "更新失败";
		}
	}

	@Override
	public String upload(User user, File file) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User serchUserByAccount(String account) throws Exception {
		UserDao userDao=new UserDaoimpl();
		User user=userDao.searchByAccount(account);
		return user;
	}
	
	@Override
	public User serchUserById(int id) throws Exception {
		UserDao userDao=new UserDaoimpl();
		User user=userDao.searchById(id);
		return user;
	}
	
}
