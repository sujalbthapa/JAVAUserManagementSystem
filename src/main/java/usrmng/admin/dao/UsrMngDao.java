package usrmng.admin.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import usrmng.admin.bean.UserMngBean;

public class UsrMngDao {
	private String dbUrl = "jdbc://localhost:3306/practice";
	private String dbUsr = "root";
	private String dbPwd = "1527";
	private String dbDriver = "com.mysql.cj.jdbc.Driver";
	
	private String InsertUsrSql="INSERT INTO usrMng (name, email, address)"
			+ " VALUES (?,?,?);";
	private String SelectUsrIdSql = "SELECT * FROM usrMng WHERE id=?";
	private String SelectUsrAllSql = "SELECT * FROM usrMng";
	private String DeleteUsrSql = "DELETE FROM usrMng WHERE id=?";
	private String UpdateUsrSql = "UPDATE usrMng SET name=?, email=?,"
			+ "address=? WHERE id=?; ";
	
	public UsrMngDao() {
		
	}
	
	//creating the connection between the usr and the database
	protected Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(dbDriver);
			conn = DriverManager.getConnection(dbUrl, dbUsr, dbPwd);
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	//inserting user into the database
	public void insertUsr(UserMngBean usr) throws SQLException{
		try(Connection conn = getConnection();
				PreparedStatement prpSt = conn.prepareStatement(InsertUsrSql)){
			prpSt.setString(1, usr.getName());
			prpSt.setString(2, usr.getEmail());
			prpSt.setString(3, usr.getAddress());
			System.out.println(prpSt);
			prpSt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	//selection user using id from database
	public UserMngBean selectUsr(int id) {
		UserMngBean Usr = null;
		try(Connection conn = getConnection();
				PreparedStatement prpSt = conn.prepareStatement(SelectUsrIdSql);){
			prpSt.setInt(1, id);
			System.out.println(prpSt);
			ResultSet rs = prpSt.executeQuery();
			while(rs.next()) {
				String name = rs.getString("name");
				String email = rs.getString("email");
				String address = rs.getString("address");
				Usr = new UserMngBean(id, name, email, address);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return Usr;
	}
	
	//select all users from database
	public List<UserMngBean> selectAllUsr(){
		List<UserMngBean> users = new ArrayList<>();
		try(Connection conn = getConnection();
				PreparedStatement prpSt = conn.prepareStatement(SelectUsrAllSql);){
			System.out.println(prpSt);
			ResultSet rs = prpSt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String address = rs.getString("address");
				users.add(new UserMngBean(id, name, email, address));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return users;
	}
	
	//updating user in the database
	public boolean updateUser(UserMngBean usr) throws SQLException{
		boolean rowUpdated;
		try(Connection conn = getConnection();
				PreparedStatement prpSt = conn.prepareStatement(UpdateUsrSql);){
			prpSt.setString(1, usr.getName());
			prpSt.setString(2, usr.getEmail());
			prpSt.setString(3, usr.getAddress());
			prpSt.setInt(4, usr.getId());
			
			rowUpdated = prpSt.executeUpdate() > 0;
		}
		return rowUpdated;
	}
	
	//deleting user from database
	public boolean deleteUsr(int id) throws SQLException{
		boolean rowDeleted;
		try(Connection conn = getConnection();
				PreparedStatement prpSt = conn.prepareStatement(DeleteUsrSql);){
			prpSt.setInt(1, id);
			rowDeleted = prpSt.executeUpdate()>0;
		}
		return rowDeleted;
	}
	
	
}
