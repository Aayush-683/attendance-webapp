package servlets;

import java.io.IOException;

import dao.StudentDAO;
import dao.TeacherDAO;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Student;
import models.Teacher;
import models.User;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/authRegister")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Check if user is logged in
		if (request.getSession().getAttribute("user") != null) {
            response.sendRedirect("dashboard.jsp");
		}
		String error = request.getParameter("error");
		if (error != null) {
			if (error.equals("invalid")) {
				request.setAttribute("error", "Email already exists in database!");
			} else if (error.equals("failed")) {
                request.setAttribute("error", "Registration failed. Contact admin if the problem persists.");
			} else if (error.equals("password")) {
                request.setAttribute("error", "Passwords do not match.");
			}
        }
		request.getRequestDispatcher("register.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");
		String role = request.getParameter("role");
		
		if (password.equals(confirmPassword)) {
            User user = new User(name, email, password, role);
            String register = UserDAO.registerUser(user);
            
            if (role == "student") {
            	Integer divisionId = Integer.parseInt(request.getParameter("divisionId"));
            	Student student = new Student(user.getEmail(), user.getName(), password, divisionId);
            	StudentDAO.addStudent(student);
			} else if (role == "teacher") {
				Teacher teacher = new Teacher(user.getEmail(), user.getName(), password);
				TeacherDAO.addTeacher(teacher);
			}
            
            if (register == "User Registered") {
                response.sendRedirect("authLogin?error=register");
            } else if (register == "User Already Exists") {
                response.sendRedirect("authRegister?error=invalid");
            } else {
                response.sendRedirect("authRegister?error=failed");
            }
        } else {
            response.sendRedirect("authRegister?error=password");
        }
	}

}
