package com.swiftly.server;

import java.sql.*;

import com.fasterxml.jackson.databind.ObjectMapper;



import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class BaseResource {
	

//	private static final String host = "swiftly.cxjgjwimiith.us-west-2.rds.amazonaws.com";
//	private static final String port = "5432";
//	private static final String database = "swiftlydb";
//	
//	private static final String url = "jdbc:postgresql://" + host + ":" + port + "/" + database; 
//
//	private static final String username = "friendlymustache";
//	private static final String password = "startedfromthebottomnowhesnand";
//	private static final String driverName = "org.postgresql.Driver";
//	protected Connection conn;
	protected static SessionFactory sessionFactory = new Configuration().configure()
			.buildSessionFactory(); 
	
	
	
	
	protected ObjectMapper mapper = new ObjectMapper();		

	public BaseResource() throws ClassNotFoundException, SQLException {
//		Class.forName(driverName);		
//		this.conn = DriverManager.getConnection(BaseResource.url,
//				BaseResource.username, BaseResource.password);		
	}
	

}




















