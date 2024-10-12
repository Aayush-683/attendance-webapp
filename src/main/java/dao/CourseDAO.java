package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Utils.Database;
import models.Course;

public class CourseDAO {
    public static List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        try (Connection connection = Database.getConnection()) {
            String query = "SELECT * FROM courses";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                courses.add(new Course(rs.getString("course_name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public static List<Course> getCoursesByTeacher(int teacherId) {
        List<Course> courses = new ArrayList<>();
        try (Connection connection = Database.getConnection()) {
            String query = "SELECT * FROM teacher_course WHERE teacher_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, teacherId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
				String query2 = "SELECT * FROM courses WHERE course_id = ?";
				PreparedStatement stmt2 = connection.prepareStatement(query2);
				stmt2.setInt(1, rs.getInt("course_id"));
				ResultSet rs2 = stmt2.executeQuery();

				if (rs2.next()) {
					courses.add(new Course(rs2.getString("course_name")));
				}
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }
    
    public static List<Course> getCoursesByDivision(String divisionName) {
        List<Course> courses = new ArrayList<>();
        try (Connection connection = Database.getConnection()) {
            String query = "SELECT * FROM teacher_course WHERE division_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, DivisionDAO.getDivisionIdByName(divisionName));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
				String query2 = "SELECT * FROM courses WHERE course_id = ?";
				PreparedStatement stmt2 = connection.prepareStatement(query2);
				stmt2.setInt(1, rs.getInt("course_id"));
				ResultSet rs2 = stmt2.executeQuery();
				if (rs2.next()) {
					courses.add(new Course(rs2.getString("course_name")));
				}
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }
	public static Boolean addCourse(Course course) {
		try (Connection connection = Database.getConnection()) {
			String query = "INSERT INTO courses (course_name) VALUES (?)";
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, course.getName());
			int rows = stmt.executeUpdate();
			return rows > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void deleteCourse(String courseId) {
		try (Connection connection = Database.getConnection()) {
			String query = "DELETE FROM courses WHERE course_name = ?";
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, courseId);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static List<String> getAllTeachers(String course) {
		List<String> teachers = new ArrayList<>();
        try (Connection connection = Database.getConnection()) {
            String query = "SELECT * FROM courses WHERE course_name = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, course);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
            	String query2 = "SELECT * FROM teacher_course WHERE course_id = ?";
            	PreparedStatement stmt2 = connection.prepareStatement(query2);
            	stmt2.setInt(1, rs.getInt("course_id"));
            	ResultSet rs2 = stmt2.executeQuery();
				while (rs2.next()) {
					String query3 = "SELECT * FROM teachers WHERE teacher_id = ?";
					PreparedStatement stmt3 = connection.prepareStatement(query3);
					stmt3.setInt(1, rs2.getInt("teacher_id"));
					ResultSet rs3 = stmt3.executeQuery();
					if (rs3.next()) {
						String query4 = "SELECT * FROM users WHERE user_id = ?";
						PreparedStatement stmt4 = connection.prepareStatement(query4);
						stmt4.setInt(1, rs3.getInt("user_id"));
						ResultSet rs4 = stmt4.executeQuery();
						if (rs4.next()) {
                            teachers.add(rs4.getString("name"));
						}
					}
				}
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
	}
	
	public static String getNameById(int id) {
		try (Connection connection = Database.getConnection()) {
			String query = "SELECT * FROM courses WHERE course_id = ?";
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getString("course_name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static int getIdByName(String name) {
		try (Connection connection = Database.getConnection()) {
			String query = "SELECT * FROM courses WHERE course_name = ?";
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("course_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
}
