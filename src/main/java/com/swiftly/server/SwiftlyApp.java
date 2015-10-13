package com.swiftly.server;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Application;

public class SwiftlyApp extends Application{
	
	@Override
    public Set<Class<?>> getClasses()
    {
       HashSet<Class<?>> resources = new HashSet<Class<?>>();
       resources.add(HeartRateResource.class);
       return resources;
    }
}