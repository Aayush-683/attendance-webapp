package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Utils.Database;
import models.Course;
import models.Teacher;

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
            String query = "SELECT * FROM courses WHERE teacher_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, teacherId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                courses.add(new Course(rs.getString("course_name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public static List<Course> getCoursesByStudent(int studentId) {
        List<Course> courses = new ArrayList<>();
        // Implement similar logic to fetch courses for a specific student
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
                Teacher teacher = TeacherDAO.getTeacherById(rs.getInt("teacher_id"));
                teachers.add(teacher.getName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
	}
}
