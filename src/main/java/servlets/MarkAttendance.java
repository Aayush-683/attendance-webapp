package servlets;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import dao.CourseDAO;
import dao.DivisionDAO;
import dao.LSDAO;
import dao.StudentDAO;
import dao.TeacherDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.LectureSchedule;
import models.Student;
import models.User;

/**
 * Servlet implementation class MarkAttendance
 */
@WebServlet("/MarkAttendance")
public class MarkAttendance extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MarkAttendance() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
        
		if (session.getAttribute("user") == null) {
			response.sendRedirect("authLogin");
			return;
		}
		
		User user = (User) session.getAttribute("user");
		if (!user.getRole().equals("teacher")) {
			response.sendRedirect("authLogin");
		}

		int teacherId = TeacherDAO.getTeacherIdByUser(user);
        String courseName = (String) request.getParameter("course").replace("%20", " ");
        
        // Get lecture schedule for today
        List<LectureSchedule> schedules = LSDAO.getTodaysScheduleByTeacherId(teacherId);
        
        // FIlter schedules for the course
        schedules.removeIf(schedule -> !schedule.getCourseName().equals(courseName));
        
        // Get the schedule that is currently in progress
        LectureSchedule currentSchedule = null;
		
        for (LectureSchedule schedule : schedules) {
			LocalTime currentTime = LocalTime.now();
			LocalTime startTime = LocalTime.parse(schedule.getStartTime());
			LocalTime endTime = LocalTime.parse(schedule.getEndTime());
			if (currentTime.isAfter(startTime) && currentTime.isBefore(endTime)) {
				currentSchedule = schedule;
				break;
			}
		}
	
		if (currentSchedule == null) {
			response.sendRedirect("TeacherServlet?error=lecture_inactive");
		} else {
			List<Student> students = StudentDAO.getStudentsByDivisionName(currentSchedule.getDivisionName());
			
			request.setAttribute("students", students);
			request.setAttribute("schedule", currentSchedule);

	        request.getRequestDispatcher("markAttendance.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String courseName = request.getParameter("courseName");
		String divisionName = request.getParameter("divisionName");
		
		// Get the courseID and divisionID
        int courseId = CourseDAO.getIdByName(courseName);
        int divisionId = DivisionDAO.getDivisionIdByName(divisionName);
        
        // Check if attendance has been marked for this course, for this division, for this date
		if (LSDAO.isAttendanceMarked(courseId, divisionId)) {
			response.sendRedirect("TeacherServlet?error=attendance_already_marked");
		} else {
	        // Iterate over the form parameters to collect attendance data
	        request.getParameterMap().forEach((key, values) -> {
	            if (key.startsWith("attendance_")) {
	                int studentId = Integer.parseInt(key.split("_")[1]);
	                String status = values[0];
	                Boolean check = LSDAO.markAttendance(studentId, courseId, divisionId, status);
	                if (!check) {
	                    try {
							response.sendRedirect("TeacherServlet?error=attendance_not_marked");
						} catch (IOException e) {
							e.printStackTrace();
						}
	                }
	            }
	        });
        	response.sendRedirect("TeacherServlet?error=attendance_marked");
		}
	}
}
