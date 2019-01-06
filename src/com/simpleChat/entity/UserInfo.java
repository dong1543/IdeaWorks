package com.simpleChat.entity;

import java.util.ArrayList;
import java.util.List;

import com.sun.javafx.collections.MappingChange.Map;

public class UserInfo {
	private User userBasic;
	private List<User> friends;
	public UserInfo() {
		this.userBasic=new User();
		this.friends=new ArrayList<>();
	}
	public User getUserBasic() {
		return userBasic;
	}
	public void setUserBasic(User userBasic) {
		this.userBasic = userBasic;
	}
	public List<User> getFriends() {
		return friends;
	}
	public void setFriends(List<User> friends) {
		this.friends = friends;
	}

}
