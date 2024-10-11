<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Student Dashboard</title>
    <link rel="stylesheet" type="text/css" href="assets/styles.css">
</head>
<body>
    <h2>Welcome, Student</h2>
    <h3>Your Courses</h3>
    <ul>
        <!-- Use JSTL or a scriptlet to loop through courses -->
        <li>Course 1 - Attendance: 85%</li>
        <li>Course 2 - Attendance: 90%</li>
    </ul>
    
    <h3>Your Schedule for Today</h3>
    <table border="1">
        <tr>
            <th>Time</th>
            <th>Course</th>
            <th>Teacher</th>
        </tr>
        <tr>
            <td>10:00 - 11:00 AM</td>
            <td>Course 1</td>
            <td>Professor Alan</td>
        </tr>
    </table>

    <a href="authLogout">Logout</a>
</body>
</html>
