package com.simpleChat.DAO;

import java.util.List;

import com.simpleChat.entity.Message;
import com.simpleChat.entity.UnreadMessage;
import com.simpleChat.entity.User;

public interface UnreadMessageDao {
	public boolean save(UnreadMessage message)throws Exception;
	public boolean delete(UnreadMessage message)throws Exception;
	public List<UnreadMessage> searchAll(User sender,User reciver)throws Exception;
	public List<UnreadMessage> searchMessageByDate(String date,User sender,User reciver)throws Exception;
}
