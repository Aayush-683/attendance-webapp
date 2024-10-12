<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.util.List"%>
<%@page import="models.User"%>
<%@page import="models.Student"%>
<%@page import="models.Course"%>
<%@page import="models.LectureSchedule"%>
<%@page import="models.Division"%>
<%@page import="dao.TeacherDAO"%>
<%@page import="dao.CourseDAO"%>
<html>
<head>
    <title>Teacher Dashboard</title>
    <link rel="stylesheet" type="text/css" href="assets/styles.css">
</head>
<body>
	<% User user = (User) request.getSession().getAttribute("user"); %>
    <h1>Welcome, <%= user.getName() %> </h1>
	<% if (request.getAttribute("error") != null) { %>
	    <p style="color: red;"><%= request.getAttribute("error") %></p>
   	<% } %>
    <h2>Your Courses</h2>
    <ul>
    	<% List<Course> courses = (List<Course>) request.getAttribute("courses"); %>
        <% if (courses != null) { %>
            <% for (Course course : courses) { %>
                <li><%= course.getName() %></li>
            <% } %>
        <% } %>
    </ul>

    <h2>Today's Schedule</h2>
    <table border="1">
        <tr>
            <th>Course</th>
            <th>Division</th>
            <th>Start Time</th>
            <th>End Time</th>
            <th>Action</th>
        </tr>
        <% List<LectureSchedule> schedules = (List<LectureSchedule>) request.getAttribute("todaySchedule"); %>
        <% if (schedules != null) { %>
			<% for (LectureSchedule schedule : schedules) { %>
			<tr>
			    <td><%= schedule.getCourseName() %></td>
				<td><%= schedule.getDivisionName() %></td>
				<td><%= schedule.getStartTime() %></td>
				<td><%= schedule.getEndTime() %></td>
				<td><a href="MarkAttendance?course=<%= schedule.getCourseName() %>">Mark Attendance</a></td>
			</tr>
			<% } %>
		<% } %>
	</table>

    <a href="authLogout">Logout</a>
</body>
</html>
