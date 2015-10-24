package com.swiftly.server;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import javax.ws.rs.Produces;

import java.io.Serializable;
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
	}
	

	/*
	 * Access via a GET request to /api/runs. Returns a JSON-serialized
	 * list of all the Run instances in our database (for debugging
	 * purposes).
	 * 
	 * TODO remove this before we release our app publicly.
	 */
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
	
	/*
	 * Access via a POST request to /api/runs
	 * 
	 * Creates a new Run entry in our database with fields as specified 
	 * in the POST request's body.  
	 * 
	 * The request body should consist of JSON describing
	 * the run's fields, e.g. {"userId": 300}). Note that 
	 * Jackson autogenerates the primary key (id) for each run
	 * object so you can also include an id field in the JSON.
	 */
	@POST	
	@Consumes("application/json")
	@Produces("application/json")
	public String post(Run run) throws SQLException, JsonProcessingException
	{
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Long id = (Long) session.save(run);
		run.setId(id);
		
		
		session.getTransaction().commit();
		session.close();

		return "{id: " + run.getId() + "}";
	}
	
}
