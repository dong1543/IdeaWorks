package com.simpleChat.serviceimpl;

import java.util.List;

import com.simpleChat.DAO.MessageDao;
import com.simpleChat.DAO.UserDao;
import com.simpleChat.DAO.impl.MessageDaoimpl;
import com.simpleChat.DAO.impl.UserDaoimpl;
import com.simpleChat.entity.Message;
import com.simpleChat.entity.User;
import com.simpleChat.service.MessageService;

public class MessageServiceimpl implements MessageService {

	@Override
	public List<Message> searchMessageByKeyWords(String senderAccount, String reciverAccount,String keyWords) throws Exception {
		MessageDao messageDao=new MessageDaoimpl();
		UserDao userDao=new UserDaoimpl();
		User sender=userDao.searchByAccount(senderAccount);
		User reciver=userDao.searchByAccount(reciverAccount);
		//信息是双向的，所以要颠倒过来再查一遍
		List<Message> messages1=messageDao.searchMessageByKeyWord(sender,reciver,keyWords);
		List<Message> messages2=messageDao.searchMessageByKeyWord(sender,reciver,keyWords);
		if (messages1==null&&messages2!=null) {
			return messages2;
		}else if (messages1!=null&&messages2==null) {
			return messages1;
		}else if (messages1==null&&messages2==null) {
			return null;
		}else {
			messages1.addAll(messages2);
			return messages1;
		}
	}

	@Override
	public boolean save(String senderAccount, String reciverAccount, String message) throws Exception {
		MessageDao messageDao=new MessageDaoimpl();
		UserDao userDao=new UserDaoimpl();
		User sender=userDao.searchByAccount(senderAccount);
		User reciver=userDao.searchByAccount(reciverAccount);
		Message message2=new Message();
		message2.setMessage(message);
		message2.setReciverId(reciver.getId());
		message2.setSenderId(sender.getId());
		if (messageDao.save(message2)) {
			return true;
		}else{
			return false;
		}
	}

}
