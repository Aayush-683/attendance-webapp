package dao;

import java.sql.*;

import Utils.Database;
import models.User;

public class UserDAO {
    public static User authenticate(String email, String password) {
        // Logic to authenticate user
        try (Connection connection = Database.getConnection();) {
            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(rs.getString("name"), email, password, rs.getString("role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String registerUser(User user) {
    	// Check if user with the same email already exists
        try (Connection connection = Database.getConnection()) {
            String query = "SELECT * FROM users WHERE email = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, user.getEmail());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return "User Already Exists";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	
        // Logic to register new user
        try (Connection connection = Database.getConnection()) {
            String query = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getRole());
            stmt.executeUpdate();
            return "User Registered";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Registration Failed";
    }
}
