<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin | Add ${type}</title>
<link rel="stylesheet" type="text/css" href="assets/styles.css">
</head>
<body>
    <h1>Add ${type}</h1>
    <a href="AdminServlet">Back to Dashboard</a>
    <form>
    	<label for="type">Type</label>
    	<select id="type" name="action" onchange="this.form.submit()">
    		<option value="add-Teacher">Teacher</option>
    		<option value="add-Student">Student</option>
    		<option value="add-Course">Course</option>
    		<option value="add-Division">Division</option>
  		</select>
  		<script>
  			document.getElementById('type').value = "add-${type}";
  		</script>
   	</form>
    <form action="AdminServlet" method="post">
		<% if (request.getAttribute("type").equals("Teacher")) { %>
            <input type="hidden" name="action" value="addTeacher">
            <label for="name">Name</label>
            <input type="text" id="name" name="teacherName" required><br><br>
            <label for="email">Email</label>
            <input type="email" id="email" name="teacherEmail" required><br><br>
            <label for="password">Password</label>
            <input type="password" id="password" name="password" required><br><br>
            <button type="submit">Add Teacher</button>
    	<% } else if (request.getAttribute("type").equals("Student")) { %>
            <input type="hidden" name="action" value="addStudent">
            <label for="name">Name</label>
            <input type="text" id="name" name="studentName" required><br><br>
            <label for="email">Email</label>
            <input type="email" id="email" name="studentEmail" required><br><br>
            <label for="password">Password</label>
            <input type="password" id="password" name="password" required><br><br>
            <label for="division">Division</label>
            <input type="text" id="division" name="division" required><br><br>
            <button type="submit">Add Student</button>
    	<% } else if (request.getAttribute("type").equals("Course")) { %> 
           	<input type="hidden" name="action" value="addCourse">
            <label for="name">Name</label>
            <input type="text" id="name" name="courseName" required><br><br>
            <button type="submit">Add Course</button>
    	<% } else if (request.getAttribute("type").equals("Division")) { %>
            <input type="hidden" name="action" value="addDivision">
            <label for="name">Name</label>
            <input type="text" id="name" name="divisionName" required><br><br>
            <button type="submit">Add Division</button>
	   	<% } %>
    </form>
</body>
</html>