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

public class LoginDAO {
	/* Description: This function authenticates the user an return a boolean vale
	 * Input: email and password 
	 * Output: True : if email and password are correct
	 *         False: if email , password pair is not correct
	 * */
	public Boolean isAuthorized (String email , String password) throws AppException{
		
		Boolean isAuthorized  = false;

		Connection con = DBUtils.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("SELECT  Count(*) as Count FROM   RESTAURANT_DB.OWNER WHERE EMAIL = ? AND PASSWORD =  MD5(?)");

		    ps.setString(1, email);
		    ps.setString(2, password);
		    System.out.println("Query is :  "+ ps.toString());
			rs = ps.executeQuery();

			while (rs.next()) {
				
				int count = (rs.getInt("Count"));
				if(count ==1)
				{
					isAuthorized = true;
				}
			
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException(e.getMessage(), e.getCause());
		} finally {
			DBUtils.closeResource(ps, rs, con);
		}
    	return isAuthorized;
	}
}
