
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
import org.shweta.model.Table;
import org.shweta.utils.DBUtils;

import orrg.shweta.exceptions.AppException;

public class TableDAO {
	/* Description: This function returns List of all tables from the TablesDetails Table.
	 * Input: NA
	 * Output: List of all tables 
	 * */
	public List<Table> getAllTables() throws AppException{
		
		List<Table> tableList = new ArrayList<Table>();

		Connection con = DBUtils.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("SELECT * FROM TableDetails");
			rs = ps.executeQuery();

			while (rs.next()) {
				Table  table = new Table();
				table.setID(rs.getInt("ID"));
				table.setSize(rs.getInt("TABLESIZE"));
				tableList.add(table);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException(e.getMessage(), e.getCause());
		} finally {
			DBUtils.closeResource(ps, rs, con);
		}
    	return tableList;
	}

	/* Description: This function returns List of tables for given partysize from the TablesDetails Table.
	 * Input: partySize
	 * Output: List of all tables for a given partySize
	 * */
	public List<Table> getAllPartySizeTables(int partySize) throws AppException{
		
		List<Table> tableList = new ArrayList<Table>();
		if((partySize%2)==0)
		{
			partySize = partySize+2;
		}
		else
		{
			partySize = partySize+3;
		}
		Connection con = DBUtils.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("SELECT * FROM TableDetails where tablesize between ? and ?  order by tableSize;");
			ps.setInt(1, partySize-2);
			ps.setLong(2, partySize);
			rs = ps.executeQuery();

			while (rs.next()) {
				Table  table = new Table();
				table.setID(rs.getInt("ID"));
				table.setSize(rs.getInt("TABLESIZE"));
				tableList.add(table);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException(e.getMessage(), e.getCause());
		} finally {
			DBUtils.closeResource(ps, rs, con);
		}
    	return tableList;
	}
	
	/* Description: This function returns List of all available tables from the TablesDetails Table.
	 * Input: Date Time and party Size 
	 * Output: List of all available tables 
	 * */
	public List<Table> getAvailableTables(String date , String time , int partySize) throws AppException{
		List<Table> tableList = null;
		List<Table> availableTables = new ArrayList<Table>();
		String status = "booked";
		TableDAO dao = new TableDAO();
		Connection con = DBUtils.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			tableList = dao.getAllTables();
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		for(Table table:tableList)
		{
			System.out.println("table ID is  " + table.getID());
			try {
				ps = con.prepareStatement("SELECT Count(*) AS COUNT FROM reservation WHERE RESERVATIONDATE = ? AND  RESERVATIONTIME <= AddTime(? , '01:00:00') AND RESERVATIONTIME >= subtime(? ,'1:00:00') AND TABLENR = ?"
						+ " AND STATUS = ? ");
				ps.setString(1, date);
				ps.setString(2, time);
				ps.setString(3, time);
				ps.setInt(4, table.getID());
				ps.setString(5,  status);
				System.out.println(ps.toString());

				rs = ps.executeQuery();

				if (rs.next()) {
					if(rs.getInt("COUNT")>0)
					{
						System.out.println("Table is not available");
					}
					else
					{
						availableTables.add(table);
					}
				
		    	//return availableTables;
				}//end if
			} catch (SQLException e) {
				e.printStackTrace();
				throw new AppException(e.getMessage(), e.getCause());
			} finally {
				//DBUtils.closeResource(ps, rs, con);
			}		
		}//end of for 
		
		DBUtils.closeResource(ps, rs, con);
		return availableTables;		
	}
	
	
	
	/* Description: This function returns tableID of the available table.
	 * Input: Date Time and party Size 
	 * Output: table ID if the table is available else 0.
	 * */
	public int getAvailableTableID(String date , String time , int partySize) throws AppException{
		List<Table> tableList = null;
		int availableTableID = 0;
		String status = "booked";
		TableDAO dao = new TableDAO();
		Connection con = DBUtils.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			tableList = dao.getAllPartySizeTables(partySize);
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		
		for(Table table:tableList)
		{
			
			System.out.println("table ID is  " + table.getID());
			try {
				ps = con.prepareStatement("SELECT Count(*) AS COUNT FROM reservation WHERE RESERVATIONDATE = ? AND  "
						+ "RESERVATIONTIME <= AddTime(? , '01:00:00') AND RESERVATIONTIME >=subtime(? ,'1:00:00') AND TABLENR = ?"
						+ " AND STATUS = ?");
				ps.setString(1, date);
				ps.setString(2, time);
				ps.setString(3, time);
				ps.setInt(4, table.getID());
				ps.setString(5,  status);
				rs = ps.executeQuery();
				if (rs.next()) {
					System.out.println("THe count for table id is availableTableID  "+rs.getInt("COUNT"));
					if(rs.getInt("COUNT")>0)
					{
						System.out.println("Table is not available");
					}
					else
					{
						availableTableID = table.getID();
						break;
					}
		    	//return availableTables;
				}//end if
			} catch (SQLException e) {
				e.printStackTrace();
				throw new AppException(e.getMessage(), e.getCause());
			} finally {
				//DBUtils.closeResource(ps, rs, con);
			}
				
		}//end of for 
		
		DBUtils.closeResource(ps, rs, con);
		
		System.out.println("the available table id is " + availableTableID);
		return availableTableID;
		
	}
	
}
