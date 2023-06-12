package kietna.thihk2.thuvien.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kietna.thihk2.thuvien.model.Order;
import kietna.thihk2.thuvien.model.User;

public class OrderDAO {
	private String jdbcURL = "jdbc:mysql://localhost/thuvien";
    private String jdbcUsername = "root";
    private String jdbcPassword = "NguyenAnhKiet@@123";
    
    private static final String GET_AN_ORDER = "SELECT * FROM userorder WHERE id = ?";
    private static final String GET_ALL_ORDER = "SELECT * FROM userorder WHERE userid = ? AND status = 1";
    private static final String GET_ALL_CANCELED_ORDER = "SELECT * FROM userorder WHERE userid = ? AND status = 0";
    private static final String CREATE_NEW_ORDER = "INSERT INTO userorder(userid, bookid, bookname, quantity, status) VALUES (?, ?, ?, ?, 1)";
    private static final String CANCEL_ORDER = "UPDATE userorder SET status = 0 WHERE id = ?";
    private static final String GET_OLD_SOLD = "SELECT sold FROM book WHERE id = ?";
    private static final String UPDATE_SOLD = "UPDATE book SET sold = ? WHERE id = ?";
    public OrderDAO() {};
    
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
    
    public List<Order> getAllOrder(User user){
    	List<Order> orders = new ArrayList<>();
    	try {
    		Connection connection = getConnection();
    		PreparedStatement ps = connection.prepareStatement(GET_ALL_ORDER);
    		ps.setInt(1, user.getId());
    		ResultSet rs = ps.executeQuery();
    		while(rs.next()) {
    			Order order = new Order();
    			order.setId(rs.getInt("id"));
    			order.setUserid(rs.getInt("userid"));
    			order.setBookid(rs.getInt("bookid"));
    			order.setBookname(rs.getString("bookname"));
    			order.setQuantity(rs.getInt("quantity"));
    			order.setStatus(rs.getInt("status"));
    			orders.add(order);
    		}
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	return orders;
    }
    public List<Order> getAllCanceledOrder(User user){
    	List<Order> orders = new ArrayList<>();
    	try {
    		Connection connection = getConnection();
    		PreparedStatement ps = connection.prepareStatement(GET_ALL_CANCELED_ORDER);
    		ps.setInt(1, user.getId());
    		ResultSet rs = ps.executeQuery();
    		while(rs.next()) {
    			Order order = new Order();
    			order.setId(rs.getInt("id"));
    			order.setUserid(rs.getInt("userid"));
    			order.setBookid(rs.getInt("bookid"));
    			order.setBookname(rs.getString("bookname"));
    			order.setQuantity(rs.getInt("quantity"));
    			order.setStatus(rs.getInt("status"));
    			orders.add(order);
    		}
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	return orders;
    }    
    public boolean createNewOrder(Order order) {
    	boolean result = false;
    	try {
    		Connection connection = getConnection();
    		PreparedStatement ps = connection.prepareStatement(CREATE_NEW_ORDER);
    		ps.setInt(1, order.getUserid());
    		ps.setInt(2, order.getBookid());
    		ps.setString(3, order.getBookname());
    		ps.setInt(4, order.getQuantity());
    		ps.execute();
    		ps.close();
    		PreparedStatement ps2 = connection.prepareStatement(GET_OLD_SOLD);
    		ps2.setInt(1, order.getBookid());
    		ResultSet rs2 = ps2.executeQuery();
    		int oldSold = 0;
    		while(rs2.next()) {
    			oldSold = rs2.getInt("sold");
    		}
    		ps2.close();
    		int newSold = oldSold + order.getQuantity();
    		PreparedStatement ps3 = connection.prepareStatement(UPDATE_SOLD);
    		ps3.setInt(1, newSold);
    		ps3.setInt(2, order.getBookid());
    		ps3.execute();
    		ps3.close();
    		result = true;
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	return result;
    }
    
    public boolean cancelAnOrder(int id) {
    	boolean result = false;
    	try {
    		Connection connection = getConnection();
    		PreparedStatement ps = connection.prepareStatement(CANCEL_ORDER);
    		ps.setInt(1, id);
    		ps.execute();
    		ps.close();
    		PreparedStatement ps2 = connection.prepareStatement(GET_AN_ORDER);
    		ps2.setInt(1, id);
    		Order order = new Order();
    		ResultSet rs2 = ps2.executeQuery();
    		while(rs2.next()) {
    			order.setBookid(rs2.getInt("bookid"));
    			order.setBookname(rs2.getString("bookname"));
    			order.setId(rs2.getInt("id"));
    			order.setQuantity(rs2.getInt("quantity"));
    			order.setStatus(rs2.getInt("status"));
    			order.setUserid(rs2.getInt("userid"));
    		}
    		ps2.close();
    		PreparedStatement ps3 = connection.prepareStatement(GET_OLD_SOLD);
    		ps3.setInt(1, order.getBookid());
    		ResultSet rs3 = ps3.executeQuery();
    		int oldSold = 0;
    		while(rs3.next()) {
    			oldSold = rs3.getInt("sold");
    		}
    		ps3.close();
    		int newSold = oldSold - order.getQuantity();
    		PreparedStatement ps4 = connection.prepareStatement(UPDATE_SOLD);
    		ps4.setInt(1, newSold);
    		ps4.setInt(2, order.getBookid());
    		ps4.execute();
    		ps4.close();
    		result = true;
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	return result;
    }

}
