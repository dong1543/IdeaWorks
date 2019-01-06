package com.simpleChat.DAO.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.simpleChat.DAO.UnreadMessageDao;
import com.simpleChat.entity.Message;
import com.simpleChat.entity.UnreadMessage;
import com.simpleChat.entity.User;
import com.simpleChat.util.BeanListHandler;
import com.simpleChat.util.CRUD;

public class UnreadMessageDaoimpl implements UnreadMessageDao {

	@Override
	public boolean save(UnreadMessage message) throws Exception {
		String sql="insert into unreadmessage(senderId,reciverId,message,date) value(?,?,?,?)";
		Timestamp currentTime=new Timestamp(new Date().getTime());
		Object[] params=new Object[4];
		params[0]=message.getSenderId();
		params[1]=message.getReciverId();
		params[2]=message.getMessage();
		params[3]=currentTime;
		if (CRUD.update(sql, params)==0) {
			return false;
		}else {
			return true;
		}
	}

	@Override
	public boolean delete(UnreadMessage message) throws Exception {
		String sql="delete from unreadmessage where id=?";
		Object[] params=new Object[1];
		params[0]=message.getId();
		if (CRUD.update(sql, params)==0) {
			return false;
		}else {
			return true;
		}
	}

	@Override
	public List<UnreadMessage> searchAll(User sender, User reciver) throws Exception {
		String sql="select * from unreadmessage where senderId=? and reciverId=?";
		Object[] params=new Object[2];
		params[0]=sender.getId();
		params[1]=reciver.getId();
		List<UnreadMessage> unreadMessages=(List<UnreadMessage>)CRUD.query(sql, params, new BeanListHandler(UnreadMessage.class));
		return unreadMessages;
	}

	@Override
	public List<UnreadMessage> searchMessageByDate(String date, User sender, User reciver) throws Exception {
		String sql="select * from unreadmessage where senderId=? and reciverId=? and date like '%"+date+"%'";
		Object[] params=new Object[2];
		params[0]=sender.getId();
		params[1]=reciver.getId();
		List<UnreadMessage> unreadMessages=(List<UnreadMessage>)CRUD.query(sql, params, new BeanListHandler(UnreadMessage.class));
		return unreadMessages;
	}

}
