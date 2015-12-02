package com.swiftly.server;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Application;

public class SwiftlyApp extends Application{
		
	@Override
    public Set<Class<?>> getClasses()
    {
       HashSet<Class<?>> resources = new HashSet<Class<?>>();
       resources.add(MeasurementResource.class);
       resources.add(RunResource.class);
       resources.add(RunnerResource.class);
       resources.add(AllRunsResource.class);
       return resources;
    }
}
