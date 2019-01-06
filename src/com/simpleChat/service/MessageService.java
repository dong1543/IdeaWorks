package com.simpleChat.service;

import java.util.List;

import com.simpleChat.entity.Message;

public interface MessageService {
	
	public List<Message> searchMessageByKeyWords(String senderAccount, String reciverAccount,String keyWords)throws Exception;
	
	public boolean save(String senderAccount,String reciverAccount,String message)throws Exception;

}
