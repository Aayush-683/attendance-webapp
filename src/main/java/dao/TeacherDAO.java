package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Utils.Database;
import models.Course;
import models.Division;
import models.Teacher;

public class TeacherDAO {
    public static List<Teacher> getAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        try (Connection connection = Database.getConnection()) {
            String query = "SELECT * FROM teachers";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                teachers.add(new Teacher(rs.getString("name"), rs.getString("email"), rs.getString("password")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }

    public static Boolean addTeacher(Teacher teacher) {
        // Logic to add a new teacher to the database
        try (Connection connection = Database.getConnection()) {
            String query = "INSERT INTO teachers (name, email, password) VALUES (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, teacher.getName());
            stmt.setString(2, teacher.getEmail());
            stmt.setString(3, teacher.getPassword());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

	public static void deleteTeacher(String teacher) {
		// Logic to delete a teacher from the database
        try (Connection connection = Database.getConnection()) {
            String query = "DELETE FROM teachers WHERE email = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, teacher);
            stmt.executeUpdate();
            // Delete from users table as well
            query = "DELETE FROM users WHERE email = ?";
            Teacher teacher1 = getTeacherByEmail(teacher);
            stmt = connection.prepareStatement(query);
            stmt.setString(1, teacher1.getEmail());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	public static Teacher getTeacherByEmail(String teacherId) {
		Teacher teacher = null;
		String query = "SELECT * FROM teachers WHERE email = ?";
		try (Connection connection = Database.getConnection()) {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, teacherId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				teacher = new Teacher(rs.getString("name"), rs.getString("email"), rs.getString("password"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return teacher;
	}
	
	public static Teacher getTeacherById(int teacher) {
		Teacher teacher1 = null;
		String query = "SELECT * FROM teachers WHERE teacher_id = ?";
		try (Connection connection = Database.getConnection()) {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, teacher);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				teacher1 = new Teacher(rs.getString("name"), rs.getString("email"), rs.getString("password"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return teacher1;
	}
	
	public static List<Division> getAllDivisions(String teacherEmail) {
		List<Division> divisions = new ArrayList<>();
        String query = "SELECT * FROM teachers WHERE email = ?";
        int TeacherID = -1;
        try (Connection connection = Database.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, teacherEmail);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TeacherID = rs.getInt("teacher_id");
            }
			if (TeacherID < 0) {
				return null;
			} else {
				String query2 = "SELECT * from teacher_course WHERE teacher_id = ?";
				PreparedStatement ps2 = connection.prepareStatement(query2);
				ps2.setInt(1, TeacherID);
				ResultSet rs2 = ps2.executeQuery();
				while (rs2.next()) {
					String query3 = "SELECT * from divisions WHERE division_id = ?";
					PreparedStatement ps3 = connection.prepareStatement(query3);
					ps3.setInt(1, rs2.getInt("division_id"));
					ResultSet rs3 = ps3.executeQuery();
					while (rs3.next()) {
						divisions.add(new Division(rs3.getString("division_name")));
					}
				}
			}
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return divisions;
    }
	
	public static List<Course> getAllCourses(String teacherEmail) {
		List<Course> courses = new ArrayList<>();
		String query = "SELECT * FROM teachers WHERE email = ?";
		int TeacherID = -1;
		try (Connection connection = Database.getConnection()) {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, teacherEmail);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				TeacherID = rs.getInt("teacher_id");
			}
			if (TeacherID < 0) {
				return null;
			} else {
				String query2 = "SELECT * from teacher_course WHERE teacher_id = ?";
				PreparedStatement ps2 = connection.prepareStatement(query2);
				ps2.setInt(1, TeacherID);
				ResultSet rs2 = ps2.executeQuery();
				while (rs2.next()) {
					String query3 = "SELECT * from courses WHERE course_id = ?";
					PreparedStatement ps3 = connection.prepareStatement(query3);
					ps3.setInt(1, rs2.getInt("course_id"));
					ResultSet rs3 = ps3.executeQuery();
					while (rs3.next()) {
						courses.add(new Course(rs3.getString("course_name")));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courses;
	}
}
