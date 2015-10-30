package com.swiftly.server;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Runner implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String userName;
	private String firstName;
	private String lastName;
	private int age;
	private int height;
	private int weight;
	
	public Runner() {
		
	}
	
	public Runner(String userName) {
		this.userName = userName;
	}
	
	public Long getUserId() {
		return id;
	}
	
	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public int getAge() {
		return age;
	}
	
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
	}


	public int getWeight() {
		return weight;
	}


	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	@Override
	public String toString() {
		return new StringBuffer("{'id' : ").append(this.id)
                .append(", 'userName : ").append(this.userName)
                .append(", 'firstName' : ").append(this.firstName)
                .append(", 'lastName' : ").append(this.lastName)
                .append(", 'age' : ").append(this.age)
                .append(", 'height' : ").append(this.height)
                .append(", 'weight' : ").append(this.weight)
                .toString();
	}
	
}
