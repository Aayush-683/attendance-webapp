package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Utils.Database;
import models.Student;
import models.User;

public class StudentDAO {
    public static List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        try (Connection connection = Database.getConnection()) {
            String query = "SELECT * FROM students";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
            	int Division = rs.getInt("division_id");
                String query2 = "SELECT * FROM users WHERE user_id = ?";
                PreparedStatement stmt2 = connection.prepareStatement(query2);
                stmt2.setInt(1, rs.getInt("user_id"));
                ResultSet rs2 = stmt2.executeQuery();
                if (rs2.next()) {
                    students.add(new Student(rs2.getString("name"), rs2.getString("email"), rs2.getString("password"), Division));
                }
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
				String query2 = "SELECT * FROM users WHERE user_id = ?";
				int divisionID = rs.getInt("divisionId");
				PreparedStatement stmt2 = connection.prepareStatement(query2);
				stmt2.setInt(1, rs.getInt("user_id"));
				ResultSet rs2 = stmt2.executeQuery();
				if (rs2.next()) {
                    return new Student(rs2.getString("name"), rs2.getString("email"), rs2.getString("password"), divisionID);
				}
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
	
	public static String getStudentDivisionFromUser(User user) {
		String query = "SELECT * FROM users where email = ?";
		try (Connection connection = Database.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, user.getEmail());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String query2 = "SELECT * FROM students WHERE user_id = ?";
                PreparedStatement ps2 = connection.prepareStatement(query2);
                ps2.setInt(1, rs.getInt("user_id"));
                ResultSet rs2 = ps2.executeQuery();
                if (rs2.next()) {
                    String query3 = "SELECT * FROM divisions WHERE division_id = ?";
                    PreparedStatement ps3 = connection.prepareStatement(query3);
                    ps3.setInt(1, rs2.getInt("division_id"));
                    ResultSet rs3 = ps3.executeQuery();
                    if (rs3.next()) {
                        return rs3.getString("division_name");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
	
	public static List<Student> getStudentsByDivisionName(String divisionName) {
		List<Student> students = new ArrayList<>();
		String query = "SELECT * FROM divisions WHERE division_name = ?";
		try (Connection connection = Database.getConnection()) {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, divisionName);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				System.out.println("Division ID: " + rs.getInt("division_id"));
				String query2 = "SELECT * FROM users, students WHERE students.user_id = users.user_id AND division_id = ?";
				PreparedStatement ps2 = connection.prepareStatement(query2);
				ps2.setInt(1, rs.getInt("division_id"));
				ResultSet rs2 = ps2.executeQuery();
				while (rs2.next()) {
					students.add(new Student(rs2.getString("users.name"), rs2.getString("users.email"), rs2.getString("users.password"),
							rs2.getInt("division_id")));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return students;
	}
	
	public static int getIdByName(String name) {
		try (Connection connection = Database.getConnection()) {
			String query = "SELECT * FROM students, users WHERE users.user_id = students.user_id AND users.name = ?";
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("student_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public static int getAttendancePerc(String courseName, String studentName) {
		String query = "SELECT * from attendance, courses, students, users "
				+ "WHERE attendance.student_id = students.student_id "
				+ "AND students.user_id = users.user_id "
				+ "AND attendance.course_id = courses.course_id "
				+ "AND courses.course_name = ? "
				+ "AND users.name = ?";
		try (Connection connection = Database.getConnection()) {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, courseName);
			ps.setString(2, studentName);
			ResultSet rs = ps.executeQuery();
			int total = 0;
			int present = 0;
			while (rs.next()) {
				total++;
				if (rs.getString("status").equals("present")) {
					present++;
				}
			}
			if (total == 0) {
				return 0;
			}
			return (present * 100) / total;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
