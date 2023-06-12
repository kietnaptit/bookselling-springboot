package kietna.thihk2.thuvien.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kietna.thihk2.thuvien.model.User;

public class UserDAO {
    private String jdbcURL = "jdbc:mysql://localhost/thuvien";
    private String jdbcUsername = "root";
    private String jdbcPassword = "NguyenAnhKiet@@123";
    
    private static final String CHECK_LOGIN_QUERY = "SELECT * FROM user WHERE username = ? AND password = ?";
    private static final String CHECK_USER_EXIST = "SELECT * FROM user WHERE username = ?";
    private static final String REGISTER_NEW_USER = "INSERT INTO user(name, username, email, password, role) VALUES (?,?,?,?,?)";
    
    public UserDAO() {};
    
    protected Connection getConnection() {
    	Connection connection = null;
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
    		connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	return connection;
		
	}
    
    public User checkLogin(String username, String password) {
    	User user = new User();
    	try {
    		Connection connection = getConnection();
    		PreparedStatement ps = connection.prepareStatement(CHECK_LOGIN_QUERY);
    		ps.setString(1, username);
    		ps.setString(2, password);
    		ResultSet rs = ps.executeQuery();
    		while(rs.next()) {
    			user.setEmail(rs.getString("email"));
    			user.setId(rs.getInt("id"));
    			user.setName(rs.getString("name"));
    			user.setPassword(rs.getString("password"));
    			user.setRole(rs.getString("role"));
    			user.setUsername(rs.getString("username"));
    		}
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return user;
	}
    
    public boolean checkExist(String username) {
    	boolean result = false;
    	try {
    		Connection connection = getConnection();
    		PreparedStatement ps = connection.prepareStatement(CHECK_USER_EXIST);
    		ps.setString(1, username);
    		ResultSet rs = null;
    		rs = ps.executeQuery();
    		if(rs.next()) {
    			result = true;
    		}
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	return result;
    }
    
    public boolean registerUser(User user) {
    	boolean result = false;
    	try {
    		Connection connection = getConnection();
    		PreparedStatement ps = connection.prepareStatement(REGISTER_NEW_USER);
    		ps.setString(1, user.getName());
    		ps.setString(2, user.getUsername());
    		ps.setString(3, user.getEmail());
    		ps.setString(4, user.getPassword());
    		ps.setString(5, "user");
    		ps.execute();
    		ps.close();
    		result = true;
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	return result;
    }
}
