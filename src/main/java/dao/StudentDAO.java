package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Utils.Database;
import models.Student;

public class StudentDAO {
    public static List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        try (Connection connection = Database.getConnection()) {
            String query = "SELECT * FROM students";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                students.add(new Student(rs.getString("name"), rs.getString("email"), rs.getString("password"), rs.getInt("divisionId")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
    
    public static Student getStudentFromEmail(String email) {
		try (Connection connection = Database.getConnection()) {
			String query = "SELECT * FROM students WHERE email = ?";
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return new Student(rs.getString("name"), email, rs.getString("password"), rs.getInt("divisionId"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
    }

    public static void addStudent(Student student) {
        // Logic to add a new student to the database
        try (Connection connection = Database.getConnection()) {
            String query = "INSERT INTO students (name, email, password) VALUES (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, student.getName());
            stmt.setString(2, student.getEmail());
            stmt.setString(3, student.getPassword());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	public static void deleteStudent(String studentId) {
		// Logic to delete a student from the database
        try (Connection connection = Database.getConnection()) {
            String query = "DELETE FROM students WHERE email = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, studentId);
            stmt.executeUpdate();
            // Delete from users table as well
            query = "DELETE FROM users WHERE email = ?";
            Student student = getStudentById(studentId);
            stmt = connection.prepareStatement(query);
            stmt.setString(1, student.getEmail());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	public static Student getStudentById(String studentId) {
		Student student = null;
		String query = "SELECT * FROM students WHERE email = ?";
		try (Connection connection = Database.getConnection()) {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, studentId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				student = new Student(rs.getString("name"), rs.getString("email"), rs.getString("password"), rs.getInt("divisionId"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return student;
	}

	public static List<Student> getStudentsByDivision(int divisionId) {
        List<Student> students = new ArrayList<>();
        try (Connection connection = Database.getConnection()) {
            String query = "SELECT * FROM students WHERE division_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, divisionId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                students.add(new Student(rs.getString("name"), rs.getString("email"), rs.getString("password"), rs.getInt("divisionId")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
}
