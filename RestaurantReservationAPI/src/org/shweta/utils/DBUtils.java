package org.shweta.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtils {

	private final static String DB_URL = "jdbc:mysql://localhost:3306/restaurant_db";
	private final static String DB_USER = "root";
	private final static String DB_PASSWORD = "root";
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("MySQL JDBC Driver successfully loaded");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.err.println("Error in loading MySQL JDBC Driver" + e.getMessage());
		}
	}
	
	public static Connection getConnection () {
		Connection con = null;
		
		try {
			con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			System.out.println("Connection successful");
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error in connection. " + e.getMessage());
		}
		return con;
	}

	
	public static void closeResource (PreparedStatement ps, ResultSet rs, Connection con) {
		try {
			if (ps != null) {
				ps.close();
			}
			if (rs != null) {
				rs.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}

/*import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {
	private final static String DB_URL = "jdbc:mysql://localhost:3306/emp_db";
	private final static String DB_USER = "root";
	private final static String DB_PASSWORD = "root"; 
	
	
	
	static{
		try{
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("MySQL JDBC driver successfully loaded");
	}catch(ClassNotFoundException e )
	{
		e.printStackTrace();
		System.err.println("Error in loading MySQL JDBC Driver");
	}
	
	}
	
	public static Connection getConnection(){
		Connection con = null;
		try {
			con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
			
			System.out.println("Got the connection:");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Error in connection. " + e.getMessage());
			System.out.println(e.getMessage());
		}
		System.out.println("returning connection");
		return con;
	}
	
	//public static void main(String[] args){
		//DBUtils.getConnection();
//	}
	
	
	
	
}*/
