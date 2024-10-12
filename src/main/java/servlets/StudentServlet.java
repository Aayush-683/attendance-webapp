package servlets;

import java.io.IOException;
import java.util.List;

import dao.CourseDAO;
import dao.LSDAO;
import dao.StudentDAO;
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
 * Servlet implementation class StudentServlet
 */
@WebServlet("/StudentServlet")
public class StudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
HttpSession session = request.getSession(false);
        
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        User user = (User) session.getAttribute("user");
        
		if (!user.getRole().equals("student")) {
			response.sendRedirect("authLogin");
			return;
		}
		
		String division = StudentDAO.getStudentDivisionFromUser(user);		
		
        // Fetch courses for the student
        List<Course> courses = CourseDAO.getCoursesByDivision(division);

        // Fetch today's schedule for the student
        List<LectureSchedule> todaySchedule = LSDAO.getTodaysScheduleForDivision(division);

        // Set attributes to display in JSP
        request.setAttribute("courses", courses);
        request.setAttribute("todaySchedule", todaySchedule);
        request.setAttribute("divisionName", division);
        request.setAttribute("studentName", user.getName());

        // Forward to student dashboard JSP
        request.getRequestDispatcher("studentDashboard.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
