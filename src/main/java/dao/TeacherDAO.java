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
import models.User;

public class TeacherDAO {
    public static List<Teacher> getAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        try (Connection connection = Database.getConnection()) {
            String query = "SELECT * FROM teachers";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String query2 = "SELECT * FROM users WHERE user_id = ?";
                PreparedStatement stmt2 = connection.prepareStatement(query2);
                stmt2.setInt(1, rs.getInt("user_id"));
                ResultSet rs2 = stmt2.executeQuery();
                if (rs2.next()) {
                    teachers.add(new Teacher(rs2.getString("name"), rs2.getString("email"), rs2.getString("password")));
                }
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
            String query = "DELETE FROM users WHERE email = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, teacher);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	public static Teacher getTeacherByEmail(String email) {
		String query = "SELECT * FROM users WHERE email = ?";
		try (Connection connection = Database.getConnection()) {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String query2 = "SELECT * FROM teachers WHERE user_id = ?";
				PreparedStatement ps2 = connection.prepareStatement(query2);
				ps2.setInt(1, rs.getInt("user_id"));
				ResultSet rs2 = ps2.executeQuery();
				if (rs2.next()) {
					return new Teacher(rs.getString("name"), rs.getString("email"), rs.getString("password"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
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
	
	public static int getTeacherIdByUser(User user) {
		String query = "SELECT * FROM users WHERE email = ?";
		try (Connection connection = Database.getConnection()) {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, user.getEmail());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String query2 = "SELECT * FROM teachers WHERE user_id = ?";
				PreparedStatement ps2 = connection.prepareStatement(query2);
				ps2.setInt(1, rs.getInt("user_id"));
				ResultSet rs2 = ps2.executeQuery();
				if (rs2.next()) {
					return rs2.getInt("teacher_id");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public static List<Division> getAllDivisions(String teacherEmail) {
		List<Division> divisions = new ArrayList<>();
        String query = "SELECT * FROM users WHERE email = ?";
        int TeacherID = -1;
        try (Connection connection = Database.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, teacherEmail);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
				int userID = rs.getInt("user_id");
				String query1 = "SELECT * FROM teachers WHERE user_id = ?";
				PreparedStatement ps1 = connection.prepareStatement(query1);
				ps1.setInt(1, userID);
				ResultSet rs1 = ps1.executeQuery();
				while (rs1.next()) {
					TeacherID = rs1.getInt("teacher_id");
				}
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
		String query = "SELECT * FROM users WHERE email = ?";
		int TeacherID = -1;
		try (Connection connection = Database.getConnection()) {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, teacherEmail);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int userID = rs.getInt("user_id");
				String query1 = "SELECT * FROM teachers WHERE user_id = ?";
				PreparedStatement ps1 = connection.prepareStatement(query1);
				ps1.setInt(1, userID);
				ResultSet rs1 = ps1.executeQuery();
				while (rs1.next()) {
                    TeacherID = rs1.getInt("teacher_id");
				}
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
	
	public static String getNameById(int id) {
		try (Connection connection = Database.getConnection()) {
			String query = "SELECT * FROM teachers WHERE teacher_id = ?";
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				String query2 = "SELECT * FROM users WHERE user_id = ?";
				PreparedStatement stmt2 = connection.prepareStatement(query2);
				stmt2.setInt(1, rs.getInt("user_id"));
				ResultSet rs2 = stmt2.executeQuery();
				if (rs2.next()) {
					return rs2.getString("name");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static int getIdByName(String name) {
		try (Connection connection = Database.getConnection()) {
			String query = "SELECT * FROM users WHERE name = ?";
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				String query2 = "SELECT * FROM teachers WHERE user_id = ?";
				PreparedStatement stmt2 = connection.prepareStatement(query2);
				stmt2.setInt(1, rs.getInt("user_id"));
				ResultSet rs2 = stmt2.executeQuery();
				if (rs2.next()) {
					return rs2.getInt("teacher_id");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static void assignCourse(int courseId, int teacherId, int divisionId) {
		try (Connection connection = Database.getConnection()) {
            String query = "INSERT INTO teacher_course (course_id, teacher_id, division_id) VALUES (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, courseId);
            stmt.setInt(2, teacherId);
            stmt.setInt(3, divisionId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
}
