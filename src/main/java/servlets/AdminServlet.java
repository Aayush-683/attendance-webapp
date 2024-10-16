package servlets;

import java.io.IOException;
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
import models.Course;
import models.Division;
import models.LectureSchedule;
import models.Student;
import models.Teacher;
import models.User;

/**
 * Servlet implementation class TeacherServlet
 */
@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Check if user role is admin
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null || !user.getRole().equals("admin")) {
			response.sendRedirect("authLogin");
			return;
		}
		
		String action = request.getParameter("action");
		if (action == null) action = "";
        if ("viewTeachers".equals(action)) {
            List<Teacher> teachers = TeacherDAO.getAllTeachers();
            request.setAttribute("teachers", teachers);
            request.getRequestDispatcher("/adminViewTeachers.jsp").forward(request, response);
        } else if ("viewStudents".equals(action)) {
            List<Student> students = StudentDAO.getAllStudents();
            request.setAttribute("students", students);
            request.getRequestDispatcher("/adminViewStudents.jsp").forward(request, response);
        } else if ("viewCourses".equals(action)) {
            List<Course> courses = CourseDAO.getAllCourses();
            request.setAttribute("courses", courses);
            request.getRequestDispatcher("/adminViewCourses.jsp").forward(request, response);
        } else if ("viewDivisions".equals(action)) {
            List<Division> divisions = DivisionDAO.getAllDivisions();
            request.setAttribute("divisions", divisions);
            request.getRequestDispatcher("/adminViewDivisions.jsp").forward(request, response);
        } else if (("viewSchedules").equals(action)) {
        	List<LectureSchedule> lectures = LSDAO.getAllSchedules();
        	request.setAttribute("lectures", lectures);
        	request.getRequestDispatcher("/adminViewSchedule.jsp").forward(request, response);
        } else if (action.contains("-")) {
        	String[] parts = action.split("-");
        	if ("add".equals(parts[0])) {
        		if ("LectureSchedule".equals(parts[1])) {
					List<Teacher> teachers = TeacherDAO.getAllTeachers();
					List<Division> divisions = DivisionDAO.getAllDivisions();
					List<Course> courses = CourseDAO.getAllCourses();
					request.setAttribute("teachers", teachers);
					request.setAttribute("divisions", divisions);
					request.setAttribute("courses", courses);
					request.getRequestDispatcher("/adminAddSchedule.jsp").forward(request, response);
				} else {
					String type = parts[1];
					request.setAttribute("type", type);
        			request.getRequestDispatcher("/adminAdd.jsp").forward(request, response);
				}
        	} 
        } else {
            // Default: show the admin dashboard
        	List<Teacher> teachers = TeacherDAO.getAllTeachers();
        	List<Student> students = StudentDAO.getAllStudents();
        	List<Course> courses = CourseDAO.getAllCourses();
        	List<Division> divisions = DivisionDAO.getAllDivisions();
        	List<LectureSchedule> schedules = LSDAO.getAllSchedules();
        	request.setAttribute("totalTeachers", teachers.size());
        	request.setAttribute("totalStudents", students.size());
        	request.setAttribute("totalCourses", courses.size());
        	request.setAttribute("totalDivisions", divisions.size());
        	request.setAttribute("totalSchedules", schedules.size());
            request.getRequestDispatcher("/adminDashboard.jsp").forward(request, response);
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			// Check if user role is admin
		    HttpSession session = request.getSession();
		    User user = (User) session.getAttribute("user");
		    if (user == null || !user.getRole().equals("admin")) {
                response.sendRedirect("authLogin");
                return;
		    }
		
		
	        String action = request.getParameter("action");
	        if (action != null) {
	            if (action.equals("addTeacher")) {
	                // Add a new teacher
	                String teacherName = request.getParameter("teacherName");
	                String teacherEmail = request.getParameter("teacherEmail");
	                String password = request.getParameter("password");
	                Teacher teacher = new Teacher(teacherName, teacherEmail, password);
	                Boolean check = TeacherDAO.addTeacher(teacher);
					if (check) {
						response.sendRedirect("AdminServlet?action=viewTeachers");
					} else {
						request.setAttribute("error", "An error occured while adding the teacher, does the teacher already exist?");
						response.sendRedirect("AdminServlet?action=viewTeachers");
					}

	            } else if (action.equals("addStudent")) {
	                // Add a new student
	                String studentName = request.getParameter("studentName");
	                String studentEmail = request.getParameter("studentEmail");
	                String password = request.getParameter("password");
	                int divisionId = Integer.parseInt(request.getParameter("divisionId"));
	                Student student = new Student(studentName, studentEmail, password, divisionId);
	                StudentDAO.addStudent(student);
	                response.sendRedirect("AdminServlet?action=viewStudents");

	            } else if (action.equals("addCourse")) {
	                // Add a new course
	                String courseName = request.getParameter("courseName");
	                Course course = new Course(courseName);
	                CourseDAO.addCourse(course);
	                response.sendRedirect("AdminServlet?action=viewCourses");

	            } else if (action.equals("addDivision")) {
	                // Add a new division
	                String divisionName = request.getParameter("divisionName");
	                Division division = new Division(divisionName);
	                DivisionDAO.addDivision(division);
	                response.sendRedirect("AdminServlet?action=viewDivisions");

	            } else if (action.equals("deleteTeacher")) {
	                // Delete a teacher
	                String teacher = request.getParameter("email");
	                TeacherDAO.deleteTeacher(teacher);
	                response.sendRedirect("AdminServlet?action=viewTeachers");

	            } else if (action.equals("deleteStudent")) {
	                // Delete a student
	                String student = request.getParameter("email");
	                StudentDAO.deleteStudent(student);
	                response.sendRedirect("AdminServlet?action=viewStudents");

	            } else if (action.equals("deleteCourse")) {
	                // Delete a course
	                String courseId = request.getParameter("courseId");
	                CourseDAO.deleteCourse(courseId);
	                response.sendRedirect("AdminServlet?action=viewCourses");

	            } else if (action.equals("deleteDivision")) {
	                // Delete a division
	                String divisionId = request.getParameter("divisionId");
	                DivisionDAO.deleteDivision(divisionId);
	                response.sendRedirect("AdminServlet?action=viewDivisions");
	            } else if (action.equals("deleteSchedule")) {
					// Delete a schedule
					int scheduleId = Integer.parseInt(request.getParameter("scheduleId"));
					LSDAO.deleteLectureSchedule(scheduleId);
					response.sendRedirect("AdminServlet?action=viewSchedule");
				} else if (action.equals("addSchedule")) {
					// Add a new schedule
					String day = request.getParameter("lectureDay");
					String startTime = request.getParameter("startTime");
					String endTime = request.getParameter("endTime");
					String teacherName = request.getParameter("teacherId");
					String divisionName = request.getParameter("divisionId");
					String courseName = request.getParameter("courseId");
					LectureSchedule schedule = new LectureSchedule(courseName, divisionName, teacherName, day, startTime, endTime);
					LSDAO.addLectureScheduleByName(schedule);
					response.sendRedirect("AdminServlet?action=viewSchedule");
	            }
	        } 
	    }


}
