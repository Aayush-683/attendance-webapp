package servlets;

import java.io.IOException;
import java.util.List;

import dao.CourseDAO;
import dao.LSDAO;
import dao.TeacherDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Course;
import models.LectureSchedule;
import models.User;

/**
 * Servlet implementation class TeacherServlet
 */
@WebServlet("/TeacherServlet")
public class TeacherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TeacherServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
HttpSession session = request.getSession(false);
        
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("authLogin");
            return;
        }

        User user = (User) session.getAttribute("user");
        
        if (!user.getRole().equals("teacher")) {
        	response.sendRedirect("authLogin");
        }
        
        int teacherId = TeacherDAO.getTeacherIdByUser(user);

        // Fetch assigned courses
        List<Course> courses = CourseDAO.getCoursesByTeacher(teacherId);

        // Fetch today's lecture schedule
        List<LectureSchedule> todaySchedule = LSDAO.getTodaysScheduleByTeacherId(teacherId);
        
        // Error handling
        String error = request.getParameter("error");
		if (error != null) {
			if (error.equals("lecture_inactive")) {
				request.setAttribute("error", "No lecture is currently active");
			} else if (error.equals("attendance_not_marked")) {
				request.setAttribute("error", "Attendance for the previous lecture was not marked");
			} else if (error.equals("attendance_marked")) {
				request.setAttribute("error", "Attendance marked successfully");
			} else if (error.equals("attendance_already_marked")) {
				request.setAttribute("error", "Attendance for that lecture is already marked");
			}
		}

        request.setAttribute("courses", courses);
        request.setAttribute("todaySchedule", todaySchedule);
        request.getRequestDispatcher("teacherDashboard.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
