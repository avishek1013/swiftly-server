package com.swiftly.server;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import java.io.IOException;

@Path("/runners")
public class RunnerResource extends BaseResource 
{
	public RunnerResource() throws ClassNotFoundException, SQLException {
		super();
	}
	
	
	  public static final MediaType JSON
      = MediaType.parse("application/json; charset=utf-8");

	private static final String APP_SECRET = "d8dee8ee0365843116fe01cf4093175f";

	private static final String APP_KEY = "1664214063861715";
	
	// As of now, the Facebook API returns an app access token as a plaintext
	// response of the format "access_token=<access_token_content>"
	// We want to remove the initial "access_token=" prefix from this
	// response so we store its length as a constant
	private static final int ACCESS_TOKEN_PREFIX_LENGTH = 13;

	  OkHttpClient client = new OkHttpClient();
	
   /*
	* Makes a POST request to the specified URL, sending the
	* specified JSON data in the request body. Returns the response
	* body as a string.
	* 
	* See http://square.github.io/okhttp/ for more info
	*/	  
	String makePostRequest(String url, String json) throws IOException {
	    RequestBody body = RequestBody.create(JSON, json);
	    Request request = new Request.Builder()
	        .url(url)
	        .post(body)
	        .build();
	    Response response = client.newCall(request).execute();
	    return response.body().string();
	}
	
	
	/*
	 * Makes a GET request to the specified URL, returning the response
	 * body as a string 
	 */
	String makeGetRequest(String url) throws IOException {
	    Request request = new Request.Builder()
	        .url(url)
	        .get()
	        .build();
	    Response response = client.newCall(request).execute();
	    return response.body().string();				
	}
	
	public boolean validateToken(String fbAccessToken, String fbUserId) throws IOException {
				
		  // Get an app access token, which we use to validate that the following
		  // user-access-token-validation request is coming from our app
		  // See https://developers.facebook.com/docs/facebook-login/access-tokens#apptokens
		  String app_access_url = "https://graph.facebook.com/oauth/access_token?client_id=" + 
				  APP_KEY + "&client_secret=" + APP_SECRET + 
				  "&grant_type=client_credentials&redirect_uri=none";
		  
		  String appAccessToken = makeGetRequest(app_access_url).substring(ACCESS_TOKEN_PREFIX_LENGTH);
		  
		  System.out.println("App access token response: ");
		  System.out.println(appAccessToken);		  

		  // Verify that the specified Facebook access token belongs to the
		  // user with Facebook id fbUserId 
		  // See https://developers.facebook.com/docs/facebook-login/manually-build-a-login-flow/v2.4#checktoken
		  String validateTokenUrl = "https://graph.facebook.com/debug_token?input_token=" + 
				  fbAccessToken + "&access_token=" + appAccessToken;
		  
		  String authResponse = makeGetRequest(validateTokenUrl);	
		  System.out.println("Authresponse: " + authResponse);
		  
		  // Parse the validation request's response 
		  JsonNode responseJson = mapper.readTree(authResponse);
		  
		  // If the validation request doesn't contain a user ID (if we have
		  // an invalid FB access token, etc), return false.
		  if (!responseJson.has("data") || !responseJson.get("data").has("user_id"))
			  return false;
		  
		  // Get the facebook user ID from the validation request (the FB user ID of the
		  // user to whom the FB access token belongs)
		  String tokenFbId = responseJson.get("data").get("user_id").textValue();
		  
		  // Make sure the access token user ID matches the passed-in FB user id
		  return tokenFbId.equals(fbUserId);
									
	}
	
	/*
	 * Access via a POST request to /api/runners/new
	 * 
	 * Returns the runner id corresponding to the userName specified 
	 * in the JSON object passed in the POST request's body. Creates a new
	 * runner if no runner with the specified username exists.
	 *  
	 * This method also uses Facebook to validate a user's identity.
	 * 
	 * The request body should be a JSON object specifying the username,
	 * user's facebook access token, and user's facebook ID 
	 * e.g 
	 * {
	 * 		"userName": "smurching", 
	 * 		"fbAccessToken" : "CAAXpmAgbT9MBAEW...", 
	 * 		"fbUserId" : "10206208972397823"
	 * } 
	 */
	@POST	
	@Path("/new")
	@Consumes("application/json")
	@Produces("application/json")
	public String handleNewRunner(Runner runner) throws SQLException, HibernateException, IOException
	{
		
		
		System.out.println("Runner fb access token: " + runner.getFbAccessToken()
		 + " and fb user id: " + runner.getFbUserId());
		
		if (validateToken(runner.getFbAccessToken(), runner.getFbUserId())) {
			System.out.println("Creating or getting new runner!");
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			
			// Add code to return proper userId from database here
			List<Runner> runner_list = session.createCriteria(Runner.class)
					.add(Restrictions.eq("userName",runner.getUserName()))
					.list();

			Runner outputRunner;
			// If no users exist with the given username, create a new one
			if (runner_list.size() == 0) {
				outputRunner = runner;			
				outputRunner.setRandomAccessToken();
				Long id = (Long) session.save(outputRunner);
				// Set the ID attribute of our Runner object
				// to the ID generated by our DB.
				outputRunner.setId(id);			
			}
			// Otherwise, return the first user with the specified username
			else {
				outputRunner = runner_list.get(0);
			}
			session.getTransaction().commit();
			session.close();		
			
			
			return "{\"userId\":" + outputRunner.getId() + ", \"accessToken\" : \"" + outputRunner.getAccessToken() + "\"}";
		}
		return "";			
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
		
		System.out.println("Creating or getting new runner!");
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		// Add code to return proper userId from database here
		List<Runner> runner_list = session.createCriteria(Runner.class)
				.add(Restrictions.eq("userName",runner.getUserName()))
				.list();

		Runner outputRunner;
		// If no users exist with the given username, create a new one
		if (runner_list.size() == 0) {
			outputRunner = runner;			
			Long id = (Long) session.save(outputRunner);
			// Set the ID attribute of our Runner object
			// to the ID generated by our DB.
			outputRunner.setId(id);			
		}
		// Otherwise, return the first user with the specified username
		else {
			outputRunner = runner_list.get(0);
		}
		session.getTransaction().commit();
		session.close();		
		
		
		return "{\"userId\":" + outputRunner.getId() + "}";
	}

}
