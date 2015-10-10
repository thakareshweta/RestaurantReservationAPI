package org.shweta.rest.controllers;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.shweta.dao.MenuDao;
import org.shweta.dao.ReservationDAO;
import org.shweta.model.Menu;
import org.shweta.model.Reservation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import orrg.shweta.exceptions.AppException;

@Path("/menus")
@Api(tags = {"/menus"})//this is a swagger annotation to let the swagger know our path
public class MenuController {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation( // again from swagger which lets you write a small desc. about operation/method
			value = "Find all menu detionsscrip",
			notes = "This function fetches the data from menu table of mysql database "
			)
	@ApiResponses( value = {
		@ApiResponse (code = 200 ,message = "Success")	,
		@ApiResponse (code = 500 , message = "Internal Server Error")
	})
	public List<Menu> getAllMenu() {
		//return "finding all";
		MenuDao dao = new MenuDao();
		try {
			return dao.getAllMenus();
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}
	}
}
