package com.simpleChat.DAO;


import java.util.List;

import com.simpleChat.entity.Message;
import com.simpleChat.entity.User;

public interface MessageDao {
	public boolean save(Message message)throws Exception;
	public List<Message> searchMessageByKeyWord(User sender, User reciver,String keyWord)throws Exception;
	public List<Message> searchMessageByDate(String date,User sender,User reciver)throws Exception;
	public boolean delete(Message message)throws Exception;
	public List<Message> searchMessageForDelete(User user,User friend)throws Exception;
}
