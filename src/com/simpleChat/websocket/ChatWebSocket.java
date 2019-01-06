package com.simpleChat.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.simpleChat.entity.Message;
import com.simpleChat.service.MessageService;
import com.simpleChat.service.UnreadMessageService;
import com.simpleChat.serviceimpl.MessageServiceimpl;
import com.simpleChat.serviceimpl.UnreadMessageServiceimpl;

@ServerEndpoint(value="/ChatWebSocket/{userAccount}")
public class ChatWebSocket {
	//线程安全的MAP结构全局范围使用用来存放连接到服务器的User的websocket连接，如果数据量大还是建议不使用tomcat的websocket支持
	private static ConcurrentMap<String, ChatWebSocket> onlineUserMap=new ConcurrentHashMap<>();
	//Session，websocket使用该session来发送消息,每一个用户拥有一个WebSocket连接，一个线程，一个session
	private Session session;
	private String userAccount;
	//定义连接开始方法,从前端传送一个参数,可以用注解参数来获得标识该websocket是哪位User的,并注册到MAP结构中
	@OnOpen
	public void onOpen(@PathParam(value="userAccount")String userAccount,Session session){
		this.session = session;
		this.userAccount=userAccount;
		System.out.println(userAccount);
		onlineUserMap.put(userAccount, this);
		
	}
	@OnClose
	public void onClose(){
		onlineUserMap.remove(this.userAccount);
		System.out.println(this.userAccount+"---退出");
	}
	@OnMessage
	public void onMessage(String jsonstr,Session session){
		//解析jsonstr，取出sender，receiver，message
		Gson gson=new GsonBuilder().create();
		Map<String, String> map=gson.fromJson(jsonstr,new TypeToken<Map<String,String>>(){}.getType());
		String receiverAccount=map.get("receiverAccount");
		String senderAccount=map.get("senderAccount");//发送方一定是自己
		String message=map.get("message");
		ChatWebSocket reciverWebsocket=onlineUserMap.get(receiverAccount);
		if (reciverWebsocket==null) {//说明不在线,将消息保存到unreadMessage中
			UnreadMessageService unreadMessageService=new UnreadMessageServiceimpl();
			try {
				unreadMessageService.save(senderAccount, receiverAccount, message);
				this.sendMessageToDataBase(senderAccount, receiverAccount, message);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			reciverWebsocket.sendMessageToReceiver(jsonstr);
			this.sendMessageToDataBase(senderAccount, receiverAccount, message);
		}
	}
	//自定义发送方法,发送给用户和保存到服务器本地以备用户查询聊天记录,将json数据原封不动的发送出去，让接收方再次解析出发送方是谁
	public void sendMessageToReceiver(String jsonstr){
		try {
			this.session.getBasicRemote().sendText(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void sendMessageToDataBase(String senderAccount,String reciverAccount,String message){
		MessageService messageService=new MessageServiceimpl();
		try {
			messageService.save(senderAccount, reciverAccount, message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@OnError
	public void onError(Session session, Throwable error){
		System.out.println("发生错误");
		error.printStackTrace();
	}
}
