package com.swiftly.server;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import javax.ws.rs.Produces;

import java.sql.*;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.Response;

import org.hibernate.Session;

import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;



@Path("/runs")
public class RunResource extends BaseResource
{
	public RunResource() throws ClassNotFoundException, SQLException {
		super();
		System.out.println("Initializing RunResource");
	}
	
	
	// Found at /api/runs
	@GET
    @Produces("application/json")
    public String get() throws JsonProcessingException, SQLException
    {
		System.out.println("GET Request to /runs, retrieving all runs");
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		List<Run> runs = session.createCriteria(Run.class).list();
		
		session.getTransaction().commit();
		session.close();
		

		String jsonString = this.mapper.writeValueAsString(runs);		
		return jsonString;
    }
	
	// Found at /api/runs/
	@POST	
	@Consumes("application/json")
	public Response post(Run run) throws SQLException
	{
		System.out.println("Creating run!");
		String result = "Run created : " + run;
		System.out.println(result);

		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		session.save(run);
		
		
		session.getTransaction().commit();
		session.close();
		
		return Response.status(201).entity(result).build();
	}
	
}
