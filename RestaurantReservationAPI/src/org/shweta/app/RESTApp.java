package org.shweta.app;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

import io.swagger.jaxrs.config.BeanConfig;


@ApplicationPath("/api")
public class RESTApp extends ResourceConfig{
	public RESTApp(){
		packages("org.shweta.rest");//it tells to look for the jersey implementations inside this package
		//swagger configurations
		register(io.swagger.jaxrs.listing.ApiListingResource.class);//for api listing
		register(io.swagger.jaxrs.listing.SwaggerSerializers.class);//for serialization to Json
		
		BeanConfig config = new BeanConfig();
		config.setBasePath("/RestaurantReservationAPI/api");
		config.setDescription("Restaurant API Demo");
		config.setVersion("1.0");
		config.setSchemes(new String [] {"http"});
		config.setResourcePackage("org.shweta");
		config.setTitle("Restaurant API");
		config.setScan(true);
		
	}
}
 