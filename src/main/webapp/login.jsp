<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MeetReserve Connection</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
<style>
	body{
		 background:url("${pageContext.request.contextPath}/images/room.jpg") center/cover no-repeat;
		 blur: 1.5em;
        font-family: Inter;
        text-color: #fff;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
        margin: 0;
	}
	.login-container{
		background-color: rgba(0, 128, 0, 0.8);
        padding: 30px;
        border-radius: 30px;
        box-shadow: 1px 5px 25px;
        width: 350px;
        
	}
	h2{
		text-align: center;
		margin-bottom: 30px;
		font-family: forte; 
		color: #fff;
	}
	label {
        display: block;
        margin-bottom: 6px;
        margin-top: 12px;
    }

    input {
        width: 100%;
        padding: 10px;
        box-sizing: border-box;
        border: 5px solid #ccc;
        border-radius: 5px;
    }
    .password-wrapper {
		position: relative;
	}
	.password-wrapper input {
		padding-right: 40px;
	}
	.toggle-icon {
		position: absolute;
		right: 12px;
		top: 70%;
		transform: translateY(-50%);
		cursor: pointer;
		font-size: 16px;
		color: #666;
		user-select: none;
	}

    button {
        width: 100%;
        margin-top: 20px;
        padding: 10px;
        border: none;
        border-radius: 20px;
        border: none;
        background-color: #2c7be5;
        color: white;
        font-size: 16px;
        cursor: pointer;
   }

   button:hover {
        background-color: #1b5fcc;
   }

   .error {
        color: red;
        margin-top: 15px;
        text-align: center;
    }
    
</style>
</head>
<body>

	<div class="login-container">
		<h2>Login</h2>
		
		<form action="${pageContext.request.contextPath}/login" method="post">
			<label for="email">Email</label>
			<input type="text" id="email" name="email" required>
			
			<div class="password-wrapper">
				<label for="password">Password</label>
			    <input type="password" id="password" name="password" autocomplete="current-password" required>
			    <span id="togglePassword" class="toggle-icon">
			        <i class="fa-solid fa-eye"></i>
			    </span>
			</div>
			
			<button type="submit">Login</button>
		</form>
		
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