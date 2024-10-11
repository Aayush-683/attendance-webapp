<%@ page import="java.util.List" %>
<%@ page import="models.LectureSchedule" %>
<html>
<head>
    <title>View Lecture Schedule</title>
    <link rel="stylesheet" type="text/css" href="assets/style.css">
</head>
<body>
    <h1>Lecture Schedule</h1>
    <table border="1">
        <thead>
            <tr>
                <th>Course</th>
                <th>Division</th>
                <th>Teacher</th>
                <th>Day</th>
                <th>Start Time</th>
                <th>End Time</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<LectureSchedule> schedules = (List<LectureSchedule>) request.getAttribute("lectures");
                if (schedules != null && !schedules.isEmpty()) {
                    for (LectureSchedule schedule : schedules) {
            %>
                        <tr>
                            <td><%= schedule.getCourseName() %></td>
                            <td><%= schedule.getDivisionName() %></td>
                            <td><%= schedule.getTeacherName() %></td>
                            <td><%= schedule.getLectureDay() %></td>
                            <td><%= schedule.getStartTime() %></td>
                            <td><%= schedule.getEndTime() %></td>
                            <td>
                                <form action="AdminServlet" method="post">
                                    <input type="hidden" name="action" value="deleteSchedule">
                                    <input type="hidden" name="scheduleId" value="<%= schedule.getScheduleId() %>">
                                    <input type="submit" value="Delete">
                                </form>
                            </td>
                        </tr>
            <%
                    }
                } else {
            %>
                    <tr>
                        <td colspan="6">No schedules found.</td>
                    </tr>
            <%
                }
            %>
        </tbody>
        <tfoot>
            <tr>
                <td colspan="6">
                    <a href="AdminServlet?action=add-LectureSchedule">Add Lecture Schedule</a>
                </td>
            </tr>
        </tfoot>
    </table>
</body>
</html>
