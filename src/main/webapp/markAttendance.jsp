<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Mark Attendance</title>
    <link rel="stylesheet" type="text/css" href="assets/styles.css">
</head>
<body>
    <h2>Mark Attendance for Course 1</h2>
    <form action="markAttendance" method="post">
        <table border="1">
            <tr>
                <th>Student Name</th>
                <th>Present</th>
            </tr>
            <tr>
                <td>John Doe</td>
                <td><input type="checkbox" name="student1" value="present"></td>
            </tr>
            <tr>
                <td>Jane Smith</td>
                <td><input type="checkbox" name="student2" value="present"></td>
            </tr>
        </table>
        <button type="submit">Submit Attendance</button>
    </form>
</body>
</html>