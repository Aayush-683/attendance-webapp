package servlets;

import java.io.IOException;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/authLogin")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Check if user is logged in
		HttpSession session = request.getSession();
		if (session.getAttribute("user") != null) {
			// Check user role
			User user = (User) session.getAttribute("user");
			if (user.getRole().equals("admin")) {
				response.sendRedirect("AdminServlet");
				return;
			} else if (user.getRole().equals("teacher")) {
				response.sendRedirect("TeacherServlet");
				return;
			} else {
				response.sendRedirect("StudentServlet");
				return;
			}
		} else {
			String error = request.getParameter("error");
            if (error != null) {
                if (error.equals("invalid")) {
                    request.setAttribute("error", "Invalid email or password");
                } else if (error.equals("register")) {
                    request.setAttribute("error", "Registration successful. Login to continue.");
                }
            }
		}
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        User user = UserDAO.authenticate(email, password);
        
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            if (user.getRole().equals("admin")) {
                response.sendRedirect("AdminServlet");
            } else if (user.getRole().equals("teacher")) {
                response.sendRedirect("TeacherServlet");
            } else {
                response.sendRedirect("StudentServlet");
            }
        } else {
            response.sendRedirect("authLogin?error=invalid");
        }
	}

}
