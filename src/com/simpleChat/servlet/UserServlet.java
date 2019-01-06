package com.simpleChat.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.descriptor.web.LoginConfig;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.simpleChat.DAO.FriendDao;
import com.simpleChat.entity.User;
import com.simpleChat.entity.UserInfo;
import com.simpleChat.service.UserService;
import com.simpleChat.serviceimpl.UserServiceImpl;
import com.simpleChat.util.CRUD;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String mark=request.getParameter("mark");
		switch (mark) {
			case "0":
				login(request, response);
				break;
			case "1":
				register(request, response);
				break;
			case "2":
				checkUserAccount(request, response);
				break;
			case "3":
				fileUpload(request, response);
				break;
			case "4":
				logout(request, response);
				break;
			case "5":
				searchUsersByKeyWords(request, response);
				break;
			case "6":
				addFriend(request, response);
				break;
			case "7":
				searchFriends(request, response);
				break;
			case "8":
				deleteFriend(request, response);
				break;
			case "9":
				update(request, response);
				break;
			case "10":
				isFriend(request, response);
				break;
			default:
				break;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	protected void login(HttpServletRequest request, HttpServletResponse response){
		UserInfo userInfo=new UserInfo();
		UserServiceImpl userServiceImpl=new UserServiceImpl();
		HttpSession session=request.getSession();
		String account=request.getParameter("account");
		String password=request.getParameter("password");
		
		try {
			User user=userServiceImpl.serchUserByAccount(account);
			if (user==null) {
				response.sendRedirect(request.getContextPath()+"/jsp/Login.jsp");
			}else{
				userInfo=userServiceImpl.login(account, password);
				if (userInfo!=null) {
					session.setAttribute("userInfo", userInfo);
					RequestDispatcher dispatcher=this.getServletContext().getRequestDispatcher("/jsp/Chat.jsp");
					dispatcher.forward(request, response);
				}else{
					response.sendRedirect(request.getContextPath()+"/jsp/Login.jsp");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void register(HttpServletRequest request, HttpServletResponse response) {
		String feedBack=new String();
		//获取json字符串
		String jsonStr=request.getParameter("data");
		//创建第三方json解析对象GSON
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create();
		//解析数据并封装实体类,此处封装为Map
		Map<String, String> map=gson.fromJson(jsonStr,new TypeToken<Map<String,String>>(){}.getType());
		//创建实体类User对象
		User user=new User();
		user.setAccount(map.get("account"));
		user.setName(map.get("name"));
		user.setPassword(map.get("password"));
		user.setAge(0);
		user.setCity("");
		user.setGender("");
		UserService userService=new UserServiceImpl();
		try {
			feedBack=userService.register(user);
			response.getWriter().write(feedBack);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void checkUserAccount(HttpServletRequest request, HttpServletResponse response){
		String account=request.getParameter("account");
		
		UserService userService=new UserServiceImpl();
		try {
			User user=userService.serchUserByAccount(account);
			if (user==null) {
				response.getWriter().write("true");
			}else{
				response.getWriter().write("false");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void fileUpload(HttpServletRequest request,HttpServletResponse response){
		
		DiskFileItemFactory dFactory=new DiskFileItemFactory();
		ServletFileUpload sUpload=new ServletFileUpload(dFactory);
		
		List<FileItem> items;
		try {
			items = sUpload.parseRequest(request);
			for(FileItem item:items){
				if (item.isFormField()) {
					String field=item.getFieldName();
					String value=item.getString("utf-8");
				}else{
					String fileRootPath=request.getServletContext().getRealPath("/userHeadImg/");
					UserInfo userInfo=(UserInfo) request.getSession().getAttribute("userInfo");
					String account=userInfo.getUserBasic().getAccount();
					item.write(new File(fileRootPath+account+".jpg"));
					item.delete();
				}
			}
			response.getWriter().write("上传成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	protected void logout(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.invalidate();
		response.getWriter().write("注销成功");
	}
	
	protected void searchUsersByKeyWords(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String keyWords=request.getParameter("keyWords");
		UserService userService=new UserServiceImpl();
		try {
			List<User> users=userService.searchUsers(keyWords);
			//将user集合封装为json字符串
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd mm:hh:ss").create();
			String json=gson.toJson(users);
			response.getWriter().write(json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	protected void addFriend(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		UserService userService=new UserServiceImpl();
		UserInfo userInfo=(UserInfo) request.getSession().getAttribute("userInfo");
		try {
			User friend=userService.serchUserById(Integer.parseInt(request.getParameter("friendId")));
			String feedback=userService.addFriend(userInfo.getUserBasic().getId(),friend.getId());
			if (feedback.equals("true")) {
				userInfo.getFriends().add(friend);
				request.getSession().setAttribute("userInfo", userInfo);
			}
			response.getWriter().write(feedback);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void searchFriends(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		UserInfo userInfo=(UserInfo) request.getSession().getAttribute("userInfo");
		List<User> friends=userInfo.getFriends();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd mm:hh:ss").create();
		String json=gson.toJson(friends);
		response.getWriter().write(json);
	}
	
	protected void deleteFriend(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		UserService userService=new UserServiceImpl();
		UserInfo userInfo=(UserInfo) request.getSession().getAttribute("userInfo");
		
		try {
			User friend=userService.serchUserById(Integer.parseInt(request.getParameter("friendId")));
			String feedback=userService.deleteFriend(userInfo.getUserBasic().getId(), friend.getId());
			Iterator<User> iterator=userInfo.getFriends().iterator();
			while(iterator.hasNext()){
				User oldFriend=iterator.next();
				if (oldFriend.getId()==friend.getId()) {
					iterator.remove();
				}
			}
			response.getWriter().write(feedback);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void update(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		UserService userService=new UserServiceImpl();
		UserInfo userInfo=(UserInfo) request.getSession().getAttribute("userInfo");
		User user=userInfo.getUserBasic();
		String fieldName=request.getParameter("field");
		String fieldValue=request.getParameter("value");
		try {
			CRUD.set(user, fieldName, fieldValue);
			String[] fields={fieldName};
			String feedback=userService.update(user, fields);
			response.getWriter().write(feedback);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	protected void isFriend(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String friendAccount=request.getParameter("friendAccount");
		UserInfo userInfo=(UserInfo) request.getSession().getAttribute("userInfo");
		List<User> friends=userInfo.getFriends();
		boolean flag=false;
		for(User friend:friends){
			if (friend.getAccount().equals(friendAccount)) {
				flag=true;
			}
		}
		if (flag) {
			response.getWriter().write("true");
		}else{
			response.getWriter().write("false");
		}
	}
}
