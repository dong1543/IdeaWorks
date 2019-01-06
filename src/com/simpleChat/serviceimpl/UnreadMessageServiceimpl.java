package com.simpleChat.serviceimpl;

import java.util.List;

import com.simpleChat.DAO.UnreadMessageDao;
import com.simpleChat.DAO.UserDao;
import com.simpleChat.DAO.impl.UnreadMessageDaoimpl;
import com.simpleChat.DAO.impl.UserDaoimpl;
import com.simpleChat.entity.UnreadMessage;
import com.simpleChat.entity.User;
import com.simpleChat.service.UnreadMessageService;

public class UnreadMessageServiceimpl implements UnreadMessageService {

	@Override
	public boolean save(String senderAccount, String reciverAccount, String message) throws Exception {
		UserDao userDao=new UserDaoimpl();
		UnreadMessageDao unreadMessageDao=new UnreadMessageDaoimpl();
		User sender=userDao.searchByAccount(senderAccount);
		User reciver=userDao.searchByAccount(reciverAccount);
		UnreadMessage unreadMessage=new UnreadMessage();
		unreadMessage.setMessage(message);
		unreadMessage.setReciverId(reciver.getId());
		unreadMessage.setSenderId(sender.getId());
		
		if (unreadMessageDao.save(unreadMessage)) {
			return true;
		}else{
			return false;
		}
		
	}

	@Override
	public boolean delete(String senderAccount, String reciverAccount) throws Exception {
		UserDao userDao=new UserDaoimpl();
		UnreadMessageDao unreadMessageDao=new UnreadMessageDaoimpl();
		User sender=userDao.searchByAccount(senderAccount);
		User reciver=userDao.searchByAccount(reciverAccount);
		List<UnreadMessage> unreadMessages=unreadMessageDao.searchAll(sender, reciver);
		if (unreadMessages!=null) {
			for(UnreadMessage unreadMessage:unreadMessages){
				unreadMessageDao.delete(unreadMessage);
			}
		}
		return true;
	}

	@Override
	public List<UnreadMessage> searchAll(String senderAccount, String reciverAccount) throws Exception {
		UserDao userDao=new UserDaoimpl();
		UnreadMessageDao unreadMessageDao=new UnreadMessageDaoimpl();
		User sender=userDao.searchByAccount(senderAccount);
		User reciver=userDao.searchByAccount(reciverAccount);
		List<UnreadMessage> unreadMessages=unreadMessageDao.searchAll(sender, reciver);
		//获取后未读数据要删除，以免重复阅读
		if (unreadMessages!=null) {
			for(UnreadMessage unreadMessage:unreadMessages){
				unreadMessageDao.delete(unreadMessage);
			}
		}
		return unreadMessages;
	}

}
