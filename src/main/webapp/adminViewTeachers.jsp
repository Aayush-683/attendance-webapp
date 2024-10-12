<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="models.Teacher"%>
<%@page import="models.Course"%>
<%@page import="models.Division"%>
<%@page import="dao.TeacherDAO"%>
<html>
<head>
    <title>View Teachers</title>
    <link rel="stylesheet" href="assets/styles.css">
</head>
<body>
    <h1>Manage Teachers</h1>
    <a href="AdminServlet">Back to Dashboard</a>

    <table border="1">
        <thead>
            <tr>
                <th>Name</th>
                <th>Email</th>
                <th>Divisions</th>
                <th>Courses</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <% List<Teacher> teachers = (List<Teacher>) request.getAttribute("teachers"); %>
            <% for (Teacher teacher : teachers) { %>
                <tr>
                    <td><%= teacher.getName() %></td>
                    <td><%= teacher.getEmail() %></td>
                    <td>
                        <ul>
                            <% for (Division division : TeacherDAO.getAllDivisions(teacher.getEmail())) { %>
                                <li><%= division.getDivisionName() %></li>
                            <% } %>
                        </ul>
                    </td>
                    <td>
                        <ul>
                            <% for (Course course : TeacherDAO.getAllCourses(teacher.getEmail())) { %>
                                <li><%= course.getName() %></li>
                            <% } %>
                        </ul>
                    </td>
                    <td>
                        <form action="AdminServlet" method="post">
                        <input type="hidden" name="action" value="deleteTeacher">
                        <input type="hidden" name="email" value="<%= teacher.getEmail() %>">
                        <input type="submit" value="Delete">
                   	</form>
                    </td>
                </tr>
            <% } %>
        </tbody>
        <tfoot>
            <tr>
                <td colspan="5">
                    <a href="AdminServlet?action=add-Teacher">Add Teacher</a>
                </td>
            </tr>
        </tfoot>
    </table>
</body>
</html>
