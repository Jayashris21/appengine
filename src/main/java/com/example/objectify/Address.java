package com.example.objectify;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.Parent;


@Entity
public class Address {

	@Id
	Long id;
	String addressLine1;
	String addressLine2;
	@Index
	String city;
	@Index
	String state;
	String zipcode;
	@Load @Parent
	Ref<User> user;

	public Address() {

	}

	public Address(String addressLine1, String addressLine2, String city, String state, String zipcode) {
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public String getCity() {
		return city;
	}

	public Long getId() {
		return id;
	}

	public String getState() {
		return state;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	@Override
	public String toString() {

		return "AddressLine1:" + addressLine1 + " AddressLine2:" + addressLine2 + " City:" + city + " State:" + state
				+ " Zipcode:" + zipcode + " ID:" + id + " User:" + user;
	}

	public Ref<User> getUser() {
		return user;
	}
	
	public void setUser(Ref<User> user) {
		this.user = user;
	}
}
