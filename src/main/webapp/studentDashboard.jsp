<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.util.List"%>
<%@page import="models.Teacher"%>
<%@page import="models.Student"%>
<%@page import="models.Course"%>
<%@page import="models.LectureSchedule"%>
<%@page import="models.Division"%>
<%@page import="dao.StudentDAO"%>
<%@page import="dao.TeacherDAO"%>
<%@page import="dao.CourseDAO"%>
<html>
<head>
    <title>Student Dashboard</title>
    <link rel="stylesheet" type="text/css" href="assets/styles.css">
</head>
<body>
    <h1>Welcome, ${studentName} | ${divisionName}</h1>

    <h2>Your Courses</h2>
    <ul>
    	<% List<Course> courses = (List<Course>) request.getAttribute("courses"); %>
		<% for (Course course : courses) { %>
			<li><%= course.getName() %>: <%= StudentDAO.getAttendancePerc(course.getName(), (String) request.getAttribute("studentName")) %>%</li>
		<% } %>
	</ul>

    <h2>Today's Schedule</h2>
    <table border="1">
        <tr>
            <th>Course</th>
            <th>Teacher</th>
            <th>Start Time</th>
            <th>End Time</th>
        </tr>
        <% List<LectureSchedule> schedules = (List<LectureSchedule>) request.getAttribute("todaySchedule"); %>
		<% if (request.getAttribute("todaySchedule") != null) { %>
			<% for (LectureSchedule schedule : schedules) { %>
				<tr>
					<td><%= schedule.getCourseName() %></td>
					<td><%= schedule.getTeacherName() %></td>
					<td><%= schedule.getStartTime() %></td>
					<td><%= schedule.getEndTime() %></td>
				</tr>
			<% } %>
		<% } %>
	</table>

    <a href="authLogout">Logout</a>
</body>
</html>
