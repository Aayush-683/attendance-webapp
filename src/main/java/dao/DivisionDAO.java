package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Utils.Database;
import models.Division;

public class DivisionDAO {
    // Method to get all divisions
    public static List<Division> getAllDivisions() {
        List<Division> divisions = new ArrayList<>();
        String query = "SELECT * FROM divisions";
        try (Connection connection = Database.getConnection()) {
        	PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String divisionName = rs.getString("division_name");
                divisions.add(new Division(divisionName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return divisions;
    }

    // Method to get division by name
	public static int getDivisionIdByName(String divisionName) {
        String query = "SELECT * FROM divisions WHERE division_name = ?";
        try (Connection connection = Database.getConnection()) {
        	PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, divisionName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("division_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
	}
	
	// Method to get division by id
	public static String getDivisionNameById(int divisionId) {
        String query = "SELECT * FROM divisions WHERE division_id = ?";
        try (Connection connection = Database.getConnection()) {
        	PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, divisionId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("division_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
	}
	
    // Method to add a division
    public static boolean addDivision(Division division) {
        String query = "INSERT INTO divisions (division_name) VALUES (?)";
        try (Connection connection = Database.getConnection()) {
        	PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, division.getDivisionName());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to delete a division
    public static boolean deleteDivision(String divisionName) {
        String query = "DELETE FROM divisions WHERE division_name = ?";
        try (Connection connection = Database.getConnection()) {
        	PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, divisionName);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Method to get total students in a division
	public static int getTotalStudentsInDivision(String divisionName) {
        int students = 0;
        int divisionId = getDivisionIdByName(divisionName);
		if (divisionId < 0) {
			return students;
		}
        String query = "SELECT * FROM students WHERE division_id = ?";
        try (Connection connection = Database.getConnection()) {
        	PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, divisionId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                students++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
	}
	
	// Method to get total teachers assigned to a division
	public static int getTotalTeachersInDivision(String divisionName) {
		int divisionId = getDivisionIdByName(divisionName);
		if (divisionId < 0) {
			return -1;
		}
		String query = "SELECT * FROM teacher_course WHERE division_id = ?";
		int count = 0;
		try (Connection connection = Database.getConnection()) {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, divisionId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
}
