package com.swiftly.server;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.fasterxml.jackson.core.JsonProcessingException;

@Path("/runners")
public class RunnerResource extends BaseResource 
{
	public RunnerResource() throws ClassNotFoundException, SQLException {
		super();
	}
	
	
	/*
	 * Access via a GET request to /api/runners. Returns a JSON-serialized
	 * list of all the User instances in our database (for debugging
	 * purposes).
	 * 
	 * TODO remove this before we release our app publicly.
	 */
	@GET
    @Produces("application/json")
    public String get() throws JsonProcessingException, SQLException
    {
		System.out.println("GET Request to /runners, retrieving all users");
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		List<Runner> users = session.createCriteria(Runner.class).list();
		
		session.getTransaction().commit();
		session.close();
		

		String jsonString = this.mapper.writeValueAsString(users);		
		return jsonString;
    }
	
	
	/*
	 * Access via a POST request to /api/runners
	 * 
	 * Returns the runner id corresponding to the given userName specified 
	 * in the JSON object passed in the POST request's body. 
	 * 
	 * The request body should be a JSON object specifying the username, 
	 * e.g {"userName": "adutta"}. 
	 */
	@POST	
	@Consumes("application/json")
	@Produces("application/json")
	public String post(Runner runner) throws SQLException, JsonProcessingException
	{
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		// Add code to return proper userId from database here
		List<Runner> runner_list = session.createCriteria(Runner.class)
				.add(Restrictions.eq("userName",runner.getUserName()))
				.list();
		
		session.getTransaction().commit();
		session.close();
		return "{\"userId\":" + runner_list.get(0).getId() + "}";
	}

}
