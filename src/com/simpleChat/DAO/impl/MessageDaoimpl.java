package com.simpleChat.DAO.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.simpleChat.DAO.MessageDao;
import com.simpleChat.entity.Message;
import com.simpleChat.entity.User;
import com.simpleChat.util.BeanListHandler;
import com.simpleChat.util.CRUD;

public class MessageDaoimpl implements MessageDao {

	@Override
	public boolean save(Message message) throws Exception {
		String sql="insert into message(senderId,reciverId,message,date) value(?,?,?,?)";
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
	public List<Message> searchMessageByKeyWord(User sender, User reciver,String keyWord) throws Exception {
		String sql="select * from message where senderId=? and reciverId=? and message like '%"+keyWord+"%'";
		Object[] params=new Object[2];
		params[0]=sender.getId();
		params[1]=reciver.getId();
		List<Message> messages=(List<Message>)CRUD.query(sql, params, new BeanListHandler(Message.class));
		return messages;
	}

	@Override
	public List<Message> searchMessageByDate(String date, User sender, User reciver) throws Exception {
		String sql="select * from message where senderId=? and reciverId=? and date like '%"+date+"%'";
		Object[] params=new Object[2];
		params[0]=sender.getId();
		params[1]=reciver.getId();
		List<Message> messages=(List<Message>)CRUD.query(sql, params, new BeanListHandler(Message.class));
		return messages;
	}
	
	@Override
	public boolean delete(Message message) throws Exception {
		String sql="delete from message where id=?";
		Object[] params=new Object[1];
		params[0]=message.getId();
		if (CRUD.update(sql, params)==0) {
			return false;
		}else {
			return true;
		}
	}
	
	@Override
	public List<Message> searchMessageForDelete(User user, User friend) throws Exception {
		String sql="select * from message where senderId=? and reciverId=?";
		Object[] params=new Object[2];
		params[0]=user.getId();
		params[1]=friend.getId();
		List<Message> messages=(List<Message>)CRUD.query(sql, params, new BeanListHandler(Message.class));
		params[0]=friend.getId();
		params[1]=user.getId();
		List<Message> messages2=(List<Message>)CRUD.query(sql, params, new BeanListHandler(Message.class));
		if (messages==null&&messages2!=null) {
			return messages2;
		}else if (messages!=null&&messages2==null) {
			return messages;
		}else if(messages!=null&&messages2!=null){
			messages.addAll(messages2);
			return messages;
		}else {
			return null;
		}
		
	}

}
