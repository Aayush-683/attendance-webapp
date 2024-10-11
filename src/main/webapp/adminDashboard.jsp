<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="models.Teacher"%>
<%@page import="models.Student"%>
<%@page import="models.Course"%>
<%@page import="models.Division"%>
<html>
<head>
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="assets/adminStyles.css">
</head>
<body>
    <h1>Admin Dashboard</h1>

    <!-- Links to manage the entities -->
    <nav>
        <ul>
            <li><a href="AdminServlet?action=viewTeachers">Manage Teachers</a></li>
            <li><a href="AdminServlet?action=viewStudents">Manage Students</a></li>
            <li><a href="AdminServlet?action=viewCourses">Manage Courses</a></li>
            <li><a href="AdminServlet?action=viewDivisions">Manage Divisions</a></li>
        </ul>
    </nav>

    <!-- Section to show summary of data -->
    <section>
        <h2>Summary</h2>
        <p>Total Teachers: <%= request.getAttribute("teacherCount") %></p>
        <p>Total Students: <%= request.getAttribute("studentCount") %></p>
        <p>Total Courses: <%= request.getAttribute("courseCount") %></p>
        <p>Total Divisions: <%= request.getAttribute("divisionCount") %></p>
    </section>
</body>
</html>
