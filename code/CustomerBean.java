package custom.bean;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import custom.errors.DuplicateEmailException;


public class CustomerBean {

		private static DataSource ds = null;

	private static final String JNDI_NAME = "java:comp/env/jdbc/ssjdb";

	private static DataSource getDataSource() throws NamingException {

		if(ds == null){
			InitialContext ic = new InitialContext();
			ds = (DataSource) ic.lookup(JNDI_NAME);
		}

		return ds;
	}

	/**
	 * 全件検索処理
	 */

	public Collection<UserInfo> findAllUserInfo() throws SQLException {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

        try {
            String sql ="SELECT name,yomi,birthday,zip,address,tel,email FROM customerinformation";

        	Collection<UserInfo> userList = new ArrayList<UserInfo>();

        	conn = getDataSource().getConnection();

            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();

            while(rs.next()){

            	UserInfo userInfo = new UserInfo();

            	userInfo.setName(rs.getString("name"));
            	userInfo.setYomi(rs.getString("yomi"));
            	userInfo.setBirthday(rs.getString("birthday"));
            	userInfo.setZip(rs.getString("zip"));
            	userInfo.setAddress(rs.getString("address"));
            	userInfo.setTel(rs.getString("tel"));
            	userInfo.setEmail(rs.getString("email"));

            	userList.add(userInfo);

            }


            return userList;
        }  catch (NamingException e) {
            e.printStackTrace();
            throw new SQLException(e);
        } finally {

        	if(rs != null){rs.close();}
        	if(ps != null){ps.close();}
        	if(conn != null){conn.close();}
        }
    }

	/**
	 * 新規登録処理
	 */

	public void registUserInfo(UserInfo userInfo) throws SQLException,
			DuplicateEmailException{

		Connection conn = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;

		try {

			String sql ="INSERT INTO customerinformation(name,yomi,birthday,zip,address,tel,email)"
					+" VALUES (?,?,?,?,?,?,?)";

			String sqlForCheck = "SELECT email FROM customerinformation WHERE email=?";

			conn = getDataSource().getConnection();
			ps1 = conn.prepareStatement(sqlForCheck);
			ps1.setString(1, userInfo.getEmail());
			rs = ps1.executeQuery();

			ps2 = conn.prepareStatement(sql);

			ps2.setString(1, userInfo.getName());
			ps2.setString(2, userInfo.getYomi());
			ps2.setString(3, userInfo.getBirthday());
			ps2.setString(4, userInfo.getZip());
			ps2.setString(5, userInfo.getAddress());
			ps2.setString(6, userInfo.getTel());
			ps2.setString(7, userInfo.getEmail());

			ps2.executeUpdate();

		} catch (NamingException e) {
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			if(rs != null){rs.close();}
			if(ps1 != null){ps1.close();}
			if(ps2 != null){ps2.close();}
			if(conn != null){conn.close();}

		}
	}

	/**
	 削除処理
	 */
	public void deleteUserInfo(String email) throws SQLException {

		Connection conn = null;
		PreparedStatement ps = null;
		try {

			String sql = "DELETE FROM customerinformation WHERE email=?";

			conn = getDataSource().getConnection();

			ps = conn.prepareStatement(sql);

			ps.setString(1, email);

			ps.executeUpdate();

		} catch (NamingException e) {
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			if(ps != null){ps.close();}
			if(conn != null){conn.close();}

		}

	}
}
