package org.shweta.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.shweta.model.Menu;
import org.shweta.utils.DBUtils;

import orrg.shweta.exceptions.AppException;

public class MenuDao {
	
	public List<Menu> getAllMenus() throws AppException{
		
		List<Menu> menuList = new ArrayList<Menu>();

		Connection con = DBUtils.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("SELECT * FROM Menu");
			rs = ps.executeQuery();

			while (rs.next()) {
				Menu menu = new Menu();
				menu.setId(rs.getInt("ID"));
				menu.setMenu(rs.getString("NAME"));
				menu.setPrice(rs.getDouble("PRICE"));
				menu.setDescription(rs.getString("DESCRIPTION"));
				menu.setHotFlag(rs.getBoolean("HOTFLAG"));
				menuList.add(menu);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException(e.getMessage(), e.getCause());
		} finally {
			DBUtils.closeResource(ps, rs, con);
		}

		return menuList;
	}
	


}
