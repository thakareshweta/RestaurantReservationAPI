package org.shweta.rest.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import org.shweta.dao.RestaurantDAO;
import org.shweta.model.Restaurant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import orrg.shweta.exceptions.AppException;
@Path("/restaurantProfile")
@Api(tags = {"/restaurantProfile"})
public class RestaurantController {

	/* Description: This function returns details of the  restaurant record .
	 * Input: NA
	 * Output: Details of Restaurant record in JSON format
	 * */	
 
	@GET//To resolve the above get from this one use Path
	@Produces(MediaType.APPLICATION_JSON)
    @ApiOperation( // again from swagger which lets you write a small description. about operation/method
			value = "Get restaurant Details",
			notes = "This function returns details of the  restaurant in JSON format."
			)
	@ApiResponses( value = {
		@ApiResponse (code = 200 ,message = "Success")	,
		@ApiResponse (code = 500 , message = "Internal Server Error"),
		@ApiResponse (code = 404 , message = "Not found")
	})
    public Restaurant getRestaurantProfile(){
		Restaurant rest = new Restaurant();
		RestaurantDAO dao = new RestaurantDAO();
		try {
			rest = dao.getRestaurantDetails();
			if(rest==null){
				throw new WebApplicationException(Status.NOT_FOUND);
			}
		} catch (AppException e) {
			
			e.printStackTrace();
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}
		return rest;
	}
}
