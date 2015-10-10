/*
 * ReservationDAO provides interaction with database and performs CRUD operations on Reservation table in database.
 * */
package org.shweta.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.shweta.model.Reservation;
import org.shweta.utils.DBUtils;

import orrg.shweta.exceptions.AppException;

public class ReservationDAO {

	/* Description: This function returns List of all reservations from the reservations Table.
	 * Input: NA
	 * Output: List of all reservations 
	 * */
	public List<Reservation> getAllReservations() throws AppException{	
		List<Reservation> resList = new ArrayList<Reservation>();
		Connection con = DBUtils.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("SELECT * FROM Reservation");
			rs = ps.executeQuery();

			while (rs.next()) {
				Reservation res = new Reservation();
				res.setId(rs.getInt("ID"));
				res.setCustomerFname(rs.getString("CUSTOMERFNAME"));
				res.setCustomerLname(rs.getString("CUSTOMERLNAME"));
				res.setPhone(rs.getString("PHONE"));
				res.setEmail(rs.getString("EMAIL"));
				res.setReservationDate(rs.getString("RESERVATIONDATE"));
				res.setReservationTime(rs.getString("RESERVATIONTIME"));
				res.setPartysize(rs.getInt("PARTYSIZE"));
				res.setTableNR(rs.getInt("TABLENR"));
				res.setStatus(rs.getString("STATUS"));
				resList.add(res);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException(e.getMessage(), e.getCause());
		} finally {
			DBUtils.closeResource(ps, rs, con);
		}

		return resList;
	}
	/* Description: This function returns details of matching reservation record from the reservations Table.
	 * Input: Reservation ID
	 * Output: Details of Reservation record 
	 * */		
    public Reservation getOneReservation(int id) throws AppException{
	    Reservation res = new Reservation();
	    int resId = id;
	    System.out.println("ID is "+ resId);
	    Connection con = DBUtils.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("SELECT * FROM Reservation WHERE ID=?");
			ps.setInt(1, resId);

			rs = ps.executeQuery();

			if (rs.next()) {
				res.setId(rs.getInt("ID"));
				res.setCustomerFname(rs.getString("CUSTOMERFNAME"));
				res.setCustomerLname(rs.getString("CUSTOMERLNAME"));
				res.setPhone(rs.getString("PHONE"));
				res.setEmail(rs.getString("EMAIL"));
				res.setReservationDate(rs.getString("RESERVATIONDATE"));
				res.setReservationTime(rs.getString("RESERVATIONTIME"));
				res.setPartysize(rs.getInt("PARTYSIZE"));
				res.setTableNR(rs.getInt("TABLENR"));
				res.setStatus(rs.getString("STATUS"));
		}
		else
		{
			throw new AppException("Reservation with ID "+resId+" not found!");
		}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException(e.getMessage(), e.getCause());
		} finally {
			DBUtils.closeResource(ps, rs, con);
		}		
		return res;

   }
	/* Description: This function creates reservation record in the database and returns the created record in JSON format.
	 * Input: Reservation object
	 * Output: Details of created reservation record (object)
	 * */	       
	public Reservation createReservation(Reservation res) throws AppException {
		Connection con = DBUtils.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("INSERT INTO Reservation (CUSTOMERFNAME, CUSTOMERLNAME, PHONE, EMAIL, RESERVATIONDATE, RESERVATIONTIME, PARTYSIZE, TABLENR,STATUS) VALUES (?,?,?,?,?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, res.getCustomerFname());	
			ps.setString(2, res.getCustomerLname());			
			ps.setString(3, res.getPhone());
			ps.setString(4, res.getEmail());
			ps.setString(5, res.getReservationDate());	
			ps.setString(6, res.getReservationTime());	
			ps.setInt(7, res.getPartysize());			
			ps.setInt(8, res.getTableNR());	
			ps.setString(9,res.getStatus());			
			
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();

			if (rs.next()) {
				res.setId(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException(e.getMessage(), e.getCause());
		} finally {
			DBUtils.closeResource(ps, rs, con);
		}       
		return res;
	}
	
    /* Description: This function updates reservation record in the database and returns the updated record.
	 * Input: Reservation object with updated values
	 * Output: Details of updated reservation object
	 * */   	
	public Reservation updateReservation(Reservation res) throws AppException {
		Connection con = DBUtils.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("Update Reservation set CUSTOMERFNAME=?, CUSTOMERLNAME=?, PHONE=?, EMAIL=?, RESERVATIONDATE=?, RESERVATIONTIME=?, PARTYSIZE=?, TABLENR=?, STATUS=? Where ID =?");
			ps.setString(1, res.getCustomerFname());	
			ps.setString(2, res.getCustomerLname());			
			ps.setString(3, res.getPhone());
			ps.setString(4, res.getEmail());
			ps.setString(5, res.getReservationDate());	
			ps.setString(6, res.getReservationTime());	
			ps.setInt(7, res.getPartysize());			
			ps.setInt(8, res.getTableNR());	
			ps.setString(9,res.getStatus());			
			
			ps.setInt(10, res.getId());	
			System.out.println(ps.toString());
			
			
			
			int nrOfRowsaffected = ps.executeUpdate();	
			System.out.println("nr of rows afftected are : "+nrOfRowsaffected);
			

			//if (rs.next()) {
				//emp.setId(rs.getInt(1));
			//}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException(e.getMessage(), e.getCause());
		} finally {
			DBUtils.closeResource(ps, rs, con);
		}
       
		return res;
	}
	   /* Description: This function deletes reservation record in the database.
		 * Input: reservation ID 
		 * Output: flag : true if the record is deleted , false: otherwise
		 * */	
	public boolean delete(int resId) throws AppException {
		//Before deleting the record we''ll see if the record exists or not
		Connection con = DBUtils.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Boolean isDeleted = false;
		Reservation res = getOneReservation(resId);
		if(res==null)
		{
			throw new AppException("Reservation with ID "+resId+" not found!");
			
		}else
		{		
		
	
		try {
			ps = con.prepareStatement("DELETE FROM Reservation Where ID =?");
			
			ps.setInt(1, resId);		
			System.out.println(ps.toString());
			int nrOfRowsaffected = ps.executeUpdate();	
			System.out.println("nr of rows afftected are : "+nrOfRowsaffected);
			if (nrOfRowsaffected>0) {
				isDeleted = true;
			}
			else{
				throw new AppException("");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException(e.getMessage(), e.getCause());
		} finally {
			DBUtils.closeResource(ps, rs, con);
		}
		}
       
		return isDeleted;
	}
	
   
}
