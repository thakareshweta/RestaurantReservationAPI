package org.shweta.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.shweta.model.Reservation;
import org.shweta.model.Restaurant;
import org.shweta.utils.DBUtils;

import orrg.shweta.exceptions.AppException;

public class RestaurantDAO {
	/* Description: This function returns the restaurant details 
	 * Input: NA
	 * Output: Restaurant object
	 * */
	public Restaurant getRestaurantDetails() throws AppException{	
		Connection con = DBUtils.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Restaurant restaurant = new Restaurant();
		try {
			ps = con.prepareStatement("SELECT * FROM RestaurantDetails where ID = 1");
			rs = ps.executeQuery();
			if (rs.next()) {				
				restaurant.setID(rs.getInt("ID"));
				restaurant.setName(rs.getString("NAME"));
				restaurant.setContact(rs.getString("CONTACT"));
				restaurant.setAddress1(rs.getString("ADDRESS1"));
				restaurant.setCity(rs.getString("CITY"));
				restaurant.setState(rs.getString("STATE"));
				restaurant.setCountry(rs.getString("COUNTRY"));
				restaurant.setAutoAssign(rs.getBoolean("AUTOASSIGN"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException(e.getMessage(), e.getCause());
		} finally {
			DBUtils.closeResource(ps, rs, con);
		}
		return restaurant;
	}
}
