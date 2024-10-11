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
                     "ls.start_time, ls.end_time, c.course_name, d.division_name, t.teacher_name " +
                     "FROM lecture_schedule ls " +
                     "JOIN courses c ON ls.course_id = c.course_id " +
                     "JOIN divisions d ON ls.division_id = d.division_id " +
                     "JOIN teachers t ON ls.teacher_id = t.teacher_id";

        try (Connection connection = Database.getConnection()) {
    	   Statement stmt = connection.createStatement();
           ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                LectureSchedule schedule = new LectureSchedule(
                        rs.getString("course_name"),
                        rs.getString("division_name"),
                        rs.getString("teacher_name"),
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
    public static boolean addLectureSchedule(LectureSchedule schedule) {
        String sql = "INSERT INTO lecture_schedule (course_id, division_id, teacher_id, lecture_day, start_time, end_time) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = Database.getConnection()) {
        	PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, schedule.getCourseId());
            pstmt.setInt(2, schedule.getDivisionId());
            pstmt.setInt(3, schedule.getTeacherId());
            pstmt.setString(4, schedule.getLectureDay());
            pstmt.setString(5, schedule.getStartTime());
            pstmt.setString(6, schedule.getEndTime());
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
}
