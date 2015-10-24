package com.swiftly.server;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import javax.ws.rs.Produces;

import java.sql.*;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.Response;

import org.hibernate.Session;

import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;



@Path("/measurements")
public class MeasurementResource extends BaseResource
{
	public MeasurementResource() throws ClassNotFoundException, SQLException {
		super();
	}
	
	
	/*
	 * Access via a GET request to /api/measurements. Returns a JSON-serialized
	 * list of all measurements in our database (for debugging purposes).
	 * 
	 * TODO remove this before we release our app publicly.
	 */
	@GET
    @Produces("application/json")
    public String get() throws JsonProcessingException, SQLException
    {
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		List<Measurement> datapoints = session.createCriteria(Measurement.class).list();
		
		session.getTransaction().commit();
		session.close();
		

		String jsonString = this.mapper.writeValueAsString(datapoints);		
		return jsonString;
    }
	

	/*
	 * Access via a POST request to /api/measurements
	 * 
	 * Creates new measurement entries in our database with 
	 * fields as specified in the POST request's body.  
	 * 
	 * The request body should be an array of JSON objects each
	 * describing a measurement's fields. 
	 */
	@POST	
	@Consumes("application/json")
	public Response post(List<Measurement> points) throws SQLException, JsonProcessingException
	{

		Session session = sessionFactory.openSession();
		for (int i = 0; i < points.size(); i++) {
			session.beginTransaction();			
			session.save(points.get(i));						
			session.getTransaction().commit();
		}
		session.close();
		
		String jsonString = this.mapper.writeValueAsString(points);				
		return Response.status(201).entity(jsonString).build();
	}
	
}
