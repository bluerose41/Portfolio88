package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import dto.Shoutdto;
import dto.Userdto;

public class DBmanager extends DBconnect {

	public Userdto getLoginUser(String loginId, String password) {
		Connection conn = null;            
		PreparedStatement ps = null;    
		ResultSet rs = null;  
           
		String sql = "SELECT * FROM profile WHERE loginId=? AND password=?";
		Userdto user = null;
    
		try {
			
			conn = getConnection();

			ps = conn.prepareStatement(sql);	
			ps.setString(1, loginId);
			ps.setString(2, password);
			rs = ps.executeQuery();

			if (rs.next()) {
				
				user = new Userdto();
				user.setLoginId(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setUserName(rs.getString(4));
				user.setIcon(rs.getString(5));
				user.setIntroduce(rs.getString(6));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			close(rs);
			close(ps);
			close(conn);
		}

		return user;
	}

	
	public ArrayList<Shoutdto> getShoutList() {
		Connection conn = null;
		Statement ps = null;
		ResultSet rs = null;

		ArrayList<Shoutdto> list = new ArrayList<Shoutdto>();

		try {
			
			conn = getConnection();
			ps = conn.createStatement();

			String sql = "SELECT * FROM word ORDER BY date DESC";
			rs = ps.executeQuery(sql);

			while (rs.next()) {
				
				Shoutdto shout = new Shoutdto();
				shout.setUserName(rs.getString(2));
				shout.setIcon(rs.getString(3));
				shout.setDate(rs.getString(4));
				shout.setWriting(rs.getString(5));

				
				list.add(shout);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			close(rs);
			close(ps);
			close(conn);
		}

		return list;
	}

	public boolean setWriting(Userdto user, String writing) {
		Connection conn = null;
		PreparedStatement ps = null;

		boolean result = false;
		try {
			conn = getConnection();

			String sql = "INSERT INTO word(userName, icon, date, writing) VALUES(?, ?, ?, ?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUserName());
			ps.setString(2, user.getIcon());
			Calendar calender = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			ps.setString(3, sdf.format(calender.getTime()));
			ps.setString(4, writing);

			int cnt = ps.executeUpdate();
			if (cnt == 1) {
				
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			close(ps);
			close(conn);
		}

		return result;
	}
}
