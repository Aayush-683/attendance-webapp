<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="models.Course"%>
<%@page import="dao.CourseDAO"%>
<html>
<head>
    <title>View Courses</title>
    <link rel="stylesheet" href="assets/styles.css">
</head>
<body>
    <h1>Manage Courses</h1>
    <a href="AdminServlet">Back to Dashboard</a>

    <table border="1">
        <thead>
            <tr>
                <th>Course Name</th>
                <th>Teachers</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <% List<Course> courses = (List<Course>) request.getAttribute("courses"); %>
            <% for (Course course : courses) { %>
                <tr>
                    <td><%= course.getName() %></td>
					<td>
						<ul>
							<% for (String teacher : CourseDAO.getAllTeachers(course.getName())) { %>
								<li><%= teacher %></li>
							<% } %>
						</ul>
					</td>
					<td>
                        <form action="AdminServlet" method="post">
                            <input type="hidden" name="action" value="deleteCourse">
                            <input type="hidden" name="courseName" value="<%= course.getName() %>">
                            <input type="submit" value="Delete">
                        </form>
                    </td>
				</tr>
            <% } %>
        </tbody>
        <tfoot>
            <tr>
                <td colspan="3">
                    <a href="AdminServlet?action=add-Course">Add Course</a>
                </td>
            </tr>
        </tfoot>
    </table>
</body>
</html>
