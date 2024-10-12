<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.util.List"%>
<%@page import="models.Teacher"%>
<%@page import="models.Student"%>
<%@page import="models.Course"%>
<%@page import="models.LectureSchedule"%>
<%@page import="models.Division"%>
<%@page import="dao.StudentDAO"%>
<%@page import="dao.CourseDAO"%>
<html>
<head>
    <title>Mark Attendance</title>
    <link rel="stylesheet" type="text/css" href="assets/styles.css">
</head>
<body>
    <h1>Mark Attendance</h1>

    <h2>Lecture Details</h2>
    <% LectureSchedule schedule = (LectureSchedule) request.getAttribute("schedule"); %>
    <p>Course: <%= schedule.getCourseName() %></p>
    <p>Division: <%= schedule.getDivisionName() %></p>
    <p>Teacher: <%= schedule.getTeacherName() %></p>
    <p>Start Time: <%= schedule.getStartTime() %></p>
    <p>End Time: <%= schedule.getEndTime() %></p>

    <h2>Students List</h2>
    <form action="MarkAttendance" method="post">
        <table border="1">
            <tr>
                <th>Student ID</th>
                <th>Name</th>
                <th>Status</th>
            </tr>
            <% List<Student> students = (List<Student>) request.getAttribute("students"); %>
			<% for (Student student : students) { %>
			<tr>
				<td><%= StudentDAO.getIdByName(student.getName()) %></td>
				<td><%= student.getName() %></td>
				<td><select name="attendance_<%= StudentDAO.getIdByName(student.getName()) %>">
						<option value="present">Present</option>
						<option value="absent">Absent</option>
				</select></td>
			</tr>
			<% } %>
		</table>
		<input type="hidden" name="courseName" value="<%= schedule.getCourseName() %>">
		<input type="hidden" name="divisionName" value="<%= schedule.getDivisionName() %>">
        <button type="submit">Submit Attendance</button>
    </form>

    <a href="teacher">Back to Dashboard</a>
</body>
</html>
