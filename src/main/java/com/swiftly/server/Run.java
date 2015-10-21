package com.swiftly.server;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Run implements Serializable {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Long id;
	private Long userId;
	
	public Run() {
		
	}
	
	public Run(Long id, Long userId) {
		this.id = id;
		this.userId = userId;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
	
	 @Override
	     public String toString() {
	         return new StringBuffer("{'id' : ").append(this.id)
	                 .append(", 'userId': ")
	                 .append(this.userId)
	                 .append("}").toString();
	     }
	
	
}
