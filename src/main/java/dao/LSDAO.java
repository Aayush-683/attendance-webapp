package dao;

import models.LectureSchedule;
import Utils.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LSDAO {
    // Method to fetch all lecture schedules
    public static List<LectureSchedule> getAllSchedules() {
        List<LectureSchedule> schedules = new ArrayList<>();
        String sql = "SELECT ls.schedule_id, ls.course_id, ls.division_id, ls.teacher_id, ls.lecture_day, " +
                     "ls.start_time, ls.end_time, c.course_name, d.division_name, u.name " +
                     "FROM lecture_schedule ls " +
                     "JOIN courses c ON ls.course_id = c.course_id " +
                     "JOIN divisions d ON ls.division_id = d.division_id " +
                     "JOIN teachers t ON ls.teacher_id = t.teacher_id " +
                     "JOIN users u ON t.user_id = u.user_id";

        try (Connection connection = Database.getConnection()) {
    	   Statement stmt = connection.createStatement();
           ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                LectureSchedule schedule = new LectureSchedule(
                        rs.getString("course_name"),
                        rs.getString("division_name"),
                        rs.getString("name"),
                        rs.getString("lecture_day"),
                        rs.getString("start_time"),
                        rs.getString("end_time")
                );
                schedules.add(schedule);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedules;
    }

    // Method to add a new lecture schedule
    public static Boolean addLectureScheduleByName(LectureSchedule schedule) {
        String courseName = schedule.getCourseName();
        String divisionName = schedule.getDivisionName();
        String teacherName = schedule.getTeacherName();
        String lectureDay = schedule.getLectureDay();
        String startTime = schedule.getStartTime();
        String endTime = schedule.getEndTime();
        
        int courseId = CourseDAO.getIdByName(courseName);
        int divisionId = DivisionDAO.getDivisionIdByName(divisionName);
        int teacherId = TeacherDAO.getIdByName(teacherName);
        
        System.out.println(courseId + " " + divisionId + " " + teacherId + " " + lectureDay + " " + startTime + " " + endTime);
        
        // Assign teacher to course and division
        TeacherDAO.assignCourse(courseId, teacherId, divisionId);
        
        return addLectureSchedule(courseId, divisionId, teacherId, lectureDay, startTime, endTime);
    }
    
    // Method to add a new lecture schedule
	public static Boolean addLectureSchedule(int courseId, int divisionId, int teacherId, String lectureDay, String startTime, String endTime) {
		String sql = "INSERT INTO lecture_schedule (course_id, division_id, teacher_id, lecture_day, start_time, end_time) VALUES (?, ?, ?, ?, ?, ?)";
		try (Connection connection = Database.getConnection()) {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, courseId);
			pstmt.setInt(2, divisionId);
			pstmt.setInt(3, teacherId);
			pstmt.setString(4, lectureDay);
			pstmt.setString(5, startTime);
			pstmt.setString(6, endTime);
			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

    // Method to delete a lecture schedule
	public static boolean deleteLectureSchedule(int scheduleId) {
		String sql = "DELETE FROM lecture_schedule WHERE schedule_id = ?";
		try (Connection connection = Database.getConnection()) {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, scheduleId);
			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// Method to get lecture schedule for today for a division
	public static List<LectureSchedule> getTodaysScheduleForDivision(String divisionName) {
		List<LectureSchedule> schedules = new ArrayList<>();
		String sql = "SELECT ls.schedule_id, ls.course_id, ls.division_id, ls.teacher_id, ls.lecture_day, "
				+ "ls.start_time, ls.end_time, c.course_name, d.division_name, u.name " + "FROM lecture_schedule ls "
				+ "JOIN courses c ON ls.course_id = c.course_id "
				+ "JOIN divisions d ON ls.division_id = d.division_id "
				+ "JOIN teachers t ON ls.teacher_id = t.teacher_id " + "JOIN users u ON t.user_id = u.user_id "
				+ "WHERE d.division_name = ? AND ls.lecture_day = DAYNAME(CURDATE())";

		try (Connection connection = Database.getConnection()) {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, divisionName);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				LectureSchedule schedule = new LectureSchedule(rs.getString("course_name"),
						rs.getString("division_name"), rs.getString("name"), rs.getString("lecture_day"),
						rs.getString("start_time"), rs.getString("end_time"));
				schedules.add(schedule);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return schedules;
	}
	
	// Method to get lecture schedule for today for a teacher
	 public static List<LectureSchedule> getTodaysScheduleByTeacherId(int teacherId) {
			List<LectureSchedule> schedules = new ArrayList<>();
			String sql = "SELECT ls.schedule_id, ls.course_id, ls.division_id, ls.teacher_id, ls.lecture_day, "
					+ "ls.start_time, ls.end_time, c.course_name, d.division_name, u.name "
					+ "FROM lecture_schedule ls " + "JOIN courses c ON ls.course_id = c.course_id "
					+ "JOIN divisions d ON ls.division_id = d.division_id "
					+ "JOIN teachers t ON ls.teacher_id = t.teacher_id " + "JOIN users u ON t.user_id = u.user_id "
					+ "WHERE ls.teacher_id = ? AND ls.lecture_day = DAYNAME(CURDATE())";

			try (Connection connection = Database.getConnection()) {
				PreparedStatement pstmt = connection.prepareStatement(sql);
				pstmt.setInt(1, teacherId);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					LectureSchedule schedule = new LectureSchedule(rs.getString("course_name"),
							rs.getString("division_name"), rs.getString("name"), rs.getString("lecture_day"),
							rs.getString("start_time"), rs.getString("end_time"));
					schedules.add(schedule);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return schedules;
	 }

	public static LectureSchedule getLectureById(int scheduleId) {
		LectureSchedule lecture = null;
		String query = "SELECT * FROM lecture_schedule WHERE schedule_id = ?";
		try (Connection connection = Database.getConnection()) {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, scheduleId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				lecture = new LectureSchedule(rs.getInt("course_id"),
						rs.getInt("division_id"), rs.getInt("teacher_id"), rs.getString("lecture_day"),
						rs.getString("start_time"), rs.getString("end_time"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lecture;
	}
	
	public static Boolean markAttendance(int studentID, int courseID, int divisionID, String status) {
		String query = "INSERT INTO attendance (student_id, course_id, division_id, date, status) VALUES (?, ?, ?, CURDATE(), ?)";
		try (Connection connection = Database.getConnection()) {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, studentID);
			ps.setInt(2, courseID);
			ps.setInt(3, divisionID);
			ps.setString(4, status);
			int rowsAffected = ps.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean isAttendanceMarked(int courseId, int divisionId) {
		String query = "SELECT * FROM attendance WHERE course_id = ? AND division_id = ? AND date = CURDATE()";
        try (Connection connection = Database.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, courseId);
            ps.setInt(2, divisionId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
	}
}
