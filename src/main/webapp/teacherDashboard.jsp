<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Teacher Dashboard</title>
    <link rel="stylesheet" type="text/css" href="assets/styles.css">
</head>
<body>
    <h2>Welcome, Teacher</h2>
    <h3>Your Courses</h3>
    <ul>
        <!-- Use JSTL or a scriptlet to loop through courses -->
        <li><a href="markAttendance.jsp?courseId=1">Course 1</a></li>
        <li><a href="markAttendance.jsp?courseId=2">Course 2</a></li>
    </ul>
    
    <a href="logout">Logout</a>
</body>
</html>
