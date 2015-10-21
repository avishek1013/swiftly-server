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



@Path("/datapoints")
public class DataPointResource extends BaseResource
{
	public DataPointResource() throws ClassNotFoundException, SQLException {
		super();
		System.out.println("Initializing DataPointResource");
	}
	
	
	// Found at /api/datapoints
	@GET
    @Produces("application/json")
    public String get() throws JsonProcessingException, SQLException
    {
		System.out.println("GET Request to /hr, retrieving all datapoints");
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		List<DataPoint> datapoints = session.createCriteria(DataPoint.class).list();
		
		session.getTransaction().commit();
		session.close();
		

		String jsonString = this.mapper.writeValueAsString(datapoints);		
		return jsonString;
    }
	
	// Found at /api/datapoints/
	@POST	
	@Consumes("application/json")
	public Response post(DataPoint point) throws SQLException
	{
		System.out.println("Creating datapoint!");
		String result = "Point created : " + point;
		System.out.println(result);

		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		session.save(point);
		
		
		session.getTransaction().commit();
		session.close();
		
		return Response.status(201).entity(result).build();
	}
	
}
