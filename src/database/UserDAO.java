package database;


import java.sql.*;

public class UserDAO {
	public boolean registerUser(String username, String password) {
        if (isUsernameExists(username)) {
            return false; 
        }

        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
	
	 private boolean isUsernameExists(String username) {
	        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
	        try (Connection conn = DatabaseConnection.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            
	            pstmt.setString(1, username);
	            try (ResultSet rs = pstmt.executeQuery()) {
	                if (rs.next()) {
	                    return rs.getInt(1) > 0;
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return false;
	    }

	 public User loginUser(String username, String password) {
		    String sql = "SELECT id, username, password, is_logged_in FROM users WHERE username = ? AND password = ?";
		    try (Connection conn = DatabaseConnection.getConnection();
		         PreparedStatement pstmt = conn.prepareStatement(sql)) {

		        pstmt.setString(1, username);
		        pstmt.setString(2, password);

		        ResultSet rs = pstmt.executeQuery();
		        if (rs.next()) {
		            int id = rs.getInt("id");
		            boolean isLoggedIn = rs.getBoolean("is_logged_in");
		            if (isLoggedIn) {
		                return null; 
		            }
		            User user = new User(id, rs.getString("username"), rs.getString("password"));
		            setUserLoggedIn(id, true);
		            return user;
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return null;
		}
	 
    public User getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean isUserLoggedIn(int userId) {
        String sql = "SELECT is_logged_in FROM users WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("is_logged_in");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setUserLoggedIn(int userId, boolean isLoggedIn) {
        String sql = "UPDATE users SET is_logged_in = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, isLoggedIn);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updateLoggedOut(int userID) {
        String sql = "UPDATE users SET is_logged_in = false WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Lỗi khi cập nhật trạng thái logged_in: " + e.getMessage());
        }
    }
    
}