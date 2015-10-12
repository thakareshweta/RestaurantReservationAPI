package org.shweta.rest.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.shweta.dao.LoginDAO;
import org.shweta.dao.ReservationDAO;
import org.shweta.model.Reservation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import orrg.shweta.exceptions.AppException;
@Path("/owner")
@Api(tags = {"/login"})
public class OwnerController {
	
	
	
	/* Description: This function validates the user, If the email,password pair is correct it returns TRUE else FALSE.
	 * Input: email, password
	 * Output: TRUE: id user is authorized else FALSE
	 * */	
    @GET//To resolve the above get from this one use Path
    @Path("/{email}/{password}")
	@Produces(MediaType.APPLICATION_JSON)
    @ApiOperation( // again from swagger which lets you write a small description. about operation/method
			value = "Check if the user is authorized",
			notes = "This function validates the user, If the email,password pair is correct it returns TRUE else FALSE."
			)
	@ApiResponses( value = {
		@ApiResponse (code = 200 ,message = "Success")	,
		@ApiResponse (code = 500 , message = "Internal Server Error"),
		@ApiResponse (code = 404 , message = "Not found")
	})
    public boolean  isAuthorized(@PathParam("email") String email ,
    							 @PathParam("password") String password ){
		boolean isAuthorized = false;
		LoginDAO dao = new LoginDAO();
		try {
			isAuthorized = dao.isAuthorized(email, password);
		} catch (AppException e) {
			
			e.printStackTrace();
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}
		return isAuthorized;
	}
}
