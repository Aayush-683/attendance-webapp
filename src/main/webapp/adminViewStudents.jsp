<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="models.Student"%>
<%@page import="dao.DivisionDAO"%>
<html>
<head>
    <title>View Students</title>
    <link rel="stylesheet" href="assets/styles.css">
</head>
<body>
    <h1>Manage Students</h1>
    <a href="AdminServlet">Back to Dashboard</a>

    <table border="1">
        <thead>
            <tr>
                <th>Name</th>
                <th>Email</th>
                <th>Division</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <% List<Student> students = (List<Student>) request.getAttribute("students"); %>
            <% for (Student student : students) { %>
                <tr>
                    <td><%= student.getName() %></td>
                    <td><%= student.getEmail() %></td>
                    <td><%= DivisionDAO.getDivisionNameById(student.getDivisionId()) %></td>
				<td>
					<form action="AdminServlet" method="post">
                        <input type="hidden" name="action" value="deleteStudent">
                        <input type="hidden" name="email" value="<%= student.getEmail() %>">
                        <input type="submit" value="Delete">
                   	</form>
				</td>
			</tr>
            <% } %>
        </tbody>
        <tfoot>
            <tr>
                <td colspan="4">
                    <a href="AdminServlet?action=add-Student">Add Student</a>
                </td>
            </tr>
        </tfoot>
    </table>
</body>
</html>
