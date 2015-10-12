/*
 * ReservationController provides various methods in order to perform CRUD operation on Reservation Record.
 * It calls methods from ReseraationDAO and performs operations on reservation table in the restaurant_db. 
 */

package org.shweta.rest.controllers;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.shweta.dao.ReservationDAO;
import org.shweta.dao.RestaurantDAO;
import org.shweta.dao.TableDAO;
import org.shweta.model.Reservation;
import org.shweta.model.Restaurant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;
import orrg.shweta.exceptions.AppException;

@Path("/reservations")
@Api(tags = {"/reservations"})//this is a swagger annotation to let the swagger know application path
public class ReservationController {
	
	/* Description: This function returns List of all reservations from the reservations Table.
	 * Input: NA
	 * Output: List of all reservations in JSON format
	 * */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation( // again from swagger which lets you write a small description about operation/method
			value = "Get all reservations",
			notes = "This function fetches all reservations from database in JSON format"
			)
	@ApiResponses( value = {
		@ApiResponse (code = 200 ,message = "Success")	,
		@ApiResponse (code = 500 , message = "Internal Server Error")
	})
	public List<Reservation> getAllReservations() {
		//return "finding all";
		ReservationDAO dao = new ReservationDAO();
		try {
			return dao.getAllReservations();
		} catch (AppException e) {			
			e.printStackTrace();
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}
	}

	
	/* Description: This function returns details of matching reservation record from the reservations Table.
	 * Input: Reservation ID
	 * Output: Details of Reservation record in JSON format
	 * */	
    @GET//To resolve the above get from this one use Path
    @Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
    @ApiOperation( // again from swagger which lets you write a small description. about operation/method
			value = "Get matching reservation",
			notes = "This function gets the matching reservation record from database in JSON format"
			)
	@ApiResponses( value = {
		@ApiResponse (code = 200 ,message = "Success")	,
		@ApiResponse (code = 500 , message = "Internal Server Error"),
		@ApiResponse (code = 404 , message = "Not found")
	})
    public Reservation getOneReservation(@PathParam("id") int resId ){
		Reservation res = new Reservation();
		ReservationDAO dao = new ReservationDAO();
		try {
			res = dao.getOneReservation(resId);
			if(res==null){
				throw new WebApplicationException(Status.NOT_FOUND);
			}
		} catch (AppException e) {
			
			e.printStackTrace();
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}
		return res;
	}
	/* Description: This function creates reservation record in the database and returns the created record in JSON format.
	 *              First check the AutoassignFlag 
	 *              1. AutoAssign is True  : a. Table is available - Assign the table and make the reservation
	 *              2. AutoAssign is True  : b. Table is unavailable - Confirm with customer whether he should be added to the waiting list
	 *              3. AutoAssign is FALSE : Confirm with customer whether he should be added to the waiting list
	 * Input: Reservation Record in JSON format
	 * Output: Details of created reservation record in JSON format
	 * */	    
    
    @POST
    @ApiOperation( // again from swagger which lets you write a small description. about operation/method
			value = "Create a reservation",
			notes = "This function creates a record for reservation in database"
			)
	@ApiResponses( value = {
		@ApiResponse (code = 200 ,message = "Success")	,
		@ApiResponse (code = 500 , message = "Internal Server Error")
	})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Reservation createReservation(Reservation res){
    	ReservationDAO dao = new ReservationDAO();
    	RestaurantDAO restDao = new RestaurantDAO();
    	TableDAO tabledao = new TableDAO();
    	int tableNR =0;
    	Restaurant restaurant = new Restaurant();
    	
    	try {
    	restaurant =  restDao.getRestaurantDetails();
    	
    	if(!restaurant.isAutoAssign())
    	{
    		//depends on the customer response
    		
    		res.setStatus("Waiting");
    		return res;
    	}
    	
    		/*Check if the table is available 
    		 * if yes set the table nr and confirm the reservation else set the status as "Waiting"
    		 */
    		tableNR = tabledao.getAvailableTableID(res.getReservationDate(),res.getReservationDate(), res.getPartysize());
    		if(tableNR ==0)
    		{
    				res.setStatus("Waiting");
    				return res;
    		} 
    		res.setStatus("booked");
    		res.setTableNR(tableNR);
    		res = dao.createReservation(res);
			if(res==null)
			{
				throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
			}
		} catch (AppException e) {
			
			e.printStackTrace();
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}
    	
    	return res;
    	
	}

	/* Description: This function reschedules a reservation by updating the date/time in the database and returns the updated record in JSON format.
	 *              First check the AutoassignFlag 
	 *              1. AutoAssign is True  : a. Table is available - Assign the table and update the reservation
	 *              2. AutoAssign is True  : b. Table is unavailable - Confirm with customer whether he should be added to the waiting list
	 *              3. AutoAssign is FALSE : Confirm with customer whether he should be added to the waiting list
	 * Input: reservation ID, reservationDate,reservationTime, partySize
	 * Output: Details of updated reservation record in JSON format
	 * */	      
    @PUT
    @Path("/reschedule/{id}/{reservationDate}/{reservationTime}/{partySize}")
    @ApiOperation( // again from swagger which lets you write a small description. about operation/method
			value = "Reschedule a reservation",
			notes = "This function updates the date/time of a record for reservation in database"
			)
	@ApiResponses( value = {
		@ApiResponse (code = 200 ,message = "Success")	,
		@ApiResponse (code = 500 , message = "Internal Server Error")
	})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Reservation rescheduleReservation(@PathParam("id") int resId , 
			                                 @PathParam("reservationDate") String reservationDate,
											 @PathParam("reservationTime") String reservationTime,
											 @PathParam("partySize") int partySize){
    	ReservationDAO dao = new ReservationDAO();
    	Reservation res = new Reservation();
    	TableDAO tabledao = new TableDAO();
    	int tableNR =0;
    	Restaurant restaurant = new Restaurant();
    	RestaurantDAO restDao = new RestaurantDAO();
    	try {
    		restaurant =  restDao.getRestaurantDetails();
    	
    		res = dao.getOneReservation(resId); 
    		res.setReservationDate(reservationDate);
    		res.setReservationTime(reservationTime);
    		res.setPartysize(partySize);
    		if(!restaurant.isAutoAssign())
    		{
    			//depends on the customer response
    			res.setStatus("Waiting");
    			return res;
    		}
    	
    		/*Check if the table is available 
    		 * if yes set the table nr and confirm the reservation else set the status as "Waiting"
    		 */
    		tableNR = tabledao.getAvailableTableID(res.getReservationDate(),res.getReservationTime(), res.getPartysize());
    		System.out.println("availble table nr is " + tableNR);
    		if(tableNR ==0)
    		{
    				res.setStatus("Waiting");
    				res.setTableNR(0);
    				return res;
    		} 
    		res.setStatus("booked");
    		res.setTableNR(tableNR);
    		res = dao.updateReservation(res);
			if(res==null)
			{
				throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
			}
		} catch (AppException e) {
			
			e.printStackTrace();
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}
    	
    	return res;
    	
	}
    
    
	/* Description: This function updates the contact information in the database and returns the updated record in JSON format.
	 * Input: reservation ID , firstName , LastName,phone, Email
	 * Output: Details of updated reservation record in JSON format
	 * */	      
    @PUT
    @Path("/updateContact/{id}/{firstName}/{lastName}/{phone}/{email}")
    @ApiOperation( // again from swagger which lets you write a small description. about operation/method
			value = "Reschedule a reservation",
			notes = "This function updates the date/time of a record for reservation in database"
			)
	@ApiResponses( value = {
		@ApiResponse (code = 200 ,message = "Success")	,
		@ApiResponse (code = 500 , message = "Internal Server Error")
	})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Reservation updateContactInformation(@PathParam("id") int resId , 
			                                 @PathParam("firstName") String firstName,
											 @PathParam("lastName") String lastName,
											 @PathParam("phone") String phone,
											 @PathParam("email") String email){
    	ReservationDAO dao = new ReservationDAO();
    	Reservation res = new Reservation();
    	
    	try{
    		res = dao.getOneReservation(resId); 
    		res.setCustomerFname(firstName);
    		res.setCustomerLname(lastName);
    		res.setPhone(phone);
    		res.setEmail(email);
    
    		res = dao.updateReservation(res);
			if(res==null)
			{
				throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
			}
		} catch (AppException e) {
			
			e.printStackTrace();
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}
    	
    	return res;
    	
	}
    
    /* Description: This function updates reservation record in the database and returns the updated record in JSON format.
	 * Input: Reservation Record with updated values
	 * Output: Details of updated reservation record in JSON format
	 * */   
    @PUT
    @ApiOperation( // again from swagger which lets you write a small desc. about operation/method
			value = "Updates Reservation Record",
			notes = "This function updates a record for reservation in database"
			)
	@ApiResponses( value = {
		@ApiResponse (code = 200 ,message = "Success")	,
		@ApiResponse (code = 500 , message = "Internal Server Error")
	})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Reservation update(Reservation res){
    	ReservationDAO dao = new ReservationDAO();
    	
    	try {
			res = dao.updateReservation(res);
			//if(res==null)
			//{
				//throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
			//}
		} catch (AppException e) {
			
			e.printStackTrace();
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}
    	
    	return res;	
	}
    
    /* Description: This function cancels reservation and returns the updated record in JSON format.
	 * Input: Reservation Record with status as cancel.
	 * Output: Details of cancelled reservation record in JSON format
	 * */   
    @PUT
    @Path("/{id}")
    @ApiOperation( // again from swagger which lets you write a small desc. about operation/method
			value = "cancels Reservation Record",
			notes = "This function cancels the reservation i.e. updates a reservation status as cancel in database"
			)
	@ApiResponses( value = {
		@ApiResponse (code = 200 ,message = "Success")	,
		@ApiResponse (code = 500 , message = "Internal Server Error")
	})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Reservation cancel(@PathParam("id") int resId ){
    	Reservation res = new Reservation();
    	ReservationDAO dao = new ReservationDAO();
    	
    	try {
			res = dao.getOneReservation(resId);
    		if(res==null)
    		{
    			System.out.println("Reservation not found for the reservation id ");
    			return res;
    		}
    		res = dao.cancelReservation(res);
			//if(res==null)
			//{
				//throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
			//}
		} catch (AppException e) {
			
			e.printStackTrace();
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}
    	
    	return res;	
	}
    
    /* Description: This function deletes reservation record in the database.
	 * Input: reservation ID 
	 * Output: Response status
	 * */ 
    @DELETE
    @Path("/{Id}")
    @ApiOperation( // again from swagger which lets you write a small desc. about operation/method
			value = "Delete a reservation Record",
			notes = "This function deletes the reservation record from the database"
			)
	@ApiResponses( value = {
		@ApiResponse (code = 200 ,message = "Success")	,
		@ApiResponse (code = 500 , message = "Internal Server Error"),
		@ApiResponse (code = 204 , message = "Record deleted"),
		@ApiResponse (code = 404 , message = "Record not present in database")
	})
	public Response delete(@PathParam("Id") int Id){
    		ReservationDAO dao = new ReservationDAO();
    	boolean isDeleted = false;
    	try {
			isDeleted = dao.delete(Id);
			if(!isDeleted)
			{
				throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
			}
		} catch (AppException e) {
			
			e.printStackTrace();
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}
    	
    	return Response.status(Status.OK).build();
    	

	}
}
