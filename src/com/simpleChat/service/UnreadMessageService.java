package com.simpleChat.service;

import java.util.List;

import com.simpleChat.entity.UnreadMessage;

public interface UnreadMessageService {
	
	public boolean save(String senderAccount,String reciverAccount,String message)throws Exception;
	//根据发送方接收方来删除数据，谁看了就删谁的
	public boolean delete(String senderAccount,String reciverAccount)throws Exception;
	
	public List<UnreadMessage> searchAll(String senderAccount,String reciverAccount)throws Exception;

}
