package com.swiftly.server;

import javax.servlet.ServletException;
import javax.ws.rs.core.Application;

import io.undertow.Undertow;
import io.undertow.servlet.api.DeploymentInfo;

import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;
import java.sql.*;

public class SwiftlyServer {
	private final UndertowJaxrsServer server = new UndertowJaxrsServer();
	
	public SwiftlyServer(String host, Integer port)
	{
		Undertow.Builder serverBuilder = Undertow.builder().addHttpListener(port, host);
		server.start(serverBuilder);
	}
	
	public DeploymentInfo deployApplication(String path, Class<? extends Application> appClass)
	{
		ResteasyDeployment deployment = new ResteasyDeployment();
		deployment.setApplicationClass(appClass.getName());
		return server.undertowDeployment(deployment, path);
	}
	
	public void deploy(DeploymentInfo deploymentInfo) throws ServletException 
	{
		server.deploy(deploymentInfo);
	}
	
	public static void main(String[] args) throws ServletException, ClassNotFoundException, SQLException 
	{
		// Access resources at localhost:8080/api/resource_name
		SwiftlyServer swiftlyServer = new SwiftlyServer("0.0.0.0", 8080);
		DeploymentInfo deploymentInfo = swiftlyServer.deployApplication("/", SwiftlyApp.class);
		
		deploymentInfo.setClassLoader(SwiftlyServer.class.getClassLoader());
		deploymentInfo.setDeploymentName("Swiftly");
		deploymentInfo.setContextPath("/api");
		
		swiftlyServer.deploy(deploymentInfo);
	}

}
