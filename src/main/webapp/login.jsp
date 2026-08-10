<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>MeetReserve Connection</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/fontawesome.min.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/solid.min.css" />
	<link rel="stylesheet" href="css/login.css" />
</head>
<body>
	
	<div class="logo-container">
		<img src="${pageContext.request.contextPath}/images/Log2.png" alt="Logo" class="logo">
	</div>
	
	
	<div class="login-container">
		<h2>Login</h2>
		
		<form action="${pageContext.request.contextPath}/login" method="post">
			<label for="email">Email</label>
			<input type="email" id="email" name="email" required>
			
			<div class="password-wrapper">
				<label for="password">Password</label>
			    <input type="password" id="password" name="password" autocomplete="current-password" required>
			    <span id="togglePassword" class="toggle-icon">
			        <i class="fa-solid fa-eye"></i>
			    </span>
			</div>
			
			<button type="submit">Login</button>
		</form>
		
		<p>Don't have an account? <a href="${pageContext.request.contextPath}/register.jsp">Sign Up</a></p>
		
		<% String error = request.getParameter("error");
			if(error != null){
				String message;
				switch (error){
					case "champsVides":
						message = "Please fill in the blank spaces";
						break;
					case "identifiants":
						message = "Email or Password not correct";
						break;
					case "role":
						message = "Unknown user role";
						break;
					default:
						message = "An error occured";
						break;
				}
		%>
		<div class="error"><%= message %></div>
		<%
			}
		%>
	
	</div>
	
	
	<script>
		const inputs = document.querySelectorAll('input');
		const errorDiv = document.querySelector('.error');

		if (errorDiv) {
			inputs.forEach(function(input) {
				input.addEventListener('input', function() {
					errorDiv.style.display = 'none';
				});
			});
		}
		
		const togglePassword = document.getElementById('togglePassword');
	    const passwordInput = document.getElementById('password');
	    
	    togglePassword.addEventListener("click", function () {
	        const type = passwordInput.getAttribute("type") === "password" ? "text" : "password";
	        passwordInput.setAttribute("type", type);
	        
	        const icon = this.querySelector('i');
	        icon.classList.toggle("fa-eye");
	        icon.classList.toggle("fa-eye-slash");
	        
	    });
	</script>

</body>
</html>