package com.swiftly.server;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.fasterxml.jackson.core.JsonProcessingException;

@Path("/allruns")
public class AllRunsResource extends BaseResource 
{
	public AllRunsResource() throws ClassNotFoundException, SQLException {
		super();
	}
	
	
	/*
	 * Access via a GET request to /api/allruns. 
	 * 
	 * TODO remove this before we release our app publicly.
	 */
	@GET
    public String get() throws JsonProcessingException, SQLException
    {
		System.out.println("GET Request to /allruns");
		return this.mapper.writeValueAsString("GET Request to /allruns");
    }
	
	
	/*
	 * Access via a POST request to /api/allruns
	 * 
	 * Returns the runs corresponding to the given userId specified 
	 * in the JSON object passed in the POST request's body. 
	 * 
	 * The request body should be a JSON object specifying the userID, 
	 * e.g {"id": "2"}. 
	 */
	@POST	
	@Consumes("application/json")
	@Produces("application/json")
	public String post(Runner runner) throws SQLException, JsonProcessingException
	{
		// Start transaction
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		// Retrieve runs that correspond to this runner
		List<Run> all_runs = session.createCriteria(Run.class)
				.add(Restrictions.eq("userId",runner.getId()))
				.list();
		
		// End transaction
		session.getTransaction().commit();
		session.close();
		
		// Write as json string and return
		String jsonString = this.mapper.writeValueAsString(all_runs);
		return jsonString;
	}

}
