<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" type="text/css" href="assets/styles.css">
</head>
<body>
    <h2>Register</h2>
    <form action="authRegister" method="post">
  		<% if (request.getAttribute("error") != null) { %>
	        <p style="color: red;"><%= request.getAttribute("error") %></p>
        <% } %>
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" required><br><br>
        
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required><br><br>
        
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br><br>
        
        <label for="confirmPassword">Confirm Password:</label>
        <input type="password" id="confirmPassword" name="confirmPassword" required><br><br>

        <label for="role">Role:</label>
        <select id="role" name="role" onchange="showFields()" required>
            <option value="student">Student</option>
            <option value="teacher">Teacher</option>
        </select><br><br>
        
        <div id="studentFields">
            <label for="divisionId">Division:</label>
            <select id="divisionId" name="divisionId" required>
            <option value="1">A</option>
            <option value="2">B</option>
            <option value="3">C</option>
        </select><br><br>
        </div>
        
        <button type="submit">Register</button>
        
        <p>Already have an account? <a href="authLogin">Login here</a></p>
    </form>
    
    <script>
        function showFields() {
            var role = document.getElementById("role").value;
            var studentFields = document.getElementById("studentFields");
            
            if (role === "student") {
                studentFields.style.display = "block";
                var studInput = document.getElementById("division");
                studInput.setAttribute("required", "");
            } else {
                studentFields.style.display = "none";
            }
        }
    </script>
</body>
</html>
