package com.swiftly.server;

import java.sql.*;

import com.fasterxml.jackson.databind.ObjectMapper;



import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class BaseResource {
	
	protected static SessionFactory sessionFactory = new Configuration().configure()
			.buildSessionFactory(); 
		
	
	protected ObjectMapper mapper = new ObjectMapper();		

	public BaseResource() throws ClassNotFoundException, SQLException {	
	}
	

}




















