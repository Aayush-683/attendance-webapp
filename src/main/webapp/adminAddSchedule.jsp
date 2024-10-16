<%@ page import="java.util.List" %>
<%@ page import="models.Course" %>
<%@ page import="models.Division" %>
<%@ page import="models.Teacher" %>
<html>
<head>
    <title>Add Lecture Schedule</title>
    <link rel="stylesheet" type="text/css" href="assets/styles.css">
</head>
<body>
    <h1>Add Lecture Schedule</h1>
    <a href="AdminServlet">Back to Dashboard</a>
    
    <form action="AdminServlet" method="post">
        <input type="hidden" name="action" value="addSchedule">

        <label for="course">Course:</label>
        <select name="courseId" id="course" required>
            <option value="">Select Course</option>
            <%
                List<Course> courses = (List<Course>) request.getAttribute("courses");
                for (Course course : courses) {
            %>
                <option value="<%= course.getName() %>"><%= course.getName() %></option>
            <%
                }
            %>
        </select><br>

        <label for="division">Division:</label>
        <select name="divisionId" id="division" required>
            <option value="">Select Division</option>
            <%
                List<Division> divisions = (List<Division>) request.getAttribute("divisions");
                for (Division division : divisions) {
            %>
                <option value="<%= division.getDivisionName() %>"><%= division.getDivisionName() %></option>
            <%
                }
            %>
        </select><br>

        <label for="teacher">Teacher:</label>
        <select name="teacherId" id="teacher" required>
            <option value="">Select Teacher</option>
            <%
                List<Teacher> teachers = (List<Teacher>) request.getAttribute("teachers");
                for (Teacher teacher : teachers) {
            %>
                <option value="<%= teacher.getName() %>"><%= teacher.getName() %></option>
            <%
                }
            %>
        </select><br>

        <label for="day">Lecture Day:</label>
        <select name="lectureDay" id="day" required>
            <option value="">Select Day</option>
            <option value="Monday">Monday</option>
            <option value="Tuesday">Tuesday</option>
            <option value="Wednesday">Wednesday</option>
            <option value="Thursday">Thursday</option>
            <option value="Friday">Friday</option>
            <option value="Saturday">Saturday</option>
            <option value="Sunday">Sunday</option>
        </select><br>

        <label for="start">Start Time:</label>
        <input type="time" name="startTime" id="start" required><br>

        <label for="end">End Time:</label>
        <input type="time" name="endTime" id="end" required><br>

        <button type="submit">Add Schedule</button>
    </form>
</body>
</html>
