<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Registration</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/fontawesome.min.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/solid.min.css" />
	<link rel="stylesheet" href="css/register.css" />
</head>
<body>
	<div class="register-container">
		<h2>Registration</h2>
		
		<form action="${pageContext.request.contextPath}/register" method="post">
			<label for="name">Name</label>
			<input type="text" id="name" name="name" required>
			
			<label for="surname">Surname</label>
			<input type="text" id="surname" name="surname" required>
			
			<label for="email">Email</label>
			<input type="email" id="email" name="email" required autocomplete="email">
			
			<div class="password-wrapper">
				<label for="password">Password</label>
			    <input type="password" id="password" name="password" autocomplete="new-password" required>
			    <span id="togglePassword" class="toggle-icon">
			        <i class="fa-solid fa-eye"></i>
			    </span>
			</div>
			
			<button type="submit">Register</button>
		</form>
		
		<p>Already have an account? <a href="${pageContext.request.contextPath}/login.jsp">Sign In</a></p>
		
		<p class="message">${message}</p>
		
	</div>
	
	<script>
	
		const message = document.querySelector('.message');
		const inputs = document.querySelectorAll('input');
	
		inputs.forEach(input => {
		    input.addEventListener('input', function () {
		        if (message) {
		            message.textContent = '';
		        }
		    });
		});
		
		const togglePassword = document.getElementById('togglePassword');
	    const passwordInput = document.getElementById('password');
	    
	    togglePassword.addEventListener('click', function () {
	        const isPassword = passwordInput.type === 'password';
	        passwordInput.type = isPassword ? 'text' : 'password';
	        
	        const icon = this.querySelector('i');
	        icon.classList.toggle('fa-eye');
	        icon.classList.toggle('fa-eye-slash');
	        
	    });
	</script>
	
	
</body>
</html>