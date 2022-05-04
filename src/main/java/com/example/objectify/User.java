package com.example.objectify;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.OnLoad;

@Entity
public class User {

	@Id
	private Long id;
	private String name;
	@Index
	private String email;
	private String mobile;
	@Index
	private int age;
	
	@Load
	Ref<Address> address;

	public User() {
		
	}

	public User(String name, String email, String mobile, int age) {
		this.setName(name);
		this.setEmail(email);
		this.setMobile(mobile);
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@OnLoad
	public Ref<Address> getAddress() {
		return address;
	}  
	
	public void setAddress(Ref<Address> address) {
		this.address = address;
	}
	
	@Override
	public String toString() {
		return "Name:"+ name +" Email:"+email+" Mobile:"+mobile+" age:"+age;
	}

}
