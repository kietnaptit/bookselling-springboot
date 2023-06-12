package kietna.thihk2.thuvien.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kietna.thihk2.thuvien.model.Book;

public class BookDAO {
	private String jdbcURL = "jdbc:mysql://localhost/thuvien";
    private String jdbcUsername = "root";
    private String jdbcPassword = "NguyenAnhKiet@@123";
    
    private static final String SELECT_ALL_BOOKS = "SELECT * FROM book";
    private static final String GET_BOOK_BY_ID = "SELECT * FROM book WHERE id = ?";
    private static final String UPDATE_A_BOOK = "UPDATE book SET title = ?, author = ?, description = ?, publishdate = ?, numberofpage = ?, cover = ? WHERE id = ?";
    private static final String CHECK_BOOK_EXISTED = "SELECT * FROM book WHERE title = ? AND author = ?";
    private static final String ADD_A_BOOK = "INSERT INTO book(title,author,category,publishdate,numberofpage,sold,description,cover) VALUES(?,?,?,?,?,0,?,?)";
    private static final String DELETE_A_BOOK = "DELETE FROM book WHERE id = ?";
    private static final String GET_BOOK_COVER = "SELECT cover FROM book WHERE id = ?";
    
    public BookDAO() {};
    
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
    
    public List<Book> getAllBooks(){
    	List<Book> books = new ArrayList<>();
    	try {
    		Connection connection = getConnection();
    		PreparedStatement ps = connection.prepareStatement(SELECT_ALL_BOOKS);
    		ResultSet rs = ps.executeQuery();
    		while(rs.next()) {
    			Book book = new Book();
    			book.setId(rs.getInt("id"));
    			book.setTitle(rs.getString("title"));
    			book.setAuthor(rs.getString("author"));
    			book.setCategory(rs.getString("category"));
    			book.setPublishdate(rs.getDate("publishdate"));
    			book.setNumberofpage(rs.getInt("numberofpage"));
    			book.setSold(rs.getInt("sold"));
    			book.setCover(rs.getString("cover"));
    			books.add(book);
    		}
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	return books;
    }
    
    public Book getABookById(int id) {
    	Book book = new Book();
    	try {
    		Connection connection = getConnection();
    		PreparedStatement ps = connection.prepareStatement(GET_BOOK_BY_ID);
    		ps.setInt(1, id);
    		ResultSet rs = ps.executeQuery();
    		while(rs.next()) {
    			book.setAuthor(rs.getString("author"));
    			book.setCategory(rs.getString("category"));
    			book.setCover(rs.getString("cover"));
    			book.setDescription(rs.getString("description"));
    			book.setId(rs.getInt("id"));
    			book.setNumberofpage(rs.getInt("numberofpage"));
    			book.setPublishdate(rs.getDate("publishdate"));
    			book.setSold(rs.getInt("sold"));
    			book.setTitle(rs.getString("title"));
    		}
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	return book;
    }
    
    public boolean checkExisted(Book book) {
    	boolean notExisted = true;
    	try {
    		Connection connection = getConnection();
    		PreparedStatement ps = connection.prepareStatement(CHECK_BOOK_EXISTED);
    		ps.setString(1, book.getTitle());
    		ps.setString(2, book.getAuthor());
    		ResultSet rs = ps.executeQuery();
    		if(rs.next()) {
    			notExisted = false;
    		}
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	return notExisted;
    	
    }
    
    public boolean updateABook(Book book) {
    	boolean result = false;
    	try {
    		Connection connection = getConnection();
    		PreparedStatement ps = connection.prepareStatement(UPDATE_A_BOOK);
    		ps.setString(1, book.getTitle());
    		ps.setString(2, book.getAuthor());
    		ps.setString(3, book.getDescription());
    		ps.setDate(4, (java.sql.Date) book.getPublishdate());
    		ps.setInt(5, book.getNumberofpage());
    		ps.setString(6, book.getCover());
    		ps.setInt(7, book.getId());
    		ps.execute();
    		ps.close();
    		result = true;
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	return result;
    }
    
    public boolean addABook(Book book) {
    	boolean result = false;
    	try {
    		Connection connection = getConnection();
    		PreparedStatement ps = connection.prepareStatement(ADD_A_BOOK);
    		ps.setString(1, book.getTitle());
    		ps.setString(2, book.getAuthor());
    		ps.setString(3, book.getCategory());
    		ps.setDate(4, (java.sql.Date) book.getPublishdate());
    		ps.setInt(5, book.getNumberofpage());
    		ps.setString(6, book.getDescription());
    		ps.setString(7, book.getCover());
    		ps.execute();
    		ps.close();
    		result = true;
    		
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	return result;
    }
    public boolean deleteABook(int id) {
        boolean result = false;
        try {
          Connection connection = getConnection();
          PreparedStatement ps = connection.prepareStatement(DELETE_A_BOOK);
          ps.setInt(1, Integer.valueOf(id));
          ps.execute();
          ps.close();
          result = true;
        }catch (Exception e) {
          e.printStackTrace();
        }
        return result;
      }
    
    public String getBookCover(int id) {
    	String result = "";
    	try {
    		Connection connection = getConnection();
    		PreparedStatement ps = connection.prepareStatement(GET_BOOK_COVER);
    		ps.setInt(1, id);
    		ResultSet rs = ps.executeQuery();
    		while(rs.next()) {
    			result = rs.getString("cover");
    		}
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	return result;
    }
    
}
