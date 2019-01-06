package com.simpleChat.entity;

public class User {
	private int id;
	private String account;
	private String password;
	private String name;
	private int age;
	private String gender;
	private String city;
	private boolean unreadFlag=false;
	public boolean isUnreadFlag() {
		return unreadFlag;
	}
	public void setUnreadFlag(boolean unreadFlag) {
		this.unreadFlag = unreadFlag;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

}
