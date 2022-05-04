package com.example.elasticsearch;

public class User {
	
	private String name;
	private String age;
	private String gender;
	private String phone;
	private String address;
	
	public User(String name, String age, String gender, String phone, String address) {
		
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.phone = phone;
		this.address = address;
		
	}
	
	public String getAddress() {
		return address;
	}
	public String getAge() {
		return age;
	}
	public String getGender() {
		return gender;
	}
	public String getName() {
		return name;
	}
	public String getPhone() {
		return phone;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	

}
