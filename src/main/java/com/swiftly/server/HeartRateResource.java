package com.swiftly.server;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/hr")
public class HeartRateResource 
{
	// Found at /swiftly/app/hr
	@GET
    @Produces("text/plain")
    public String get()
    {
		System.out.println("GET Request");
		return "Hello, welcome to swiftly.";
    }
	
	// Found at /swiftly/app/hr/{value}
	@PUT
	@Produces("text/plain")
	@Path("/{value}")
	public String put(@PathParam("value") String value)
	{
		System.out.println("PUT Request: " + value);
		return "Received data: " + value;
		
	}
	
}
