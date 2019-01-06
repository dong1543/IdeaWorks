package com.simpleChat.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.simpleChat.entity.UnreadMessage;
import com.simpleChat.service.UnreadMessageService;
import com.simpleChat.serviceimpl.UnreadMessageServiceimpl;

/**
 * Servlet implementation class MessageServlet
 */
@WebServlet("/MessageServlet")
public class MessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String mark=request.getParameter("mark");
		switch (mark) {
			case "0":
				getUnreadMessages(request, response);
				break;
			case "1":
				saveUnreadMessage(request, response);
				break;
			default:
				break;
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	protected void getUnreadMessages(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String senderAccount=request.getParameter("senderAccount");
		String receiverAccount=request.getParameter("receiverAccount");
		UnreadMessageService unreadMessageService=new UnreadMessageServiceimpl();
		try {
			List<UnreadMessage> unreadMessages=unreadMessageService.searchAll(senderAccount, receiverAccount);
			if (unreadMessages==null) {
				response.getWriter().write("NoItem");
			}else{
				Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create();
				String json=gson.toJson(unreadMessages);
				response.getWriter().write(json);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	protected void saveUnreadMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String jsonstr=request.getParameter("data");
		Gson gson=new GsonBuilder().create();
		Map<String, String> map=gson.fromJson(jsonstr,new TypeToken<Map<String,String>>(){}.getType());
		String senderAccount=map.get("senderAccount");
		String receiverAccount=map.get("receiverAccount");
		String message=map.get("message");
		UnreadMessageService unreadMessageService=new UnreadMessageServiceimpl();
		try {
			unreadMessageService.save(senderAccount, receiverAccount, message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
