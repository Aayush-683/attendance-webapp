<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="models.Division"%>
<%@page import="dao.DivisionDAO"%>

<html>
<head>
    <title>View Divisions</title>
    <link rel="stylesheet" href="assets/styles.css">
</head>
<body>
    <h1>Manage Divisions</h1>
    <a href="AdminServlet">Back to Dashboard</a>

    <table border="1">
        <thead>
            <tr>
                <th>Division Name</th>
                <th>Total Students</th>
                <th>Total Teachers</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <% List<Division> divisions = (List<Division>) request.getAttribute("divisions"); %>
            <% for (Division division : divisions) { %>
	            <tr>
                    <td><%= division.getDivisionName() %></td>
        	        <td><%= DivisionDAO.getTotalStudentsInDivision(division.getDivisionName()) %></td>
					<td><%= DivisionDAO.getTotalTeachersInDivision(division.getDivisionName()) %></td>
					<td>
						<form action="AdminServlet" method="post">
							<input type="hidden" name="action" value="deleteDivision">
							<input type="hidden" name="divisionName"
								value="<%= division.getDivisionName() %>"> <input
								type="submit" value="Delete">
						</form>
					</td>
				</tr>
            <% } %>
        </tbody>
        <tfoot>
            <tr>
                <td colspan="4">
                    <a href="AdminServlet?action=add-Division">Add Division</a>
                </td>
            </tr>
       	</tfoot>
    </table>
</body>
</html>
